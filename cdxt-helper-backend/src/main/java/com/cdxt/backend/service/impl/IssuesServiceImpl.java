package com.cdxt.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdxt.backend.convert.IssueConvert;
import com.cdxt.backend.enums.AfsWssMsgTypeEnum;
import com.cdxt.backend.enums.TaskStateEnum;
import com.cdxt.backend.model.Issues;
import com.cdxt.backend.dao.IssuesMapper;
import com.cdxt.backend.model.IssuesOpLog;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.dto.IssuesUpdateDTO;
import com.cdxt.backend.pojo.vo.IssuesDetailVO;
import com.cdxt.backend.pojo.vo.IssuesViewVO;
import com.cdxt.backend.service.IssuesOpLogService;
import com.cdxt.backend.service.IssuesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.backend.service.SMSService;
import com.cdxt.backend.service.UserService;
import com.cdxt.backend.websockt.AfterSafeIssueWebsocket;
import com.cdxt.common.enums.MailType;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResponseListVO;
import com.cdxt.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
@Slf4j
@Service
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues> implements IssuesService {

    private   IssueConvert issueConvert =  Mappers.getMapper(IssueConvert.class);

    @Autowired
    IdWorker idWorker;
    @Autowired
    UserService userService;
    @Autowired
    IssuesOpLogService issuesOpLogService;
    @Autowired
    AfterSafeIssueWebsocket afterSafeIssueWebsocket;
    @Autowired
    SMSService smsService;

    @Override
    public Boolean releaseIssue(IssuesPostDTO postDTO) {
        Issues  issues = issueConvert.postParams2Entity(postDTO);
        issues.setId(idWorker.nextId()+"");
        issues.setState(postDTO.getIsAdditional()? TaskStateEnum.RESOLVE.value() : TaskStateEnum.UNPROCESSED.value()  );
        Boolean isSave = this.save(issues);
        //查询关联信息并返回
        //IssuesViewVO issuesViewVO = baseMapper.selectViewColumnById(issues.getId());
        String userName = userService.getTrueName(postDTO.getPUid());
        if (isSave){
            StringBuilder issueLog = new StringBuilder();
            if (postDTO.getIsAdditional()){
                issueLog.append(userName + "补录了问题任务:")
                        .append(postDTO.getTitle());
            }else{
                issueLog.append(userName + "发布了问题任务:")
                        .append(postDTO.getTitle());
            }
            sendIssueOpLog(issues.getId(),issues.getPUid(),issueLog.toString(),null);
            //发布紧急任务邮件提醒
//            if (false == postDTO.getIsAdditional()){
//                String email = userService.getUserEmail(issues.getDUid());
//                smsService.sendMail(email,"紧急问题任务提示邮件",issueLog.toString(), MailType.NOTIC);
//            }

        }
        return isSave;
    }

    @Override
    public Boolean updateIssueState(IssuesUpdateDTO dto,String opUid) {
        Optional.ofNullable(dto.getState()).orElseThrow(()->
                new ResponseCommonException(HttpStatus.BAD_REQUEST,"状态不能为空")
        );
        IssuesViewVO currentIssue = baseMapper.selectViewColumnById(dto.getId());
        if (null == currentIssue){
            new ResponseCommonException(HttpStatus.BAD_REQUEST,"问题信息不存在,请确认后重试");
        }
        String opTypeText  = "";
        StringBuilder issueLog = new StringBuilder();
        if (TaskStateEnum.PROCESSING.value().equals(dto.getState()) && TaskStateEnum.UNPROCESSED.value().equals(currentIssue.getState())){
            opTypeText = "开始了";
        }else if (TaskStateEnum.AUDIT.value().equals(dto.getState()) && TaskStateEnum.PROCESSING.value().equals(currentIssue.getState())){
            opTypeText="完成了";
        }else if (TaskStateEnum.RESOLVE.value().equals(dto.getState()) && TaskStateEnum.AUDIT.value().equals(currentIssue.getState())){
            opTypeText="审核通过了";
        }else if (TaskStateEnum.PROCESSING.value().equals(dto.getState()) && TaskStateEnum.AUDIT.value().equals(currentIssue.getState())){
            opTypeText="审核退回了";
        } else{
            throw  new ResponseCommonException("未知的问题状态");
        }
        Issues issues = issueConvert.updateParams2Entity(dto);
        Boolean isUpdate = baseMapper.updateByIdWithFieldsNotNull(issues) > 0 ? true: false;
        if (isUpdate){
            String userName = userService.getTrueName(opUid);
            issueLog.append(userName);
            issueLog.append(opTypeText);
            issueLog.append("任务:").append(currentIssue.getTitle());
            sendIssueOpLog(issues.getId(),opUid,issueLog.toString(),dto.getFeedBackText());
        }
        return isUpdate;
    }
    @Override
    public ResponseListVO<IssuesViewVO> searchIssueList(IssuesQueryDTO dto) {
        List<IssuesViewVO> records =  baseMapper.selectPageByQueryDTO(dto);
        Long totalCount = baseMapper.selectPageByQueryDTOCount(dto);
        return  new ResponseListVO<>(dto.getPageNum(),dto.getPageSize(),totalCount,records);
    }

    @Override
    public IssuesDetailVO  issueDetail(String issueId) {
        IssuesDetailVO detailVO = baseMapper.selectById(issueId);
        return detailVO;
    }

    @Override
    public IssuesViewVO getIssueBaseInfo(String issueId) {
        return baseMapper.selectViewColumnById(issueId);
    }

    @Override
    public Boolean assignIssueDealUser(String issueId, String dUid, String opUid,String feedbackText) {
        //判断用户是否存在
        Boolean isisExistUser  =  userService.isExistUser(dUid);
        if (!isisExistUser){
            throw  new ResponseCommonException(HttpStatus.BAD_REQUEST,"指派用户不存在,请确认是否正确");
        }
        Issues issues = new Issues();
        issues.setId(issueId);
        issues.setDUid(dUid);
        issues.setState(TaskStateEnum.UNPROCESSED.value());
        Boolean isUpdate = baseMapper.updateByIdWithFieldsNotNull(issues) > 0 ? true: false;
        if (isUpdate){
            StringBuilder issueLog = new StringBuilder();
            String dUserName = userService.getTrueName(dUid);
            String oPserName = userService.getTrueName(opUid);
            issueLog.append(oPserName).append("指派了任务").append("给" + dUserName);
            sendIssueOpLog(issueId,opUid,issueLog.toString(),null);
        }
        return isUpdate;
    }

    /**
     * 向所有客户端发送日志
     * @param issueId
     * @param uid
     * @param content
     */
    void sendIssueOpLog(String issueId,String uid,String content,String extrasText){
        IssuesOpLog issuesOpLog = new IssuesOpLog();
        issuesOpLog.setIsid(issueId);
        issuesOpLog.setContent(content);
        issuesOpLog.setUid(uid);
        issuesOpLog.setGmtCreate(LocalDateTime.now());
        issuesOpLog.setExtrasText(extrasText);
        issuesOpLogService.save(issuesOpLog);
        try {
            JSONObject response = new JSONObject();
            response.put("type", AfsWssMsgTypeEnum.ISSUE_OP_LOG);
            response.put("data",issuesOpLog);
            afterSafeIssueWebsocket.sendMessage(response);
        } catch (IOException e) {
            log.error("问题操作日志发送失败",e);
        }
    }
}

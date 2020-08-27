package com.cdxt.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdxt.backend.convert.IssueConvert;
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
import com.cdxt.backend.service.UserService;
import com.cdxt.backend.websockt.AfterSafeIssueWebsocket;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResponseListVO;
import com.cdxt.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
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

    @Override
    public Boolean releaseIssue(IssuesPostDTO postDTO) {
        Issues  issues = issueConvert.postParams2Entity(postDTO);
        issues.setId(idWorker.nextId()+"");
        issues.setState(postDTO.getIsAdditional()? TaskStateEnum.RESOLVE.value() : TaskStateEnum.UNPROCESSED.value());
        Boolean isSave = this.save(issues);
        StringBuilder issueLog = new StringBuilder();
        //查询关联信息并返回
        //IssuesViewVO issuesViewVO = baseMapper.selectViewColumnById(issues.getId());
        String userName = Optional.ofNullable(userService.getTrueName(postDTO.getpUid())).orElseGet(() -> "未知用户");
        issueLog.append(userName + "发布了问题任务:")
                .append(postDTO.getTitle());
        if (isSave){
            sendIssueOpLog(issues.getId(),issues.getPUid(),issueLog.toString());
        }
        return isSave;
    }

    @Override
    public Boolean updateIssueState(IssuesUpdateDTO dto,String opUid) {
        Optional.ofNullable(dto.getState()).orElseThrow(()->
                new ResponseCommonException(HttpStatus.BAD_REQUEST,"状态不能为空")
        );
        StringBuilder issueLog = new StringBuilder();
        issueLog.append(opUid).append("用户");
        if ("1".equals(dto.getState())){//完成
            issueLog.append("完成了");
        }else if ("2".equals(dto.getState())){//校验
            issueLog.append("审核通过");
        }else if ("3".equals(dto.getState())){//处理中 （退回）
            issueLog.append("审核失败");
        }else{
            throw  new ResponseCommonException("状态匹配,请问题处理状态参数是否正确!");
        }
        issueLog.append("\"").append(dto.getTitle()).append("\"")
                .append("问题");
        Issues issues = issueConvert.updateParams2Entity(dto);
        Boolean isUpdate = this.updateById(issues);
        if(isUpdate){
            sendIssueOpLog(issues.getId(),issues.getPUid(),issueLog.toString());
        }

        return isUpdate;
    }
    @Override
    public ResponseListVO<IssuesViewVO> searchIssueList(IssuesQueryDTO dto) {
        List<IssuesViewVO> records =  baseMapper.selectPageByQueryDTO(dto);
        Long totalCount = baseMapper.selectPageByQueryDTOCount(dto);
        return  new ResponseListVO<IssuesViewVO>(dto.getPageNum(),dto.getPageNum(),totalCount,records);
    }

    @Override
    public IssuesDetailVO  issueDetail(String issueId) {
        IssuesDetailVO detailVO = baseMapper.selectById(issueId);
        return detailVO;
    }

    @Override
    public Boolean assignIssueDealUser(String issueId, String dUid, String opUid) {
        Issues issues = new Issues();
        issues.setId(issueId);
        issues.setDUid(dUid);
        Boolean isUpdate = this.updateById(issues);
        StringBuilder issueLog = new StringBuilder();
        if (isUpdate){
            issueLog.append(opUid).append("将任务").append(issueId).append("指派给了用户")
                    .append(dUid);
            sendIssueOpLog(issueId,opUid,issueLog.toString());
        }
        return isUpdate;
    }

    /**
     * 向所有客户端发送日志
     * @param issueId
     * @param uid
     * @param content
     */
    void sendIssueOpLog(String issueId,String uid,String content){
        IssuesOpLog issuesOpLog = new IssuesOpLog();
        issuesOpLog.setIsid(issueId);
        issuesOpLog.setContent(content);
        issuesOpLog.setUid(uid);
        issuesOpLogService.save(issuesOpLog);
        try {
            JSONObject response = new JSONObject();
            response.put("type","oplog");
            afterSafeIssueWebsocket.sendMessage(issuesOpLog);
        } catch (IOException e) {
            log.error("问题操作日志发送失败",e);
        }
    }
}

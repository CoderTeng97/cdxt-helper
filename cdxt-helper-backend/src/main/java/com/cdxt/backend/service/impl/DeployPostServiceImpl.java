package com.cdxt.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.dao.DeployPostMapper;
import com.cdxt.backend.model.DeployPost;
import com.cdxt.backend.model.DeployPostSrc;
import com.cdxt.backend.pojo.dto.DeployPostDTO;
import com.cdxt.backend.pojo.dto.DeployPostQueryDTO;
import com.cdxt.backend.pojo.vo.DeployViewVO;
import com.cdxt.backend.service.DeployPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.backend.service.DeployPostSrcService;
import com.cdxt.backend.service.SMSService;
import com.cdxt.backend.service.UserService;
import com.cdxt.common.enums.MailType;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.utils.IdWorker;
import com.cdxt.common.utils.SVNUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
@Slf4j
@Service
public class DeployPostServiceImpl extends ServiceImpl<DeployPostMapper, DeployPost> implements DeployPostService {
    @Autowired
    IdWorker idWorker;
    @Autowired
    DeployPostSrcService deployPostSrcService;
    @Autowired
    SMSService smsService;
    @Autowired
    UserService userService;
    @Autowired
    SVNUtil svnUtil;

    @Value("${svn.config.ip}")
    private String svnClientIp;
    @Value("${svn.src.master}")
    private String masterSrcPrifix;
    @Value("${svn.src.branch}")
    private String branchSrcPrifix;
    @Value("${svn.src.temp}")
    private String svnTempDir;


    @Override
    public Boolean increaseDeployPost(DeployPostDTO deployPostDTO) {
        Boolean isPost = false;
        //装配补丁发布对象
        DeployPost entity = new DeployPost();
        entity.setId(idWorker.nextId() + "");
        entity.setHospitalId(deployPostDTO.getHospitalId());
        entity.setBranch(deployPostDTO.getBranch());
        entity.setRemark(deployPostDTO.getRemark());
        entity.setPostLevel(deployPostDTO.getPostLevel());
        entity.setIsAuto(deployPostDTO.getIsAuto());
        entity.setIsFullyDeploy(deployPostDTO.getIsFullyDeploy());
        entity.setPUid(deployPostDTO.getPUid());
        entity.setDUid(deployPostDTO.getDUid());
        //校验路径是否存在 TODO
        //String srcTempPrifix = entity.getBranch().trim().equals("master") ? masterSrcPrifix : branchSrcPrifix + entity.getBranch();
        //进行路径的关联设置
        List<DeployPostSrc> srcList = new ArrayList<>();
        try {
            for (int i = 0; i < deployPostDTO.getFileSrcList().size(); i++) {
                String tempSrc = deployPostDTO.getFileSrcList().get(i);
                //判断路径是否在SVN中存在
                //svnUtil.downloadFile("svn://" + svnClientIp + srcTempPrifix, "/" + tempSrc, svnTempDir + '/' + entity.getId());
                DeployPostSrc src = new DeployPostSrc();
                src.setPid(entity.getId());
                src.setSrc("\\" + tempSrc);
                srcList.add(src);
            }
            //生成压缩包到文件服务器
//            File svnTempFile = new File(svnTempDir + "/" + entity.getId());
//            ZipUtil.pack(svnTempFile, new File(svnTempDir + "/" + entity.getId() + ".zip"));
//            svnTempFile.deleteOnExit();
            //FIXME 此处会出现事务不一致性，需测试并做事务处理
            boolean insertFlag = deployPostSrcService.saveBatch(srcList);
            int insertCount = baseMapper.insert(entity);
            if (insertFlag == true && insertCount == 1) {
                isPost = true;
                sendNoticMail(entity);
            } else {
                throw new ResponseCommonException(HttpStatus.BAD_REQUEST, "插入失败，稍后尝试");
            }
        } catch (ResponseCommonException e) {
            throw new ResponseCommonException(e.getHttpStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("补丁发布西信息失败", e);
            throw new ResponseCommonException(HttpStatus.INTERNAL_SERVER_ERROR, "发布信息失败");
        }
        return isPost;
    }


    void generateDeployPostZIP(List<DeployPostSrc> deployPostSrcList) {

    }


    @Override
    public IPage<DeployViewVO> getDeployViewList(DeployPostQueryDTO dto) {
        List<DeployViewVO> records  =  baseMapper.selectPageByQueryDTO(dto);
        IPage iPage = new Page(dto.getPageNum(),dto.getPageSize());
        iPage.setTotal(baseMapper.selectCountPageByQueryDTO(dto));
        iPage.setRecords(records);
        iPage.setPages(iPage.getPages());
        return iPage;
    }

    @Override
    public Boolean udpateDeployState(String id, Integer state) {
        UpdateWrapper<DeployPost> wrapper = new UpdateWrapper<>();
        wrapper.set(true, "state", state).eq("id", id);
        return this.update(wrapper);
    }

    @Override
    public void getDeployPatchesWithZip(String patchId, OutputStream outputStream) {
        ZipUtil.pack(new File(svnTempDir + "/" + patchId), outputStream);
    }


    /**
     * 发送补丁通知邮件
     *
     * @param deployPost
     */
    void sendNoticMail(DeployPost deployPost) {
        try {
            String email = userService.getUserEmail(deployPost.getDUid());
            //邮箱不为空发送
            if (!StringUtils.isEmpty(email)) {
                //获取处理人邮箱
                String title = "补丁任务通知（" + (deployPost.getPostLevel() == 1 ? "紧急" : "一般") + ")";
                StringBuffer html = new StringBuffer();
                html.append("id:" + deployPost.getId())
                        .append("\n补丁详情:" + deployPost.getRemark())
                        .append("\n所属分支:" + deployPost.getBranch())
                        .append("\n发布时间:" + LocalDateTime.now().toString());
                smsService.sendMail(email, title, html.toString(), MailType.NOTIC);

            }
        } catch (Exception e) {
            log.warn("补丁通知邮件发送错误", e);
        }

    }


}
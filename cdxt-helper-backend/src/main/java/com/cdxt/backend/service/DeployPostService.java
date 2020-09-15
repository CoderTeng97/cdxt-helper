package com.cdxt.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.DeployPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdxt.backend.pojo.dto.DeployPostDTO;
import com.cdxt.backend.pojo.dto.DeployPostQueryDTO;
import com.cdxt.backend.pojo.vo.DeployViewVO;

import java.io.OutputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface DeployPostService extends IService<DeployPost> {
    /**
     * 发布部署信息
     * @param deployPost
     * @return
     */
    Boolean increaseDeployPost(DeployPostDTO deployPost);

    /**
     * 获取列表
     * @param dto
     * @return
     */
    IPage<DeployViewVO> getDeployViewList(DeployPostQueryDTO dto);

    /**
     * 更新状态
     * @param id
     * @param state
     * @return
     */
    Boolean udpateDeployState(String id,Integer state);


    void getDeployPatchesWithZip(String patchId,OutputStream outputStream);

    /**
     * 设置处理人员
     * @param deployPostId 问题id
     * @param dUid 处理人员id
     * @return
     */
    Boolean setDUser(String deployPostId,String dUid);
}

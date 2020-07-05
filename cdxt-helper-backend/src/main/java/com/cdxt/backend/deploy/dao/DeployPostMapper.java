package com.cdxt.backend.deploy.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.deploy.model.DeployPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.deploy.pojo.dto.DeployPostQueryDTO;
import com.cdxt.backend.deploy.pojo.vo.DeployViewVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface DeployPostMapper extends BaseMapper<DeployPost> {
    IPage<DeployViewVO> selectPageByQueryDTO(Page page,@Param("dto") DeployPostQueryDTO dto);


    long selectCountPageByQueryDTO(@Param("dto") DeployPostQueryDTO dto);
}

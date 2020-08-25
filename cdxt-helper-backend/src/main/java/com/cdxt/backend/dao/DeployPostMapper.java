package com.cdxt.backend.dao;

import com.cdxt.backend.model.DeployPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.pojo.dto.DeployPostQueryDTO;
import com.cdxt.backend.pojo.vo.DeployViewVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface DeployPostMapper extends BaseMapper<DeployPost> {
    List<DeployViewVO> selectPageByQueryDTO(@Param("dto") DeployPostQueryDTO dto);


    long selectCountPageByQueryDTO(@Param("dto") DeployPostQueryDTO dto);
}

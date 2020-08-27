package com.cdxt.backend.dao;

import com.cdxt.backend.model.Issues;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.vo.IssuesDetailVO;
import com.cdxt.backend.pojo.vo.IssuesViewVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
public interface IssuesMapper extends BaseMapper<Issues> {

    /**
     *
     * @param id
     * @return
     */
    IssuesDetailVO selectById(String id);
    IssuesViewVO selectViewColumnById(String id);

    /**
     * 分页查询
     * @param dto
     * @return
     */
    List<IssuesViewVO> selectPageByQueryDTO(IssuesQueryDTO dto);

    Long selectPageByQueryDTOCount(IssuesQueryDTO dto);

}

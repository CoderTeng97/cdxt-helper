package com.cdxt.backend.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.model.WatchList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.pojo.vo.WatcherVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-08-27
 */
public interface WatchListMapper extends BaseMapper<WatchList> {
    List<WatcherVO> selectViewByPage();
}

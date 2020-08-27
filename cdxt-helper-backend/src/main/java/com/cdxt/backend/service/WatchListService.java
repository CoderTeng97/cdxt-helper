package com.cdxt.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.WatchList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdxt.backend.pojo.vo.WatcherVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-27
 */
public interface WatchListService extends IService<WatchList> {
    /**
     * 获取值班人员列表
     * @return
     */
    List<WatcherVO> getWatcher();

    /**
     * 设置值班人员
     * @param uid
     * @param endTime
     * @param  type
     * @return
     */
    Boolean  setWather(String uid, LocalDateTime endTime,String type);
}

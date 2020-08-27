package com.cdxt.backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.model.WatchList;
import com.cdxt.backend.dao.WatchListMapper;
import com.cdxt.backend.pojo.vo.WatcherVO;
import com.cdxt.backend.service.WatchListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-27
 */
@Service
public class WatchListServiceImpl extends ServiceImpl<WatchListMapper, WatchList> implements WatchListService {


    @Override
    public List<WatcherVO> getWatcher() {
        return baseMapper.selectViewByPage();
    }

    @Override
    public Boolean setWather(String uid, LocalDateTime endTime,String type) {
        WatchList watchList = new WatchList();
        watchList.setType(type);
        watchList.setGmtEndTime(endTime);
        watchList.setUid(uid);
        return this.save(watchList);
    }
}

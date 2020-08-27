package com.cdxt.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.model.IssuesOpLog;
import com.cdxt.backend.dao.IssuesOpLogMapper;
import com.cdxt.backend.service.IssuesOpLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
@Service
public class IssuesOpLogServiceImpl extends ServiceImpl<IssuesOpLogMapper, IssuesOpLog> implements IssuesOpLogService {

    @Override
    public IPage<IssuesOpLog> getHistoryIssuesOpLog(Long pageNum,Long pageSize) {
        QueryWrapper<IssuesOpLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("gmt_create");
        IPage<IssuesOpLog> iPage = this.page(new Page<>(pageNum,pageSize),queryWrapper);
        return iPage;
    }
}

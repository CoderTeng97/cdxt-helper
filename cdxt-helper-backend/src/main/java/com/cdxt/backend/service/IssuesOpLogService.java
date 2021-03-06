package com.cdxt.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.IssuesOpLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
public interface IssuesOpLogService extends IService<IssuesOpLog> {

    IPage<IssuesOpLog> getHistoryIssuesOpLog(Long pageNum,Long pageSize);

}

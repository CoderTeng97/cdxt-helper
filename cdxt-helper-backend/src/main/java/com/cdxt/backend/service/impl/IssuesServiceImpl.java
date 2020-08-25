package com.cdxt.backend.service.impl;

import com.cdxt.backend.model.Issues;
import com.cdxt.backend.dao.IssuesMapper;
import com.cdxt.backend.service.IssuesService;
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
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues> implements IssuesService {

}

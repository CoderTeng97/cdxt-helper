package com.cdxt.backend.pojo.vo;

import com.cdxt.backend.model.Issues;
import com.cdxt.backend.model.IssuesOpLog;
import lombok.Data;

import java.util.List;

@Data
public class IssuesDetailVO {
    /**
     * 问题标题
     */
    private String id;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题详情
     */
    private String detail;


    private String hospitalName;

    /**
     * 分类
     */
    private String classification;

    /**
     * 处理用户
     */
    private String dUser;

    /**
     * 校验用户
     */
    private String vUser;

    /**
     * 发布用户
     */
    private String pUser;
    /**
     * 状态
     */
    private String state;
    /**
     * 问题任务操作日志
     */
    private List<IssuesOpLog> opLogs;
}

package com.cdxt.backend.pojo.vo;

import com.cdxt.backend.model.Issues;
import com.cdxt.backend.model.IssuesOpLog;
import lombok.Data;

import java.time.LocalDateTime;
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

    /**
     * 优先级代码
     */
    private String priorityCode;


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

    private String module;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 完成时间
     */
    private LocalDateTime gmtCompletion;

    /**
     * 问题任务操作日志
     */
    private List<IssuesOpLog> opLogs;
}

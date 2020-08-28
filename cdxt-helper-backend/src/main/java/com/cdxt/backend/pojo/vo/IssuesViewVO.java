package com.cdxt.backend.pojo.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.IssuesOpLog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssuesViewVO {

    /**
     * 问题id
     */
    private String id;
    /**
     * 问题标题
     */
    private String title;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 所属分支
     */
    private String hospitalBranch;

    /**
     * 优先级代码
     */
    private String priorityCode;

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
     * 所属模块
     */
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
     * 记录总条数
     */
    @JsonIgnore(value = false)
    private  Integer recordsTotal;

}

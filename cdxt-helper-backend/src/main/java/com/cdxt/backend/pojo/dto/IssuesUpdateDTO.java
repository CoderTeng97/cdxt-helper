package com.cdxt.backend.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
@Data
@ApiModel(description = "售后问题更新DTO")
public class IssuesUpdateDTO implements Serializable {

    /**
     * 问题
     */
    @ApiModelProperty(value = "问题id" ,required =  true)
    private String id;

    /**
     * 问题标题
     */
    @ApiModelProperty(value = "问题标题" ,required =  false)
    private String title;

    /**
     * 问题详情
     */
    @ApiModelProperty(value = "问题详情" ,required =  false)
    private String detail;

    /**
     * 分类
     */
    @ApiModelProperty(value = "问题标题" ,required =  false)
    private String classification;

    /**
     * 优先级代码
     */
    @ApiModelProperty(value = "优先级代码" ,required =  false)
    private String priorityCode;

    /**
     * 处理用户
     */
    @ApiModelProperty(value = "处理用户" ,required =  false)
    private String dUid;

    /**
     * 校验用户
     */
    @ApiModelProperty(value = "校验用户" ,required =  false)
    private String vUid;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态" ,required =  false)
    private String state;

    /**
     * 所属模块
     */
    @ApiModelProperty(value = "所属模块" ,required =  false)
    private String module;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间" ,required =  false)
    private LocalDateTime gmtCompletion;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除" ,required =  false)
    private Integer isDel;

    @ApiModelProperty(value = "反馈内容" ,required =  false)
    private String feedBackText;

    public String getdUid() {
        return dUid;
    }

    public void setdUid(String dUid) {
        this.dUid = dUid;
    }

    public String getvUid() {
        return vUid;
    }

    public void setvUid(String vUid) {
        this.vUid = vUid;
    }
}

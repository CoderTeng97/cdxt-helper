package com.cdxt.backend.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class IssuesPostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题标题
     */
    @ApiModelProperty(value = "问题标题" ,required =  true)
    private String title;

    /**
     * 问题详情
     */
    @ApiModelProperty(value = "问题详情" ,required =  true)
    private String detail;


    /**
     * 分类
     */
    @ApiModelProperty(value = "医院" ,required =  true)
    private String hospitalId;


    /**
     * 优先级代码
     */
    @ApiModelProperty(value = "优先级代码" ,required =  true)
    private String priorityCode;


    /**
     * 发布用户
     */
    @ApiModelProperty(value = "发布用户" ,required =  false)
    private String pUid;


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
     * 所属模块
     */
    @ApiModelProperty(value = "所属模块" ,required =  true)
    private String module;

    /**
     * 是否补录问题
     */
    @ApiModelProperty(value = "是否补录问题" ,required =  false)
    private Boolean isAdditional = false;


}

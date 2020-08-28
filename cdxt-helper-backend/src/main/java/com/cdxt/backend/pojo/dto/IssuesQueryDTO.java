package com.cdxt.backend.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class IssuesQueryDTO implements Serializable {
    /**
     * 问题标题
     */
    @ApiModelProperty(value = "问题标题" ,required =  false)
    private String title;
    /**
     * 分类
     */
    @ApiModelProperty(value = "分类" ,required =  false)
    private String classification;

    /**
     * 优先级代码
     */
    @ApiModelProperty(value = "优先级代码" ,required =  false)
    private String priorityCode;

    /**
     * 用户
     */
    @ApiModelProperty(value = "关联用户id" ,required =  false)
    private String uid;

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


    @ApiModelProperty(value = "创建时间" ,required =  false)
    private LocalDateTime gmtCreate;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间" ,required =  false)
    private LocalDateTime gmtCompletion;

    /**
     * 是否有效
     */
    @ApiModelProperty(value = "是否有效" ,required =  false)
    private Integer isDel = 0;

    /**
     * 是否有效
     */
    @ApiModelProperty(value = "页码" ,required =  false)
    private Integer pageNum = 1;

    /**
     * 是否有效
     */
    @ApiModelProperty(value = "分页大小" ,required =  false)
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序sql" ,required =  false)
    private String  sortSql= "state,priority_code,gmt_create desc";



}

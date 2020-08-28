package com.cdxt.backend.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_issues")
public class Issues implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题详情
     */
    private String detail;

    private String hospitalId;

    /**
     * 分类
     */
    private String classification;

    /**
     * 优先级代码
     */
    private String priorityCode;

    /**
     * 处理用户
     */
    private String dUid;

    /**
     * 校验用户
     */
    private String vUid;

    /**
     * 发布用户
     */
    private String pUid;

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
     * 是否有效
     */
    private Integer isDel;

}

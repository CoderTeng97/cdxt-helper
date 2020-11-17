package com.cdxt.backend.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author TengGang
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_deploy_post")
public class DeployPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 发布分支
     */
    private String branch;

    /**
     * 备注
     */
    private String remark;

    /**
     *补丁详情
     */
    private String detail;

    /**
     * 0 一般 1 紧急
     */
    private Integer postLevel;

    /**
     * 是否自动部署 0否 1 是
     */
    private Integer isAuto;

    /**
     * 是否完全部署
     */
    private Integer isFullyDeploy;

    /**
     * 0 未处理 1 处理中 2 驳回 3 处理完成
     */
    private Integer state;

    /**
     * 发布补丁人员
     */
    private String pUid;

    /**
     * 补丁处理人员
     */
    private String dUid;

    /**
     * 补丁发布核实责任人员
     */
    private String vUid;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    private Integer isDel;


}

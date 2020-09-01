package com.cdxt.backend.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-06-14
 */
@Data
public class UserViewVO   {


    private String id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 角色
     */
    private String role;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 负责模块
     */
    private String module;

    /**
     * 部门
     */
    private String dept;

    /**
     * 电话
     */
    private String phone;


}

package com.cdxt.backend.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserUpdateDTO {

    @NotNull
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
     * 负责模块
     */
    private String module;

    private String dept;
}

package com.cdxt.backend.deploy.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

/**'
 * 登录返回对象
 */
@Data
public class UserLoginVO {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户角色
     */
    private String role;
}

package com.cdxt.backend.deploy.service;

import com.cdxt.backend.deploy.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdxt.backend.deploy.pojo.dto.UserLoginDTO;
import com.cdxt.common.pojo.vo.UserBaseVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface UserService extends IService<User> {
    Boolean increaseUser(User user);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    UserBaseVO login(UserLoginDTO userLoginDTO);


    /**
     * 获取用户列表
     * @param trueName
     * @param role
     * @return
     */
    List<Map<String,Object>> getUserList(String trueName,String role);

    /**
     * 获取用户邮箱
     * @param uid
     * @return
     */
    String getUserEmail(String uid);


}

package com.cdxt.backend.deploy.controller;

import com.cdxt.backend.deploy.pojo.dto.UserLoginDTO;
import com.cdxt.backend.deploy.model.User;
import com.cdxt.backend.deploy.service.UserService;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.pojo.vo.UserBaseVO;
import com.cdxt.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerResponseProcessor
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
     * 用户登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public UserBaseVO login(@RequestBody UserLoginDTO dto) {
        return  userService.login(dto);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PutMapping("/register")
    public Boolean register(@RequestBody User user) {
        return userService.increaseUser(user);
    }


    /**
     * 获取用户列表
     * @param text
     * @param role
     * @return
     */
    @GetMapping
    public List<Map<String, Object>> getUserList(
            @RequestParam(required = false)
            String text,
            @RequestParam(required = false)
            String role
    ){
        return  userService.getUserList(text,role);
    }

}

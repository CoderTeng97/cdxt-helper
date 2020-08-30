package com.cdxt.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.WatchList;
import com.cdxt.backend.pojo.dto.UserLoginDTO;
import com.cdxt.backend.model.User;
import com.cdxt.backend.pojo.vo.WatcherVO;
import com.cdxt.backend.service.UserService;
import com.cdxt.backend.service.WatchListService;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import com.cdxt.common.pojo.vo.ResponseListVO;
import com.cdxt.common.pojo.vo.UserBaseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@ControllerResponseProcessor
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    WatchListService watchListService;


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
     * @param text 用户搜索文本
     * @param role 角色
     * @return
     */
    @GetMapping("/list")
    public List<Map<String, Object>> getUserList(
            @RequestParam(required = false)
                    String text,
            @RequestParam(required = false)
                    String role
    ){
        return  userService.getUserList(text,role);
    }

    @ApiOperation("获取值班人员列表")
    @GetMapping("/watcher/list")
    public List<WatcherVO> getWatcher(){
        List<WatcherVO>  result = watchListService.getWatcher();
        return  result;
    }

    @ApiOperation("设置值班人员")
    @GetMapping("/watcher/set")
    public Boolean setWatcher(
            @RequestParam String uid,
            @RequestParam LocalDateTime gmtEndTime,
            @RequestParam(required = false,defaultValue = "1" ) String type
    ){

        return watchListService.setWather(uid,gmtEndTime,type);
    }

}

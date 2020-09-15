package com.cdxt.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.convert.UserConvert;
import com.cdxt.backend.model.Issues;
import com.cdxt.backend.model.WatchList;
import com.cdxt.backend.pojo.dto.UserLoginDTO;
import com.cdxt.backend.model.User;
import com.cdxt.backend.pojo.dto.UserUpdateDTO;
import com.cdxt.backend.pojo.vo.DeployViewVO;
import com.cdxt.backend.pojo.vo.UserViewVO;
import com.cdxt.backend.pojo.vo.WatcherVO;
import com.cdxt.backend.service.UserService;
import com.cdxt.backend.service.WatchListService;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import com.cdxt.common.enums.UserRole;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResponseListVO;
import com.cdxt.common.pojo.vo.UserBaseVO;
import com.cdxt.common.utils.IdWorker;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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
    @Autowired
    IdWorker idWorker;
    @Autowired
    UserConvert userConvert;

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
        UserBaseVO userBaseVO = getUserInfo();
        if (!UserRole.ADMIN.equals(UserRole.valueOf(userBaseVO.getRole()))){
            throw new ResponseCommonException(HttpStatus.BAD_REQUEST,"您不是管理员，不具备注册权限");
        }
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


    /**
     * 获取用户列表
     * @param pageNum
     * @param pageSize
     * @param trueName
     * @return
     */
    @ApiOperation("获取或搜索用户分页列表")
    @GetMapping("/page")
    public ResponseListVO<UserViewVO> getUserPageList(
            @RequestParam(required = false,defaultValue = "1")
                    Integer pageNum,
            @RequestParam(required = false,defaultValue = "10")
                    Integer pageSize,
            @RequestParam(required = false)
                    String trueName
    ){
        IPage<UserViewVO> iPage = userService.getUserPageInfo(pageNum,pageSize,trueName);
        ResponseListVO<UserViewVO> vo = new ResponseListVO<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal(),iPage.getRecords());
        return vo;
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

    /**
     * 重置密码
     * @return
     */
    @PutMapping("/resetPassword")
    public Boolean resetPassword(@RequestBody Map<String,String> req){
        String oldPassword = req.get("oldPassword");
        String newPassword = req.get("newPassword");
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(oldPassword) ){
            throw  new ResponseCommonException(HttpStatus.BAD_REQUEST,"密码不能为空");
        }
        return userService.resetPassword(oldPassword,newPassword,getUid());
    }

    /**
     * 用户信息更新
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public Boolean update(@RequestBody  UserUpdateDTO dto){
        UserBaseVO userBaseVO = getUserInfo();
        if(!UserRole.ADMIN.equals(UserRole.valueOf(userBaseVO.getRole()))){
            throw  new ResponseCommonException("您不是管理员，不具备修改用户权限");
        }
        User user = userConvert.updateParams2Entity(dto);
        return  userService.updateById(user);
    }
}

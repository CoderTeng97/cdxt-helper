package com.cdxt.common.base;


import com.cdxt.common.pojo.vo.UserBaseVO;
import com.cdxt.common.utils.JwtTokenUtil;
import com.cdxt.common.utils.SpringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/*/**
 * @Author: TengGang
 * @Date: 2020/6/7 2:03
 * @Description:  通用controller
 */
public abstract class BaseController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /*
     * 获取uid
     * @return
     */
    public String getUid(){
        HttpServletRequest request = SpringUtil.getHttpServletRequest();
        String token = request.getHeader("X-Token");
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        Optional<String> uid =  Optional.ofNullable((String) claims.get("uid"));
        if (uid.isPresent()){
            return uid.get();
        }else{
            throw new RuntimeException(this.getClass().getName() + ":  uid 为空");
        }
    }


    public UserBaseVO getUserInfo(){
        HttpServletRequest request = SpringUtil.getHttpServletRequest();
        String token = request.getHeader("X-Token");
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        Optional<String> uid =  Optional.ofNullable((String) claims.get("uid"));
        UserBaseVO userBaseVO = new UserBaseVO();
        userBaseVO.setId((String) claims.get("uid"));
        userBaseVO.setRole((String) claims.get("role"));
        if (Optional.ofNullable(userBaseVO).isPresent()){
            return userBaseVO;
        }else{
            throw new RuntimeException(this.getClass().getName() + ":  用户信息为空");
        }
    }
}



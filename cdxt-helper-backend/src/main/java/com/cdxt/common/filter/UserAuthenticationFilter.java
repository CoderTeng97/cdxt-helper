package com.cdxt.common.filter;



import com.cdxt.common.utils.FilterResponseUtil;
import com.cdxt.common.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    public UserAuthenticationFilter() {
    }

    public UserAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //log.info(httpServletRequest.getRemoteUser() + "请求地址：" + httpServletRequest.getRequestURI());
        /**用户凭证检查*/
        String token = httpServletRequest.getHeader("X-Token");
        try {
            if (StringUtils.isEmpty(token)) {
                FilterResponseUtil.response("无效的登录凭证", HttpStatus.FORBIDDEN);
                return;
            }

        } catch (Throwable throwable) {
            FilterResponseUtil.response("亲，系统内部错误，请稍后重试~", HttpStatus.INTERNAL_SERVER_ERROR);
            logger.error("doFilterInternal 用户凭证检查错误 ->", throwable);
            return;
        }
        try {
            jwtTokenUtil.getAllClaimsFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            FilterResponseUtil.response("无效的登录凭证,请登录后重试!", HttpStatus.FORBIDDEN);
            return;
        }
        doFilter(httpServletRequest, httpServletResponse, filterChain);
        /**判断用户是否有访问权限*/
    }
}

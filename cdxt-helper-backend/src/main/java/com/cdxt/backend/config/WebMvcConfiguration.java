package com.cdxt.backend.config;

import com.cdxt.common.filter.CustomCorsFilter;
import com.cdxt.common.filter.UserAuthenticationFilter;
import com.cdxt.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebMvcConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .formLogin().disable()
                .httpBasic().disable();

        //校验Token
        http.addFilterBefore(new UserAuthenticationFilter(jwtTokenUtil),
                UsernamePasswordAuthenticationFilter.class);
        // 添加CORS过滤器
        http.addFilterAfter(new CustomCorsFilter(), UserAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) {
        // AuthenticationTokenFilter will ignore the below paths
        // 放行swagger 和静态资源

        web.ignoring().antMatchers("/swagger-ui.html",
                "/",
                "/v2/api-docs",
                "/configuration/**",
                "/swagger*/**",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.svg",
                "/static/*/**",
                "/user/login",
                "/user/registry",
                "/wss/**",
                "/file/upload",
                "/user/register",
                "/login"
        );

        web.ignoring().antMatchers(HttpMethod.GET,
                "/user/isExistUserName",
                "/user/isExistEmail"
        );

    }
}

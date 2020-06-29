package com.cdxt.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: TengGang
 * @Date: 2020/3/29
 */
@Component
public class PasswordUtil {

    @Value("${pwd.secret}")
    private String secret;

    @Autowired
    private  BCryptPasswordEncoder encoder;

    public String encode(String password){
        String pwdPrifix = password.substring(0,password.length() / 2);
        String pwdSuffix = password.substring(password.length() / 2);
        return  encoder.encode(pwdPrifix + secret + pwdSuffix);
    }

}

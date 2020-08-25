package com.cdxt.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan( basePackages = {"com.cdxt"})
@MapperScan(basePackages = {"com.cdxt.backend.deploy.dao"})
public class CdxtHelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(CdxtHelperApplication.class,args);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}

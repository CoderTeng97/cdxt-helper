package com.cdxt.backend;

import com.cdxt.backend.websockt.AfterSafeIssueWebsocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan( basePackages = {"com.cdxt"})
@MapperScan(basePackages = {"com.cdxt.backend.dao"})
public class CdxtHelperApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =  SpringApplication.run(CdxtHelperApplication.class,args);

        /**
         * websockt 注入spring 容器
         */
        AfterSafeIssueWebsocket.setApplicationContext(configurableApplicationContext);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}

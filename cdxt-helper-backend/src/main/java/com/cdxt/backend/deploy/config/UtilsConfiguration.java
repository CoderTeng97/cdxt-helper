package com.cdxt.backend.deploy.config;

import com.cdxt.common.utils.SVNUtil;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class UtilsConfiguration {

    @Bean(name = "SVNUtil")
    protected  SVNUtil svnUtilRegister(){
        return  new SVNUtil("tenggang","123456");
    }
}

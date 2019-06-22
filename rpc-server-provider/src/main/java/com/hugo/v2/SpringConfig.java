package com.hugo.v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * created by xuyahui on 2019/6/18
 */

@Configuration
@ComponentScan(basePackages = "com.hugo")
public class SpringConfig {

    @Bean(name="gpRpcServer")
    public GpRpcServer gpRpcServer(){
        return new GpRpcServer(8080);
    }

}

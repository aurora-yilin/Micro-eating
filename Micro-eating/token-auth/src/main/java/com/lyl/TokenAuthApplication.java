package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author lyl
 * @Date 2020/9/26 9:58
 */

@EnableWebSecurity
@MapperScan("com.lyl.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class TokenAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(TokenAuthApplication.class, args);
    }
}

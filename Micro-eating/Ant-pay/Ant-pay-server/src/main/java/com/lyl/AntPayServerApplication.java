package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AntPayServerApplication
 *
 * @author lyl
 * @date 2020/11/7 16:42
 * @since 1.0.0
 **/
@MapperScan("com.lyl.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class AntPayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntPayServerApplication.class,args);
    }
}

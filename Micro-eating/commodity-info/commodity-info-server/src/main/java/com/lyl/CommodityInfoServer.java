package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * CommodityInfoServer
 *
 * @author lyl
 * @date 2020/11/16 17:21
 * @since 1.0.0
 **/
@MapperScan("com.lyl.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class CommodityInfoServer {
    public static void main(String[] args) {
        SpringApplication.run(CommodityInfoServer.class,args);
    }
}

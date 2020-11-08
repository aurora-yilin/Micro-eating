package com.lyl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * teseConfig
 *
 * @author lyl
 * @date 2020/11/8 16:38
 * @since 1.0.0
 **/

@RestController
@RefreshScope
public class TestController {

    @Value("${nacos.age}")
    private String age;

    @GetMapping("/test")
    public String testconfig1(){
        return age;
    }
}

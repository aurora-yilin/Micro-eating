package com.lyl.config.utilConfig;

import com.lyl.properties.RedisConstant;
import com.lyl.properties.VerificationCodeConstant;
import com.lyl.utils.VerificationCodeUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author lyl
 * @Date 2020/9/28 15:11
 */
@Configuration
public class VerificationCodeUtilConfig {

    @Resource
    private VerificationCodeConstant verificationCodeConstant;

    @Resource
    private RedisConstant redisConstant;

    @Bean("createImageVerificationCodeUtil")
    @RefreshScope
    public VerificationCodeUtil createImageVerificationCodeUtil(){
        return new VerificationCodeUtil.Builder()
                .setWidth(verificationCodeConstant.getWidth())
                .setHeight(verificationCodeConstant.getHeight())
                .setCharString(verificationCodeConstant.getCharString())
                .setCharLength(verificationCodeConstant.getCharLength())
                .build();
    }

    @Bean("createSmsCodeUtil")
    @RefreshScope
    public VerificationCodeUtil createSmsCodeUtil(){
        return new VerificationCodeUtil.Builder()
                .setCharLength(redisConstant.getCharLength())
                .setCharString(redisConstant.getCharString())
                .build();
    }
}

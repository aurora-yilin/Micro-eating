package com.lyl.config.utilConfig;

import com.lyl.properties.RedisConstant;
import com.lyl.properties.VerificationCodeConstant;
import com.lyl.utils.VerificationCodeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyl
 * @Date 2020/9/28 15:11
 */
@Configuration
public class VerificationCodeUtilConfig {

    @Bean("createImageVerificationCodeUtil")
    public VerificationCodeUtil createImageVerificationCodeUtil(){
        return new VerificationCodeUtil.Builder()
                .setWidth(VerificationCodeConstant.width)
                .setHeight(VerificationCodeConstant.height)
                .setCharString(VerificationCodeConstant.charString)
                .setCharLength(VerificationCodeConstant.charLength)
                .build();
    }

    @Bean("createSmsCodeUtil")
    public VerificationCodeUtil createSmsCodeUtil(){
        return new VerificationCodeUtil.Builder()
                .setCharLength(RedisConstant.charLength)
                .setCharString(RedisConstant.charString)
                .build();
    }
}

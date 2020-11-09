package com.lyl.config.utilConfig;

import com.lyl.properties.TokenConstant;
import com.lyl.utils.TokenUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author lyl
 * @Date 2020/9/28 15:03
 */
@Configuration
public class TokenConfig {

    @Resource
    private TokenConstant tokenConstant;

    @Bean("createTokenUtil")
    @RefreshScope
    public TokenUtil createTokenUtil(){
        return new TokenUtil.Builder()
                .setHeader(tokenConstant.getHeader())
                .setSecret(tokenConstant.getSecret())
                .setExpiration(tokenConstant.getExpiration())
                .build();
    }

}

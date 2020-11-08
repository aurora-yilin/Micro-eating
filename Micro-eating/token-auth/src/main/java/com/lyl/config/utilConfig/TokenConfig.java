package com.lyl.config.utilConfig;

import com.lyl.properties.TokenConstant;
import com.lyl.utils.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyl
 * @Date 2020/9/28 15:03
 */
@Configuration
public class TokenConfig {

    @Bean("createTokenUtil")
    public TokenUtil createTokenUtil(){
        return new TokenUtil.Builder()
                .setHeader(TokenConstant.header)
                .setSecret(TokenConstant.secret)
                .setExpiration(TokenConstant.expiration)
                .build();
    }

}

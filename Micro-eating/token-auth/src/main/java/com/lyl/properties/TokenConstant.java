package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:33
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "token")
public class TokenConstant {
    /**
     * 签名密钥
     */
    public static String secret="lyltokenAuth";
    /**
     * 有效期多少秒
     */
    public static Long expiration=10800000L;

    /**
     * 携带token的请求头
     */
    public static String header = "tokenHeader";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        TokenConstant.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        TokenConstant.expiration = expiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        TokenConstant.header = header;
    }
}

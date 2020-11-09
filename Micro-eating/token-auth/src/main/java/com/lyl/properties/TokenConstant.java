package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:33
 */
@Component
@ConfigurationProperties(prefix = "token")
public class TokenConstant {
    /**
     * 签名密钥
     */
    private String secret="lyltokenAuth";
    /**
     * 有效期多少秒
     */
    private Long expiration=10800000L;

    /**
     * 携带token的请求头
     */
    private String header = "tokenHeader";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

package com.lyl.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.lyl.properties.AlipayConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * AlipayConfig
 *
 * @author lyl
 * @date 2020/11/15 14:56
 * @since 1.0.0
 **/
@Configuration
public class AlipayConfig {

    @Resource
    AlipayConstant alipayConstant;

    @Bean
    public AlipayClient createAlipayClient(){
        return new DefaultAlipayClient(alipayConstant.getGatewayUrl(),
                alipayConstant.getAppId(),
                alipayConstant.getAppPrivateKey(),
                alipayConstant.getFormat(),
                alipayConstant.getCharset(),
                alipayConstant.getAlipayPublicKey(),
                alipayConstant.getSigntype());
    }
}

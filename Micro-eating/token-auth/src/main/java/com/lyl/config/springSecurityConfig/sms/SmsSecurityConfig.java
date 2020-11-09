package com.lyl.config.springSecurityConfig.sms;

import com.lyl.config.springSecurityConfig.GeneralAuthenticationFailureHandler;
import com.lyl.config.springSecurityConfig.GeneralAuthenticationSuccessHandler;
import com.lyl.enums.CommonEnum;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.utils.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lyl
 * @Date 2020/9/29 16:56
 */
@Configuration
@Component
public class SmsSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource(name = "createTokenUtil")
    private TokenUtil tokenUtil;

//    @Resource
//    SmsAuthenticationProvider smsAuthenticationProvider;

    @Resource
    private LoginParametersConstant loginParametersConstant;
//    @Resource
//    SmsCheckLoginVerificationCodeFilter smsCheckLoginVerificationCodeFilter;

    @Resource
    UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter(loginParametersConstant);
        smsAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        smsAuthenticationFilter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());

//        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        builder.authenticationProvider(createSmsAuthenticationProvider());

//        builder.addFilterBefore(smsCheckLoginVerificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterAfter(smsAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler(){
        return new GeneralAuthenticationFailureHandler.Builder()
                .setCode(CommonEnum.CLIENTERROR.getCode())
                .setIsok(true)
                .setData(null)
                .setMsg(null)
                .build();

    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new GeneralAuthenticationSuccessHandler.Builder()
                .setCode(CommonEnum.SUCCESS.getCode())
                .setIsok(true)
                .setMsg("认证成功")
                .setTokenUtil(tokenUtil)
                .build();
    }

//    private TokenUtil getTokenUtil(){
//        return new TokenUtil.Builder()
//                .setSecret(TokenConstant.secret)
//                .setHeader(TokenConstant.header)
//                .setExpiration(TokenConstant.expiration)
//                .build();
//    }

    @Bean
    public SmsAuthenticationProvider createSmsAuthenticationProvider(){
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);
        return smsAuthenticationProvider;
    }
}

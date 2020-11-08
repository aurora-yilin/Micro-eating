package com.lyl.config.springSecurityConfig.sms;

import com.alibaba.druid.util.StringUtils;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.InputIsIllegalException;
import com.lyl.exception.VerificationCodeException;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 需要使用filter com.lyl.config.springSecurityConfig.sms.filter.SmsCheckLoginVerificationCodeFilter配合进行短信验证码的验证
 * 会抛出java.lang.IllegalStateException: getWriter() has already been called for this response异常，不建议使用
 * @author lyl
 * @Date 2020/10/2 15:47
 */
public class SmsAuthenticationProvider2 implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         * 将参数强转类型为SmsAuthenticationToken类型
         */
        SmsAuthenticationToke authenticationToken = (SmsAuthenticationToke) authentication;
        /**
         * 从强转后的参数中取出填充的电话号码
         * 并调用userDetails.loadUserByUsername()方法取出认证用户信息
         */
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法根据手机号获取到用户信息");
        }
        /**
         * 创建认证后的Authentication,将权限和
         */
        SmsAuthenticationToke authenticationResult = new SmsAuthenticationToke(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return createSuccessAuthentication(userDetails,
                userDetails.getAuthorities(),
                authenticationToken.getDetails());
    }

    private Authentication createSuccessAuthentication(Object principal,
                                                       Collection<? extends GrantedAuthority> authorities,
                                                       Object Details){
        SmsAuthenticationToke authenticationResult = new SmsAuthenticationToke(principal,authorities);
        authenticationResult.setDetails(Details);
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToke.class
                .isAssignableFrom(authentication));
    }

}

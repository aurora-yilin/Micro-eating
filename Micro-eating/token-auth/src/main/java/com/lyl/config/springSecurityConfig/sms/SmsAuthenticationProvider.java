package com.lyl.config.springSecurityConfig.sms;

import com.alibaba.druid.util.StringUtils;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.InputIsIllegalException;
import com.lyl.exception.VerificationCodeException;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 将短信验证码的验证过程和认证过程整合在一起，推荐使用
 * @author lyl
 * @Date 2020/9/28 23:07
 */
//@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisConstant redisConstant;

    private UserDetailsService userDetailsService;

    @Resource
    private LoginParametersConstant loginParametersConstant;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try{
            Boolean result = validate(new ServletWebRequest(request));
            if (result) {
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
                SmsAuthenticationToke authenticationResult = new SmsAuthenticationToke(userDetails, userDetails.getAuthorities());
                authenticationResult.setDetails(authenticationToken.getDetails());
                return createSuccessAuthentication(userDetails,
                        userDetails.getAuthorities(),
                        authenticationToken.getDetails());
            }
            else {
                throw new AuthenticationServiceException("验证码校验失败");
            }
        }catch (RuntimeException e){
            throw new AuthenticationServiceException(e.getMessage());
        }



//        /**
//         * 将参数强转类型为SmsAuthenticationToken类型
//         */
//        SmsAuthenticationToke authenticationToken = (SmsAuthenticationToke) authentication;
//        /**
//         * 从强转后的参数中取出填充的电话号码
//         * 并调用userDetails.loadUserByUsername()方法取出认证用户信息
//         */
//        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
//        if (userDetails == null) {
//            throw new InternalAuthenticationServiceException("无法根据手机号获取到用户信息");
//        }
//        /**
//         * 创建认证后的Authentication,将权限和
//         */
//        SmsAuthenticationToke authenticationResult = new SmsAuthenticationToke(userDetails,userDetails.getAuthorities());
//        authenticationResult.setDetails(authenticationToken.getDetails());
//        return createSuccessAuthentication(userDetails,
//                userDetails.getAuthorities(),
//                authenticationToken.getDetails());
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


    @RefreshScope
    private Boolean validate(ServletWebRequest request) throws RuntimeException{
//        String mobileCode = ServletRequestUtils.getStringParameter(request.getRequest(),
//                LoginParametersConstant.mobileCode);
//        String mobileNum = ServletRequestUtils.getStringParameter(request.getRequest(),
//                LoginParametersConstant.mobileNum);
        String mobileCode = request.getParameter(loginParametersConstant.getMobileCode());
        String mobileNum = request.getParameter(loginParametersConstant.getMobileNum());
        if(StringUtils.isEmpty(mobileCode) || StringUtils.isEmpty(mobileNum)){
            throw new InputIsIllegalException(CommonEnum.CLIENTERROR.getCode(),"输入的验证码或手机号码为空");
        }
        else if (!redisTemplate.hasKey(redisConstant.getSmsCode()+":"+mobileNum)){
            throw new VerificationCodeException(CommonEnum.SERVERERROR.getCode(),"验证码不存在,请检查手机号码是否正确");
        }
        else if ((redisTemplate.getExpire(redisConstant.getSmsCode()+":"+mobileNum, TimeUnit.SECONDS))<3){
            redisTemplate.delete(redisConstant.getSmsCode()+":"+mobileNum);
            throw new VerificationCodeException(CommonEnum.SERVERERROR.getCode(),"验证码已过期");
        }

        String result = (String)redisTemplate.opsForValue().get(redisConstant.getSmsCode() + ":" + mobileNum);
        if(StringUtils.equalsIgnoreCase(result,mobileCode)){
            redisTemplate.delete(redisConstant.getSmsCode()+":"+mobileNum);
            return true;
        }
        return false;
    }
}

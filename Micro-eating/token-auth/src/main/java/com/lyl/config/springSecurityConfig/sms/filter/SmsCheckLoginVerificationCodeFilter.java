package com.lyl.config.springSecurityConfig.sms.filter;

import com.alibaba.druid.util.StringUtils;
import com.lyl.config.springSecurityConfig.GeneralAuthenticationFailureHandler;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.InputIsIllegalException;
import com.lyl.exception.VerificationCodeException;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import org.apache.tomcat.util.http.RequestUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lyl
 * @Date 2020/9/30 22:24
 */
@Component
public class SmsCheckLoginVerificationCodeFilter extends OncePerRequestFilter {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException, AuthenticationException{
        if (StringUtils.equals(LoginParametersConstant.smsLoginProcessingUrl, request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(),"post")) {
            try{
                Boolean result = validate(new ServletWebRequest(request));
                if (result){
                    filterChain.doFilter(request, response);
                }
            }catch (RuntimeException e){
                new GeneralAuthenticationFailureHandler.Builder()
                        .setCode(200)
                        .setIsok(true)
                        .setData(null)
                        .setMsg(null)
                        .build()
                        .onAuthenticationFailure(request, response, new AuthenticationException(e.getMessage()) {
                        });
                return;
//                throw new AuthenticationServiceException(e.getMessage());
            }
        }
        filterChain.doFilter(request,response);

    }

    private Boolean validate(ServletWebRequest request) throws ServletRequestBindingException {
        String mobileCode = ServletRequestUtils.getStringParameter(request.getRequest(),
                LoginParametersConstant.mobileCode);
        String mobileNum = ServletRequestUtils.getStringParameter(request.getRequest(),
                LoginParametersConstant.mobileNum);
        if(StringUtils.isEmpty(mobileCode) || StringUtils.isEmpty(mobileNum)){
            throw new InputIsIllegalException(CommonEnum.CLIENTERROR.getCode(),"输入的验证码或手机号码为空");
        }
        else if (!redisTemplate.hasKey(RedisConstant.smsCode+":"+mobileNum)){
            throw new VerificationCodeException(CommonEnum.SERVERERROR.getCode(),"验证码不存在,请检查手机号码是否正确");
        }
        else if ((redisTemplate.getExpire(RedisConstant.smsCode+":"+mobileNum, TimeUnit.SECONDS))<3){
            redisTemplate.delete(RedisConstant.smsCode+":"+mobileNum);
            throw new VerificationCodeException(CommonEnum.SERVERERROR.getCode(),"验证码已过期");
        }

        String result = (String)redisTemplate.opsForValue().get(RedisConstant.smsCode + ":" + mobileNum);
        if(StringUtils.equalsIgnoreCase(result,mobileCode)){
            redisTemplate.delete(RedisConstant.smsCode+":"+mobileNum);
            return true;
        }
        return false;
    }

}

package com.lyl.config.springSecurityConfig.sms.filter;

import com.alibaba.druid.util.StringUtils;
import com.lyl.config.springSecurityConfig.GeneralAuthenticationFailureHandler;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.InputIsIllegalException;
import com.lyl.exception.VerificationCodeException;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lyl
 * @Date 2020/9/30 22:24
 */
//@Component
//@RefreshScope
public class SmsCheckLoginVerificationCodeFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisConstant redisConstant;

    @Resource
    private LoginParametersConstant loginParametersConstant;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException, AuthenticationException{
        if (StringUtils.equals(loginParametersConstant.getSmsLoginProcessingUrl(), request.getRequestURI())
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
                loginParametersConstant.getMobileCode());
        String mobileNum = ServletRequestUtils.getStringParameter(request.getRequest(),
                loginParametersConstant.getMobileNum());
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

package com.lyl.config.springSecurityConfig;

import com.alibaba.druid.util.StringUtils;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 重写DaoAuthenticationProvider的additionalAuthenticationChecks方法
 * 添加了验证码的验证机制
 * @author lyl
 * @Date 2020/9/28 17:27
 */

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisConstant redisConstant;

    @Resource
    private LoginParametersConstant loginParametersConstant;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            final Boolean result = validate(req, userDetails.getUsername());
            if(result){
                super.additionalAuthenticationChecks(userDetails, authentication);
            }
        }catch (AuthenticationServiceException e){
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @RefreshScope
    private Boolean validate(HttpServletRequest request,String userName) throws AuthenticationServiceException{
        String code = request.getParameter(loginParametersConstant.getLoginCode());
        if(StringUtils.isEmpty(code)){
            throw new AuthenticationServiceException("验证码为空");
        }
        else if (!redisTemplate.hasKey(redisConstant.getImageCode()+":"+userName)){
            throw new AuthenticationServiceException("验证码不存在");
        }
        else if ((redisTemplate.getExpire(redisConstant.getImageCode()+":"+userName, TimeUnit.SECONDS))<0){
            redisTemplate.delete(redisConstant.getImageCode() +":"+userName);
            throw new AuthenticationServiceException("验证码已过期");
        }
        String result = (String)redisTemplate.opsForValue().get(redisConstant.getImageCode() + ":" + userName);
        if (StringUtils.equalsIgnoreCase(code,result)) {
            redisTemplate.delete(redisConstant.getImageCode()+":"+userName);
            return true;
        }
        else{
            throw new AuthenticationServiceException("验证码校验失败");
        }
    }
}

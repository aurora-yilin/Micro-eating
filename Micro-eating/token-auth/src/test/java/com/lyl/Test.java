package com.lyl;

import com.lyl.properties.LoginParametersConstant;
import com.lyl.properties.RedisConstant;
import com.lyl.properties.TokenConstant;
import com.lyl.properties.VerificationCodeConstant;
import com.lyl.utils.TokenUtil;
import com.lyl.utils.VerificationCodeUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author lyl
 * @Date 2020/10/2 13:15
 */
@SpringBootTest
public class Test {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    TokenConstant tokenConstant;

    @Resource
    RedisConstant redisConstant;

    @Resource
    VerificationCodeConstant verificationCodeConstant;

    @Resource
    LoginParametersConstant loginParametersConstant;
//    @Resource
//    TokenUtil tokenUtil;

    @org.junit.jupiter.api.Test
    public void test(){
        System.out.println(redisTemplate.getExpire(redisConstant.getImageCode()+":"+"admin", TimeUnit.MINUTES));
        System.out.println(redisTemplate.getExpire(redisConstant.getSmsCode()+":"+"18538765325",TimeUnit.MINUTES));
    }

    @org.junit.jupiter.api.Test
    public void testToken(){
        TokenUtil tokenUtil = new TokenUtil.Builder()
                .setSecret(tokenConstant.getSecret())
                .setHeader(tokenConstant.getHeader())
                .setExpiration(tokenConstant.getExpiration())
                .build();

        Collection authority = new ArrayList();
        authority.add("root");
        authority.add("guest");
        String token = tokenUtil.generateToken("admin", authority);
        String userName = tokenUtil.getUserNameFromToken(token);
        Collection auth = tokenUtil.getPermissionsFromToken(token);
        String newToken = tokenUtil.refreshToken(token);
        System.out.println(userName);
        System.out.println(auth);
        System.out.println(newToken);
    }

    @org.junit.jupiter.api.Test
    public void testBuilder(){
        VerificationCodeUtil build = new VerificationCodeUtil.Builder()
                .setWidth(verificationCodeConstant.getWidth())
                .setHeight(verificationCodeConstant.getHeight())
                .setCharString(verificationCodeConstant.getCharString())
                .setCharLength(verificationCodeConstant.getCharLength())
                .build();
        System.out.println(build);
    }
}

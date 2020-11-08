package com.lyl;

import com.lyl.properties.RedisConstant;
import com.lyl.properties.TokenConstant;
import com.lyl.utils.TokenUtil;
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

//    @Resource
//    TokenUtil tokenUtil;

    @org.junit.jupiter.api.Test
    public void test(){
        System.out.println(redisTemplate.getExpire(RedisConstant.imageCode+":"+"admin", TimeUnit.MINUTES));
        System.out.println(redisTemplate.getExpire(RedisConstant.smsCode+":"+"18538765325",TimeUnit.MINUTES));
    }

    @org.junit.jupiter.api.Test
    public void testToken(){
        TokenUtil tokenUtil = new TokenUtil.Builder()
                .setSecret(TokenConstant.secret)
                .setHeader(TokenConstant.header)
                .setExpiration(TokenConstant.expiration)
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
}

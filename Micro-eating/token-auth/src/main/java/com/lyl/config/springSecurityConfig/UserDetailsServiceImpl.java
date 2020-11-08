package com.lyl.config.springSecurityConfig;

import com.lyl.entity.UserDetailsImpl;
import com.lyl.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lyl
 * @Date 2020/9/28 21:47
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl userDetails = userMapper.selectUserByUnOrPn(username);
        if (userDetails != null) {
            return userDetails;
        }else {
            /**
             * 若没有找到对应的user用户则抛出springSecurity内置的UsernameNOtFoundException异常
             */
            throw new UsernameNotFoundException("user:" + username + "not found");
        }
    }
}

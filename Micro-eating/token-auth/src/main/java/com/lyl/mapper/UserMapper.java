package com.lyl.mapper;

import com.lyl.entity.UserDetailsImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/9/28 14:27
 */
@Component
@Mapper
public interface UserMapper {
    /**
     * 通过userName或者是phoneNum查询用户的信息
     * @param principal
     * @return
     */
    public UserDetailsImpl selectUserByUnOrPn(String principal);

    public Integer judgeMobileNumIsExist(String mobileNum);
}

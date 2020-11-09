package com.lyl.controller;

import com.alibaba.druid.util.StringUtils;
import com.lyl.common.ResultType;
import com.lyl.config.springSecurityConfig.UserDetailsServiceImpl;
import com.lyl.enums.CommonEnum;
import com.lyl.utils.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author lyl
 * @Date 2020/10/2 16:55
 */
@RestController
public class TokenController {

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 刷新token的过期时间
     * @param token
     * @return
     */
    @PostMapping("/refreshToken")
    public ResultType refreshToken(@RequestHeader("tokenHeader") String token){
        boolean result = StringUtils.isEmpty(token);
        if (result) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),CommonEnum.CLIENTERROR.getMsg(),null);
        }else {
            String newToken;
            try {
                newToken = tokenUtil.refreshToken(token);
            } catch (ExpiredJwtException e) {
                throw e;
            } catch (UnsupportedJwtException e) {
                throw e;
            } catch (MalformedJwtException e) {
                throw e;
            } catch (SignatureException e) {
                throw e;
            } catch (IllegalArgumentException e) {
                throw e;
            }
            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(), CommonEnum.SUCCESS.getMsg(), newToken);
        }
    }

    /**
     * 验证Token是否被篡改，并判断Token是否过期
     * @param token
     * @return
     */
    @PostMapping("/validationToken")
    public ResultType validationToken(@RequestHeader("tokenHeader")String token){
        boolean result = StringUtils.isEmpty(token);
        if (result) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),CommonEnum.CLIENTERROR.getMsg(),null);
        }else {
            String userNameFromToken;
            Collection permissionsFromToken;
            Boolean tokenExpired;
            try {
                tokenExpired = tokenUtil.isTokenExpired(token);
                userNameFromToken = tokenUtil.getUserNameFromToken(token);
                permissionsFromToken = tokenUtil.getPermissionsFromToken(token);
            } catch (ExpiredJwtException e) {
                throw e;
            } catch (UnsupportedJwtException e) {
                throw e;
            } catch (MalformedJwtException e) {
                throw e;
            } catch (SignatureException e) {
                throw e;
            } catch (IllegalArgumentException e) {
                throw e;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromToken);
            Collection<String> authorities = userDetails.getAuthorities().stream().map((authority) ->
                    authority.getAuthority())
                    .collect(Collectors.toList());
            if (tokenExpired) {
                return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(), "认证失败", false);
            } else if (permissionsFromToken.retainAll(authorities) ||
                    authorities.retainAll(permissionsFromToken) || authorities.size() != permissionsFromToken.size()) {
                return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(), "认证通过", true);
            } else {
                return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(), "认证失败", false);
            }
        }
    }

    /**
     * 用户第一次访问网站的时候调用该接口返回一个专属于该匿名用户的token用于标记临时菜单
     * @return
     */
    @GetMapping("/anonymityToken")
    public ResultType getAnonymityToken(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        String tempToken = tokenUtil.generateToken(replace, null);
        return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),tempToken);
    }

    @PostMapping("/ParsingToken")
    public ResultType ParsingTokenGetUserNameAndPermissions(@RequestHeader("tokenHeader")String token){
        boolean result = StringUtils.isEmpty(token);
        if (result) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),CommonEnum.CLIENTERROR.getMsg(),null);
        }else {
            String userNameFromToken = null;
            Collection permissionsFromToken = null;
            try {
                userNameFromToken = tokenUtil.getUserNameFromToken(token);
                permissionsFromToken = tokenUtil.getPermissionsFromToken(token);
            } catch (ExpiredJwtException e) {
                throw e;
            } catch (UnsupportedJwtException e) {
                throw e;
            } catch (MalformedJwtException e) {
                throw e;
            } catch (SignatureException e) {
                throw e;
            } catch (IllegalArgumentException e) {
                throw e;
            }
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName", userNameFromToken);
            userInfo.put("permissions", permissionsFromToken);
            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(), CommonEnum.SUCCESS.getMsg(), userInfo);
        }
    }
}

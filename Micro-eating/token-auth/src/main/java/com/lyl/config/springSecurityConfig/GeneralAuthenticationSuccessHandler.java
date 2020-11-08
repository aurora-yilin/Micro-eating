package com.lyl.config.springSecurityConfig;

import com.lyl.common.ResultType;
import com.lyl.entity.UserDetailsImpl;
import com.lyl.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author oRuol
 * @Date 2020/6/23 19:26
 * 认证成功处理器的实现
 */
@Component
@Slf4j
public class GeneralAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Boolean isok;
    private int code;
    private String msg;
    private Object data;
    private TokenUtil tokenUtil;


    public GeneralAuthenticationSuccessHandler(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.isok = builder.isok;
        this.msg = builder.msg;
        this.tokenUtil = builder.tokenUtil;
    }

    public GeneralAuthenticationSuccessHandler() {
    }

    public static final class Builder {
        private Boolean isok;
        private int code;
        private String msg;
        private Object data;
        private TokenUtil tokenUtil;

        public Builder(Boolean isok, int code, String msg, Object data,TokenUtil tokenUtil) {
            this.isok = isok;
            this.code = code;
            this.msg = msg;
            this.data = data;
            this.tokenUtil = tokenUtil;
        }

        public Builder() {
        }

        public Builder setIsok(Boolean isok) {
            this.isok = isok;
            return this;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }

        public Builder setTokenUtil(TokenUtil tokenUtil) {
            this.tokenUtil = tokenUtil;
            return this;
        }

        public GeneralAuthenticationSuccessHandler build() {
            return new GeneralAuthenticationSuccessHandler(this);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String principal = ((UserDetailsImpl)authentication.getPrincipal()).getUserName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Collection<String> newauthorities = authorities.stream().map((authority)->
            authority.getAuthority()
        ).collect(Collectors.toList());

        String token = tokenUtil.generateToken(principal, newauthorities);
        ResultType.COMMONRESULT(isok, code, msg, token, response);
    }
}


package com.lyl.config.springSecurityConfig;

import com.alibaba.druid.util.StringUtils;
import com.lyl.common.ResultType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyl
 * @Date 2020/9/30 23:03
 */
public class GeneralAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private Boolean isok;
    private int code;
    private String msg;
    private Object data;

    public GeneralAuthenticationFailureHandler(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.isok = builder.isok;
        this.msg = builder.msg;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (StringUtils.isEmpty(msg)) {
            ResultType.COMMONRESULT(isok,code,exception.getMessage(),data,response);
        }
        else {
            ResultType.COMMONRESULT(isok, code, msg, data, response);
        }
    }

    public static final class Builder{
        private Boolean isok;
        private int code;
        private String msg;
        private Object data;

        public Builder(Boolean isok, int code, String msg, Object data) {
            this.isok = isok;
            this.code = code;
            this.msg = msg;
            this.data = data;
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

        public GeneralAuthenticationFailureHandler build(){
            return new GeneralAuthenticationFailureHandler(this);
        }
    }
}

package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 15:26
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "login.parameter")
public class LoginParametersConstant {

    public static String mobileCode="mobileCode";
    public static String mobileNum="mobileNum";
    public static String smsLoginProcessingUrl="/smsLogin";

    public static String loginProcessingUrl="/login";
    public static String usernameParameter="userName";
    public static String passwordParameter="password";
    public static String loginCode="code";

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        LoginParametersConstant.loginCode = loginCode;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        LoginParametersConstant.mobileCode = mobileCode;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        LoginParametersConstant.mobileNum = mobileNum;
    }

    public String getSmsLoginProcessingUrl() {
        return smsLoginProcessingUrl;
    }

    public void setSmsLoginProcessingUrl(String smsLoginProcessingUrl) {
        LoginParametersConstant.smsLoginProcessingUrl = smsLoginProcessingUrl;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        LoginParametersConstant.loginProcessingUrl = loginProcessingUrl;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        LoginParametersConstant.usernameParameter = usernameParameter;
    }

    public static String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        LoginParametersConstant.passwordParameter = passwordParameter;
    }
}

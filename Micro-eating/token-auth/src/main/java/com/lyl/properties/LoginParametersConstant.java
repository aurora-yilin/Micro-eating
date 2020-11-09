package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 15:26
 */
//@RefreshScope
@Component
@ConfigurationProperties(prefix = "login.parameter")
public class LoginParametersConstant {

    private String mobileCode="mobileCode";
    private String mobileNum="mobileNum";
    private String smsLoginProcessingUrl="/smsLogin";

    private String loginProcessingUrl="/login";
    private String usernameParameter="userName";
    private String passwordParameter="password";
    private String loginCode="code";

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getSmsLoginProcessingUrl() {
        return smsLoginProcessingUrl;
    }

    public void setSmsLoginProcessingUrl(String smsLoginProcessingUrl) {
        this.smsLoginProcessingUrl = smsLoginProcessingUrl;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }
}

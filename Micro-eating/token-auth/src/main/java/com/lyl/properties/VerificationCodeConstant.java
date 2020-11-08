package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:34
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "kaptcha")
public class VerificationCodeConstant {
    /**
     * 生成验证码图像的宽度默认宽度为150
     */
    public static String width = "150";
    /**
     * 生成验证码图像的高度默认高度为50
     */
    public static String height = "50";
    /**
     * 生成验证码的字符组成默认字符组成为 0-9
     */
    public static String charString = "0123456789";
    /**
     * 生成的验证码长度默认验证码位数为四位
     */
    public static String charLength = "4";

    public void setWidth(String width) {
        VerificationCodeConstant.width = width;
    }

    public void setHeight(String height) {
        VerificationCodeConstant.height = height;
    }

    public void setCharString(String charString) {
        VerificationCodeConstant.charString = charString;
    }

    public void setCharLength(String charLength) {
        VerificationCodeConstant.charLength = charLength;
    }
}

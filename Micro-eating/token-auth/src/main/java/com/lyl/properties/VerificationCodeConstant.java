package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:34
 */
@Component
//@Data
@ConfigurationProperties(prefix = "image.code")
public class VerificationCodeConstant {
    /**
     * 生成验证码图像的宽度默认宽度为150
     */
    private String width = "150";
    /**
     * 生成验证码图像的高度默认高度为50
     */
    private String height = "50";
    /**
     * 生成验证码的字符组成默认字符组成为 0-9
     */
    private String charString = "0123456789";
    /**
     * 生成的验证码长度默认验证码位数为四位
     */
    private String charLength = "4";

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCharString() {
        return charString;
    }

    public void setCharString(String charString) {
        this.charString = charString;
    }

    public String getCharLength() {
        return charLength;
    }

    public void setCharLength(String charLength) {
        this.charLength = charLength;
    }
}

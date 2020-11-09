package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:32
 */
@Component
@ConfigurationProperties(prefix = "redis.constant")
public class RedisConstant {

    private String smsCode = "smsCode";

    private String charString = "0123456789";

    private String charLength = "6";

    private String imageCode = "imageCode";

    private String smsCodeExpire = "15";

    private String imageCodeExpire = "5";

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
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

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getSmsCodeExpire() {
        return smsCodeExpire;
    }

    public void setSmsCodeExpire(String smsCodeExpire) {
        this.smsCodeExpire = smsCodeExpire;
    }

    public String getImageCodeExpire() {
        return imageCodeExpire;
    }

    public void setImageCodeExpire(String imageCodeExpire) {
        this.imageCodeExpire = imageCodeExpire;
    }
}

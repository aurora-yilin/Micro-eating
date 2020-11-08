package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lyl
 * @Date 2020/10/1 9:32
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "redis.constant")
public class RedisConstant {

    public static String smsCode = "smsCode";

    public static String charString = "0123456789";

    public static String charLength = "6";

    public static String imageCode = "imageCode";

    public static String smsCodeExpire = "15";

    public static String imageCodeExpire = "5";

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        RedisConstant.smsCode = smsCode;
    }

    public String getCharString() {
        return charString;
    }

    public void setCharString(String charString) {
        RedisConstant.charString = charString;
    }

    public String getCharLength() {
        return charLength;
    }

    public void setCharLength(String charLength) {
        RedisConstant.charLength = charLength;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        RedisConstant.imageCode = imageCode;
    }

    public String getSmsCodeExpire() {
        return smsCodeExpire;
    }

    public void setSmsCodeExpire(String smsCodeExpire) {
        RedisConstant.smsCodeExpire = smsCodeExpire;
    }

    public String getImageCodeExpire() {
        return imageCodeExpire;
    }

    public void setImageCodeExpire(String imageCodeExpire) {
        RedisConstant.imageCodeExpire = imageCodeExpire;
    }
}

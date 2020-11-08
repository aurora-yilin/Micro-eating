package com.lyl.utils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

/**
 * @author lyl
 * @Date 2020/9/27 15:37
 */
public class VerificationCodeUtil {

    private String width;

    private String height;

    private String charString;

    private String charLength;


    public VerificationCodeUtil(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.charString = builder.charString;
        this.charLength = builder.charLength;
    }

    /**
     * 产生一个Producer对象用于创建验证码图像和获取验证码内容
     * @return
     */
    public Producer verifyCode(){
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", width);
        properties.setProperty("kaptcha.image.height", height);
        properties.setProperty("kaptcha.textproducer.char.string", charString);
        properties.setProperty("kaptcha.textproducer.char.length", charLength);
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }


    /**
     * 建造者模式创建VerificationCodeUtils类
     */
    public static final class Builder{

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

        public Builder(String width, String height, String charString, String charLength) {
            this.width = width;
            this.height = height;
            this.charString = charString;
            this.charLength = charLength;
        }

        public Builder() {
        }

        public Builder setWidth(String width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(String height) {
            this.height = height;
            return this;
        }

        public Builder setCharString(String charString) {
            this.charString = charString;
            return this;
        }

        public Builder setCharLength(String charLength) {
            this.charLength = charLength;
            return this;
        }

        public VerificationCodeUtil build(){
            return new VerificationCodeUtil(this);
        }
    }
}

package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AlipayConstant
 *
 * @author lyl
 * @date 2020/11/15 14:44
 * @since 1.0.0
 **/
@Component
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayConstant {

    /**
     * 商户应用id
     */
    private String appId;

    /**
     * 支付宝gatewayUrl
     */
    private String gatewayUrl;

    /**
     * RSA私钥，用于对商户请求报文加签
     */
    private String appPrivateKey;

    /**
     * 支付宝RSA公钥，用于验签支付宝应答
     */
    private String alipayPublicKey;

    /**
     * 异步地址
     */
    private String notifyUrl;

    /**
     * 同步地址
     */
    private String returnUrl;

    /**
     * 编码
     */
    private String charset;

    /**
     * 格式
     */
    private String format;

    /**
     * 签名类型
     */
    private String signtype;

    /**
     * 订单超时时间
     */
    private String timeoutExpress;

    /**
     * 产品类型
     */
    private String productCode;

    /**
     * subject
     * @return
     */
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }
}

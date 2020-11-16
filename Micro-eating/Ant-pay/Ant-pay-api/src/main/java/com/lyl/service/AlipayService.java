package com.lyl.service;

import com.alipay.api.AlipayApiException;
import com.lyl.common.ResultType;

import javax.servlet.http.HttpServletRequest;

/**
 * AlipayService
 *
 * @author lyl
 * @date 2020/11/15 15:05
 * @since 1.0.0
 **/
public interface AlipayService {

    /**
     *@Description: 创建支付宝订单
     * @param orderNo 订单编号
     * @param amount 实际支付金额
     * @param body 订单描述
     * @return
     * @throws AlipayApiException
     */
    String createOrder(String orderNo, double amount, String body) throws AlipayApiException;

    /**
     *
     * @param tradeStatus 支付宝交易状态
     * @param orderNo 订单编号
     * @param tradeNo 支付宝订单号
     * @return
     */
    boolean notify(String tradeStatus, String orderNo, String tradeNo);

    /**
     *@Description: 校验签名
     * @param request
     * @return
     */
    boolean rsaCheckV1(HttpServletRequest request);

    /**
     * @Description: 退款
     * @param orderNo 订单编号
     * @param amount 实际支付金额
     * @param refundReason 退款原因
     * @return
     */
    ResultType refund(String orderNo, double amount, String refundReason);

    String test();
}

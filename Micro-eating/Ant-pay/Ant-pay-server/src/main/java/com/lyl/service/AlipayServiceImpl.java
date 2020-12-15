package com.lyl.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.properties.AlipayConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * AlipayServiceImpl
 *
 * @author lyl
 * @date 2020/11/15 15:13
 * @since 1.0.0
 **/
@Slf4j
@DubboService
public class AlipayServiceImpl implements AlipayService{

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private AlipayConstant alipayConstant;

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @param orderNo 订单编号
     * @param amount 实际支付金额
     * @param body 订单描述
     * @return
     * @throws AlipayApiException
     * @throws UnsupportedEncodingException
     */
    @Override
    public String createOrder(String orderNo, double amount, String body) throws AlipayApiException, UnsupportedEncodingException {

        /**AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
        ali_request.setNotifyUrl(alipayConstant.getNotifyUrl());// 设置异步回调地址
        ali_request.setReturnUrl(alipayConstant.getReturnUrl());// 设置同步回调地址
        if(null!=String.valueOf(amount)){
            AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
            //订单号
            alipayTradeAppPayModel.setOutTradeNo(orderNo);
            //订单金额
            alipayTradeAppPayModel.setTotalAmount(String.valueOf(amount));
            //商品名称
            alipayTradeAppPayModel.setSubject("【微膳食】");
            //商品信息
            alipayTradeAppPayModel.setBody(body);
            //设置订单超时时间
            alipayTradeAppPayModel.setTimeoutExpress("1c");
            //设置产品代码
            alipayTradeAppPayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
            //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数
            String passback_params	 = "{ab=测试一下;tdst=公共参数;ccsd=gds；dfa=23·12}";
            String passback_params2 = URLEncoder.encode(passback_params,"UTF-8");
            alipayTradeAppPayModel.setPassbackParams(passback_params2);

            ali_request.setBizModel(alipayTradeAppPayModel);
//        AlipayTradeAppPayResponse ali_response = alipayClient.sdkExecute(ali_request);
            String result = alipayClient.pageExecute(ali_request).getBody();
            //就是orderString 可以直接给客户端请求，无需再做处理。
            return result;
        }
        return "error/error_5xx";*/


        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConstant.getReturnUrl());//同步
        alipayRequest.setNotifyUrl(alipayConstant.getNotifyUrl());//异步

        String out_trade_no = orderNo; //订单号
        String total_amount = String.valueOf(amount);//付款金额，必填
        String subject = "【微膳食】";  //订单名称，必填
        String subjectTest = alipayConstant.getSubject();
        String bodys = body;//商品描述，可空
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";
        String productCode = "FAST_INSTANT_TRADE_PAY";
        String productCodeTest = alipayConstant.getProductCode();

        String bizContent = "{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                //     + "\"goods_detail\":[{\"goods_id\":\""+ id +"\",\"goods_name\":\""+ kind +"\"}],"
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}";
        if(null!=total_amount) { //支付金额不等于空
            alipayRequest.setBizContent(bizContent);
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;
        }
        return "error/error_5xx";
    }

    @Override
    public boolean notify(String tradeStatus, String orderNo, String tradeNo) {
        if ("TRADE_FINISHED".equals(tradeStatus)
                || "TRADE_SUCCESS".equals(tradeStatus)) {
            // 支付成功，根据业务逻辑修改相应数据的状态
            boolean state = true;
            // boolean state = orderPaymentService.updatePaymentState(orderNo, tradeNo);
            if (state) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean rsaCheckV1(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean verifyResult = AlipaySignature.rsaCheckV2(params,
                    alipayConstant.getAlipayPublicKey(),
                    alipayConstant.getCharset(),
                    alipayConstant.getSigntype());
            return verifyResult;
        } catch (AlipayApiException e) {
            log.debug("verify sigin error, exception is:{}", e);
            return false;
        }
    }

    @Override
    public ResultType refund(String orderNo, double amount, String refundReason) {
        if(StringUtils.isBlank(orderNo)){
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"订单编号不能为空",null);
        }
        if(amount <= 0){
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"退款金额必须大于0",null);
        }

        AlipayTradeRefundModel model=new AlipayTradeRefundModel();
        // 商户订单号
        model.setOutTradeNo(orderNo);
        // 退款金额
        model.setRefundAmount(String.valueOf(amount));
        // 退款原因
        model.setRefundReason(refundReason);
        // 退款订单号(同一个订单可以分多次部分退款，当分多次时必传)
        // model.setOutRequestNo(UUID.randomUUID().toString());
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        alipayRequest.setBizModel(model);
        AlipayTradeRefundResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            log.error("订单退款失败，异常原因:{}", e);
        }
        if(alipayResponse != null){
            String code = alipayResponse.getCode();
            String subCode = alipayResponse.getSubCode();
            String subMsg = alipayResponse.getSubMsg();
            if("10000".equals(code)
                    && StringUtils.isBlank(subCode)
                    && StringUtils.isBlank(subMsg)){
                // 表示退款申请接受成功，结果通过退款查询接口查询
                // 修改用户订单状态为退款
                orderService.updateOrderByOrderId(3, orderNo);
                return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),"订单退款成功",null);
            }
            return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),subCode + ":" + subMsg,null);
        }
        return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),"订单退款失败",null);
    }

    @Override
    public String test() {
        return "-----------alipaytest----------";
    }
}

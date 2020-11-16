package com.lyl.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.properties.AlipayConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Service
public class AlipayServiceImpl implements AlipayService{

    @Resource
    AlipayClient alipayClient;

    @Resource
    AlipayConstant alipayConstant;

    @Override
    public String createOrder(String orderNo, double amount, String body) throws AlipayApiException {
        AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
        //商品名称
        alipayTradeAppPayModel.setSubject(body);
        //商品信息
        alipayTradeAppPayModel.setBody(body);
        //订单号
        alipayTradeAppPayModel.setOutTradeNo(orderNo);
        //设置订单超时时间
        alipayTradeAppPayModel.setTimeoutExpress("30m");
        //订单金额
        alipayTradeAppPayModel.setTotalAmount(String.valueOf(amount));

        //设置产品代码
        alipayTradeAppPayModel.setProductCode("QUICK_MSECURITY_PAY");
        //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数
        alipayTradeAppPayModel.setPassbackParams(orderNo);

        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
        ali_request.setBizModel(alipayTradeAppPayModel);
        ali_request.setNotifyUrl(alipayConstant.getNotifyUrl());// 回调地址
        AlipayTradeAppPayResponse ali_response = alipayClient.sdkExecute(ali_request);
//        AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.pageExecute(ali_request);
        //就是orderString 可以直接给客户端请求，无需再做处理。
        return ali_response.getBody();
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

            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
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

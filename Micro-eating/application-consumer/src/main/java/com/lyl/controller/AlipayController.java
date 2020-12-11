package com.lyl.controller;

import com.alibaba.druid.util.StringUtils;
import com.alipay.api.AlipayApiException;
import com.lyl.common.ResultType;
import com.lyl.entity.Order;
import com.lyl.enums.CommonEnum;
import com.lyl.service.AlipayService;
import com.lyl.service.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * AlipayController
 *
 * @author lyl
 * @date 2020/11/15 15:38
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @DubboReference
    private AlipayService alipayService;

    @DubboReference
    private OrderService orderService;

    @GetMapping("/time")
    public String time(){
        return "43u4123492083490832time"+alipayService.test();
    }

    /**
     * 创建订单
     * @param orderNo
     * @param amount
     * @param body
     * @return
     */
    @GetMapping("/createOrder")
    public void createOrder(@RequestParam(value = "orderNo",required = false) String orderNo,
                                  @RequestParam("amount") double amount,
                                  @RequestParam("body") String body,
                                  @RequestParam(value = "userName",required = false) String userName,
                                  HttpServletResponse httpServletResponse){
        try{
            //1.验证订单是否存在
            if (!StringUtils.isEmpty(orderNo)) {
                Order order = orderService.selectOrderByOrderId(orderNo);
                if (order == null) {
                    createOrderRealization(userName,body,amount,httpServletResponse);
                }
                else{
                    ResultType.SUCCESS("订单已存在",null,httpServletResponse);
                }
            }else{
                createOrderRealization(userName,body,amount,httpServletResponse);
            }
        }catch (Exception e){
//            return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),"订单生成失败",null);
        }
    }

    private void createOrderRealization(String userName,String body,double amount,HttpServletResponse httpServletResponse) throws IOException, AlipayApiException {
        Order orderInfo = new Order();
        orderInfo.setOrderId(UUID.randomUUID().toString().replace("-",""));
        orderInfo.setUserName(userName);
        orderInfo.setOrderBody(body);
        /*设置订单为未支付状态*/
        orderInfo.setOrderStatu(2);
        orderInfo.setOrderPayMode(1);

        /* 2.创建支付宝订单 */
        Integer integer = orderService.saveOrder(orderInfo);

        String form = alipayService.createOrder(orderInfo.getOrderId(), amount, body);
        httpServletResponse.setContentType("text/html;charset=utf-8" );
        /*直接将完整的表单html输出到页面*/
        httpServletResponse.getWriter().write(form);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

    @RequestMapping("/notify")
    public ResultType notify(HttpServletRequest request){
        boolean flag = alipayService.rsaCheckV1(request);
        if (flag) {
            String tradeStatus = request.getParameter("trade_status");
            String outTradeNo = request.getParameter("out_trade_no");
            String tradeNo = request.getParameter("trade_no");

            boolean notify = alipayService.notify(tradeStatus, outTradeNo, tradeNo);

            if (notify) {
                return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),null);
            }else{
                return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),CommonEnum.ERROR.getMsg(),null);
            }
        }
        return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),CommonEnum.ERROR.getMsg(),null);
    }

    /**
     * 申请退款
     * @param orderNo
     * @param amount
     * @param refundReason
     * @return
     */
    @PostMapping("/refund")
    public ResultType refund(@RequestParam String orderNo,
                             @RequestParam double amount,
                             @RequestParam(required = false) String refundReason){
        return alipayService.refund(orderNo,amount,refundReason);
    }
}

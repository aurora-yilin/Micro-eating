package com.lyl.controller;

import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.service.AlipayService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void createOrder(@RequestParam("orderNo") String orderNo,
                                  @RequestParam("amount") double amount,
                                  @RequestParam("body") String body,
                                  HttpServletResponse httpServletResponse){
        try{
            //1.验证订单是否存在

            //2.创建支付宝订单
            String form = alipayService.createOrder(orderNo, amount, body);
//            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),order);
            httpServletResponse.setContentType("text/html;charset=utf-8" );
            httpServletResponse.getWriter().write(form);//直接将完整的表单html输出到页面
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        }catch (Exception e){
//            return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),"订单生成失败",null);
        }
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

    @PostMapping("/refund")
    public ResultType refund(@RequestParam String orderNo,
                             @RequestParam double amount,
                             @RequestParam(required = false) String refundReason){
        return alipayService.refund(orderNo,amount,refundReason);
    }
}

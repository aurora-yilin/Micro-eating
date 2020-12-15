package com.lyl.controller;

import com.lyl.common.ResultType;
import com.lyl.entity.Order;
import com.lyl.enums.CommonEnum;
import com.lyl.service.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OrderController
 *
 * @author lyl
 * @date 2020/12/8 17:14
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @DubboReference
    OrderService orderService;

    /**
     * 获取参数指定用户的全部订单信息
     * @param userName
     * @return
     */
    @PostMapping("/PsersonAllOrder")
    public ResultType getPersonAllOrder(@RequestParam("userName")String userName){
        List<Order> orders = orderService.selectOrderByUserName(userName);
        return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),orders);
    }

    /**
     * 根据订单号删除订单
     * @param orderId
     * @return
     */
    @PostMapping("/deleteOrder")
    public ResultType deleteOrderByOrderId(@RequestParam("orderId")String orderId){
        Integer integer = orderService.deleteOrderByOrderId(orderId);
        return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),integer);
    }
}

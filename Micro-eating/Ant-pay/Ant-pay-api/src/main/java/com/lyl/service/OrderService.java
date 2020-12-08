package com.lyl.service;

import com.lyl.entity.Order;

import java.util.List;

/**
 * orderService
 *
 * @author lyl
 * @date 2020/12/8 15:41
 * @since 1.0.0
 **/
public interface OrderService {
    Order selectOrderByOrderId(String orderId);

    Integer saveOrder(Order orderInfo);

    Integer updateOrderByOrderId(Integer status,String orderId);

    List<Order> selectOrderByUserName(String userName);

    Integer deleteOrderByOrderId(String orderId);
}

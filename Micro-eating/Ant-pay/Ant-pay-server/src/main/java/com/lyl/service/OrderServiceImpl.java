package com.lyl.service;

import com.lyl.entity.Order;
import com.lyl.mapper.OrderMapper;
import com.lyl.properties.AlipayConstant;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author lyl
 * @date 2020/12/8 15:42
 * @since 1.0.0
 **/
@DubboService
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    private AlipayConstant alipayConstant;

    @Override
    public Order selectOrderByOrderId(String orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    @Override
    public Integer saveOrder(Order orderInfo) {
        orderInfo.setOrderSubject(alipayConstant.getSubject());
        return orderMapper.saveOrder(orderInfo);
    }

    @Override
    public Integer updateOrderByOrderId(Integer status, String orderId) {
        return orderMapper.updateOrderByOrderId(status,orderId);
    }

    @Override
    public List<Order> selectOrderByUserName(String userName) {
        return orderMapper.selectOrderByUserName(userName);
    }


}

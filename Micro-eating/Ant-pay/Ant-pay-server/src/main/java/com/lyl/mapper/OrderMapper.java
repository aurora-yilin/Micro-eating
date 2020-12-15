package com.lyl.mapper;

import com.lyl.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * OrderMapper
 *
 * @author lyl
 * @date 2020/12/8 14:55
 * @since 1.0.0
 **/
@Mapper
public interface OrderMapper {
    Order selectOrderByOrderId(@Param("orderId") String orderId);

    Integer saveOrder(@Param("orderInfo")Order orderInfo);

    Integer updateOrderByOrderId(@Param("status") String status, @Param("orderId")String orderId);

    List<Order> selectOrderByUserName(@Param("userName")String userName);

    Integer deleteOrderByOrderId(@Param("orderId") String orderId);
}

package com.lyl.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Order
 *
 * @author lyl
 * @date 2020/12/8 15:00
 * @since 1.0.0
 **/
public class Order implements Serializable {
    private String orderId;
    private String userName;
    private String orderSubject;
    private String orderBody;
    private String orderCreateTime;
    private Integer orderStatu;
    private Integer orderPayMode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(userName, order.userName) &&
                Objects.equals(orderSubject, order.orderSubject) &&
                Objects.equals(orderBody, order.orderBody) &&
                Objects.equals(orderCreateTime, order.orderCreateTime) &&
                Objects.equals(orderStatu, order.orderStatu) &&
                Objects.equals(orderPayMode, order.orderPayMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userName, orderSubject, orderBody, orderCreateTime, orderStatu, orderPayMode);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userName='" + userName + '\'' +
                ", orderSubject='" + orderSubject + '\'' +
                ", orderBody='" + orderBody + '\'' +
                ", orderCreateTime='" + orderCreateTime + '\'' +
                ", orderStatu=" + orderStatu +
                ", orderPayMode=" + orderPayMode +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Integer getOrderStatu() {
        return orderStatu;
    }

    public void setOrderStatu(Integer orderStatu) {
        this.orderStatu = orderStatu;
    }

    public Integer getOrderPayMode() {
        return orderPayMode;
    }

    public void setOrderPayMode(Integer orderPayMode) {
        this.orderPayMode = orderPayMode;
    }
}

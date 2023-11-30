package com.lease.order.api.repository;

import com.lease.order.api.domain.Order;

import java.util.List;

public interface IOrderRepository {

    boolean create(Order order);

    Order cancel(String orderNo);

    Order findByOrderId(String orderNo);

    Order changeOrderStatus(String orderNo, String status);

    List<Order> findExpireUnPayOrders();
}

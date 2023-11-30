package com.lease.order.api.service;

import com.lease.order.api.domain.Order;
import com.lease.order.model.dto.ChangeOrderStatusRequest;
import com.lease.order.model.dto.OrderDto;

public interface IOrderService {

    Order createOrder(OrderDto dto);

    Order cancelOrder(OrderDto dto);

    Order orderDetail(String orderId);

    Object checkOrderStatus(String orderId);

    Order changeOrderStatus(ChangeOrderStatusRequest request);

    Order payOrder(String orderId);
}

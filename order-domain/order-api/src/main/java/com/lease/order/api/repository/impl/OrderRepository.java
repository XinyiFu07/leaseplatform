package com.lease.order.api.repository.impl;

import com.lease.order.api.domain.Order;
import com.lease.order.api.mapper.OrderMapper;
import com.lease.order.api.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public boolean create(Order order) {
        //写入数据库
        int rows = orderMapper.insert(order);
        return rows > 0;
    }

    @Override
    public Order cancel(String orderNo) {
        //查询订单
        Order order = findByOrderId(orderNo);
        //修改状态为取消
        order.setStatus(Order.OderStatus.CANCEL.getValue());
        //更新状态
        orderMapper.updateById(order);
        return order;
    }

    @Override
    public Order findByOrderId(String orderId) {
        //查询订单
        return orderMapper.findByOrderId(orderId);
    }

    @Override
    public Order changeOrderStatus(String orderId, String status) {
        Order order = orderMapper.findByOrderId(orderId);
        if (order == null || order.getStatus().equals(status))
            return order;
        order.setStatus(status);
        orderMapper.updateById(order);
        return order;
    }

    @Override
    public List<Order> findExpireUnPayOrders() {
        return orderMapper.findExpireUnPayOrders();
    }
}

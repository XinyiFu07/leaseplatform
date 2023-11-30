package com.lease.order.api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lease.order.api.domain.Order;
import com.lease.order.api.exception.ServiceException;
import com.lease.order.api.repository.IOrderRepository;
import com.lease.order.api.rpc.BaseinfoClient;
import com.lease.order.api.service.IOrderService;
import com.lease.order.model.dto.ChangeOrderStatusRequest;
import com.lease.order.model.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService implements IOrderService {
    @Resource
    IOrderRepository repository;
    @Resource
    BaseinfoClient baseinfoClient;

    @Override
    public Order createOrder(OrderDto dto) {
        //todo 如果订单存在就直接返回
        Order order = repository.findByOrderId(dto.getOrderId());
        if (order != null) {
            return order;
        }
        order = new Order();
        order.setOrderNo(dto.getOrderId());
        order.setGoodsId(dto.getGoodsId());
        order.setStatus(Order.OderStatus.UN_PAY.getValue());//unpaid
        order.setUserId(dto.getUserId());
        order.setCreateTime(new Date());
        //todo 锁定商品状态
        JSONObject lock = new JSONObject();
        lock.put("productid", dto.getGoodsId());
        lock.put("state", "sell");
        JSONObject info = baseinfoClient.lock(lock);
        if (info == null) {
            throw new RuntimeException("Product service There is no product information");
        }
        order.setAmount(info.getDouble("price"));

        repository.create(order);
        return order;
    }

    @Override
    public Order cancelOrder(OrderDto dto) {
        String orderId = dto.getOrderId();
        return repository.cancel(orderId);
    }

    @Override
    public Order orderDetail(String orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public Object checkOrderStatus(String orderId) {
        Order order = repository.findByOrderId(orderId);
        //return status
        return order.getStatus();
        //return pay or not
//        return "1".equals(order.getStatus());
        //return pay or not
//        return "2".equals(order.getStatus());
    }

    @Override
    public Order changeOrderStatus(ChangeOrderStatusRequest request) {
        return repository.changeOrderStatus(request.getOrderId(), request.getStatus());
    }

    @Transactional
    @Override
    public Order payOrder(String orderId) {
        Order order = repository.findByOrderId(orderId);
        if (order == null)
            throw new ServiceException("订单不存在");
        if ("2".equals(order.getStatus()) || "9".equals(order.getStatus()))
            throw new ServiceException("订单已支付或取消");
        //todo 调用下游支付服务

        //更新订单状态
        return repository.changeOrderStatus(orderId, "2");
    }
}

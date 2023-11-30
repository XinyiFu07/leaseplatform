package com.lease.order.api.controller;

import com.lease.order.api.domain.Order;
import com.lease.order.api.service.IOrderService;
import com.lease.order.model.dto.ChangeOrderStatusRequest;
import com.lease.order.model.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    IOrderService orderService;


    /**
     * create order
     * @param dto
     * @return
     */
    @PostMapping("/order/create")
    public Order createOrder(@RequestBody OrderDto dto) {

        return orderService.createOrder(dto);
    }

    /**
     * cancel order
     * @param dto
     * @return order
     */
    @PostMapping("/order/cancel")
    public Order cancelOrder(@RequestBody OrderDto dto) {
        return orderService.cancelOrder(dto);
    }

    /**
     * get order detail
     * @param orderId
     * @return order detail
     */
    @GetMapping("/order/detail/{orderId}")
    public Order orderDetail(@PathVariable String orderId) {
        return orderService.orderDetail(orderId);
    }

    /**
     * check order status
     * @param orderId
     * @return order status
     */
    @GetMapping("/order/checkStatus/{orderId}")
    public Object checkOrderStatus(@PathVariable String orderId) {
        return orderService.checkOrderStatus(orderId);
    }

    /**
     * modify order status
     * @param request
     * @return order
     */
    @PutMapping("/order/changeStatus")
    public Order changeOrderStatus(@RequestBody ChangeOrderStatusRequest request) {
        return orderService.changeOrderStatus(request);
    }

    /**
     * pay order
     * @param orderId orderId
     * @return order
     */
    @GetMapping("/order/pay/{orderId}")
    public Order payOrder(@PathVariable String orderId) {
        return orderService.payOrder(orderId);
    }
}

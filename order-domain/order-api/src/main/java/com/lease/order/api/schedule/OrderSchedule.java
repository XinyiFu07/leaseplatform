package com.lease.order.api.schedule;

import com.lease.order.api.domain.Order;
import com.lease.order.api.repository.IOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderSchedule {

    @Autowired
    IOrderRepository orderRepository;
    /**
     * 每分钟检查一次
     * 检查半小时未支付的订单自动取消
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void checkOrderStatus() {
        log.info("执行任务-检查半小时未支付的订单自动取消...");
        //查找过期订单
        List<Order> expireUnPayOrders = orderRepository.findExpireUnPayOrders();
        for (Order expireUnPayOrder : expireUnPayOrders) {
            //取消订单
            orderRepository.cancel(expireUnPayOrder.getOrderNo());
        }
    }
}

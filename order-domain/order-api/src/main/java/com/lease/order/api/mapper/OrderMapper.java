package com.lease.order.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lease.order.api.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order findByOrderId(@Param("orderId") String orderId);

    List<Order> findExpireUnPayOrders();
}

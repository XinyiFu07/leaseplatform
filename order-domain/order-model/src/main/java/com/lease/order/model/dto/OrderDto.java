package com.lease.order.model.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String orderId;
    private String goodsId;
    private Double amount;
    private String userId;
}

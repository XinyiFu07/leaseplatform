package com.lease.order.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeOrderStatusRequest {

    private String orderId;
    private String status;
}

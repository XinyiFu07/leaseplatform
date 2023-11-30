package com.lease.order.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName(value = "orders")
public class Order {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField(value = "order_no")
    private String orderNo;
    @TableField(value = "goods_id")
    private String goodsId;
    @TableField(value = "amount")
    private Double amount;
    @TableField(value = "status")
    private String status;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "create_time")
    private Date createTime;

    @Getter
    @AllArgsConstructor
    public enum OderStatus {

        UN_PAY("1"),
        PAY("2"),
        CANCEL("9")
        ;

        private String value;
    }
}

package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class OrderResponse {
    private Long id;
    private Long customer_id;
    private Instant orderDate;
    private BigDecimal totalAmount;
    private Instant createdAt;
    private Instant updatedAt;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.customer_id = order.getCustomer().getId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
}

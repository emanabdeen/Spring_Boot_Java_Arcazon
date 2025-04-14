package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long customer_id;

    private BigDecimal totalAmount;

    private List<OrderItemApiRequest> orderItems;
}

package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemApiRequest {
    private Long productId;

    private int quantity;
}

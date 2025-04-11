package com.conestoga.arcazon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductSalesDTO {
    private Product product;
    private Long totalSold; // Total quantity sold
}

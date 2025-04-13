package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.categoryId = product.getCategory().getId();
    }
}

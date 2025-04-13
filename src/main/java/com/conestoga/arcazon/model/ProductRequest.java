package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;
}

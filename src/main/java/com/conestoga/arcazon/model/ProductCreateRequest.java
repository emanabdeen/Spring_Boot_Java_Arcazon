package com.conestoga.arcazon.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCreateRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;


}

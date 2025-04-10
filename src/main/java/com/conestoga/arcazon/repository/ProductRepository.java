package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByCategory_Name(String categoryName);

    List<Product> findByCategory_Id(Long categoryId);
}
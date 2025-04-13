package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByCategory_Name(String categoryName);

    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByCategoryIdAndPriceBetween(Long categoryId, Double minPrice, Double maxPrice);

    List<Product> findByCategoryIdAndPriceGreaterThanEqual(Long categoryId, Double minPrice);

    List<Product> findByCategoryIdAndPriceLessThanEqual(Long categoryId, Double maxPrice);

    List<Product> findByPriceGreaterThanEqual(Double minPrice);

    List<Product> findByPriceLessThanEqual(Double maxPrice);
}
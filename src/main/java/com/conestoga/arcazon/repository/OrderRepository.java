package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
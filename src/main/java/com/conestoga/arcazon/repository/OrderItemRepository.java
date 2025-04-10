package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
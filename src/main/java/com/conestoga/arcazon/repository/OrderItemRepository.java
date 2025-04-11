package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.OrderItem;
import com.conestoga.arcazon.model.OrderItemId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    @Query("SELECT COALESCE(SUM(oi.unitPrice * oi.quantity), 0) " +
            "FROM OrderItem oi WHERE oi.order.id = :orderId")
    BigDecimal sumOrderItemsTotalByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT oi.product, SUM(oi.quantity) as totalSold " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTop5BestSellingProducts();

}
package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.*;
import com.conestoga.arcazon.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Service
public class OrderService {
    private final OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo) {

        this.orderRepo = orderRepo;
    }


    public List<Order> findAll() {
        return orderRepo.findAll();
    }


    @Transactional
    public Order createOrder(Customer customer, BigDecimal totalPrice) {

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(Instant.now());
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());
        order.setTotalAmount(totalPrice); // Will be calculated later

        // Save the order first to generate ID
        order = orderRepo.save(order);

        /*// Process order items
        for (OrderItemRequest itemRequest : orderItems) {
            // Get product and verify stock

            if (itemRequest.getProduct().getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + itemRequest.getProduct().getName());
            }
        }*/
        return orderRepo.findById(order.getId()).orElseThrow();
    }


    public  BigDecimal getTotalPrice(List<OrderItemRequest> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }
        double totalPrice=0.0;

        for (OrderItemRequest item : orderItems) {
            totalPrice=totalPrice+(item.getProduct().getPrice()* item.getQuantity());
        }
        return new BigDecimal(totalPrice);
    }


    public List<Order> findAllOrdersByCustomerId(long id) {

        return orderRepo.findAllByCustomer_Id(id);
    }

    public Order findOrderById(long id) {
        return orderRepo.findById(id).orElseThrow(()-> new RuntimeException("order not found"));
    }

    public List<Order> findOrdersByCustomerId(long id) {

        return orderRepo.findAllByCustomer_Id(id);
    }

    public Order updateOrder(Order order) {
                order.setUpdatedAt(Instant.now());
        return orderRepo.save(order);
    }

    public void deleteOrder(long id) {
        Order order = findOrderById(id);
        orderRepo.delete(order);
    }

}

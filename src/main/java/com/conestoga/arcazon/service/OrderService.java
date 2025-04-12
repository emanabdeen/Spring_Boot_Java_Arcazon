package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.*;
import com.conestoga.arcazon.repository.OrderItemRepository;
import com.conestoga.arcazon.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    //private final OrderItemRepository orderItemRepo;
    //private final ProductService productService;
    //private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Transactional
    public Order createOrder(Customer customer, List<OrderItemRequest> orderItems, BigDecimal totalPrice) {

        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        // Verify customer exists
        //Customer customer = customerService.findCustomerById(customerId);

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(Instant.now());
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());
        order.setTotalAmount(totalPrice); // Will be calculated later

        // Save the order first to generate ID
        order = orderRepo.save(order);

        // Process order items
        for (OrderItemRequest itemRequest : orderItems) {
            // Get product and verify stock
            //Product product = productService.findById(itemRequest.getProductId());

            if (itemRequest.getProduct().getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + itemRequest.getProduct().getName());
            }
        }
        return orderRepo.findById(order.getId()).orElseThrow();
    }


    public List<Order> findAllOrdersByCustomerId(long id) {

        return orderRepo.findAllByCustomer_Id(id);
    }
}

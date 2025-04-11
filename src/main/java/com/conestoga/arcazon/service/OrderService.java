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

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepo,
                        OrderItemRepository orderItemRepo,
                        ProductService productService,
                        CustomerService customerService) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Transactional
    public Order createOrder(Long customerId, List<OrderItemRequest> orderItems) {
        // Validate inputs
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        // Verify customer exists
        Customer customer = customerService.findCustomerById(customerId);

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(Instant.now());
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());
        order.setTotalAmount(BigDecimal.ZERO); // Will be calculated later

        // Save the order first to generate ID
        order = orderRepo.save(order);

        // Process order items
        for (OrderItemRequest itemRequest : orderItems) {
            // Get product and verify stock
            Product product = productService.findById(itemRequest.getProductId());

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();

            // Create composite ID
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(order.getId());
            orderItemId.setProductId(product.getId());

            orderItem.setId(orderItemId);
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
            orderItem.setCreatedAt(Instant.now());
            orderItem.setUpdatedAt(Instant.now());

            // Save order item
            orderItemRepo.save(orderItem);

            // Update product stock
            int newStock = product.getStock() - itemRequest.getQuantity();
            productService.updateStock(product.getId(), newStock);
        }

        // Calculate and update order total
        calculateOrderTotal(order.getId());

        return orderRepo.findById(order.getId()).orElseThrow();
    }

    public BigDecimal calculateOrderTotal(Long orderId) {
        // Verify order exists
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Calculate total from order items (quantity * unit_price)
        BigDecimal total = orderItemRepo.sumOrderItemsTotalByOrderId(orderId);

        // Update and save the order with new total
        order.setTotalAmount(total);
        orderRepo.save(order);

        return total;
    }


}

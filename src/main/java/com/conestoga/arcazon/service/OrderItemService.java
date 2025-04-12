package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.*;
import com.conestoga.arcazon.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepo;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    public void saveOrderItemList(List<OrderItemRequest> orderItems, Order order) {
        // Process order items
        for (OrderItemRequest itemRequest : orderItems) {
            // Get product and verify stock
            //Product product = productService.findById(itemRequest.getProductId());

            if (itemRequest.getProduct().getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + itemRequest.getProduct().getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();

            // Create composite ID
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(order.getId());
            orderItemId.setProductId(itemRequest.getProduct().getId());

            orderItem.setId(orderItemId);
            orderItem.setOrder(order);
            orderItem.setProduct(itemRequest.getProduct());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(BigDecimal.valueOf(itemRequest.getProduct().getPrice()));
            orderItem.setCreatedAt(Instant.now());
            orderItem.setUpdatedAt(Instant.now());

            // Save order item
            orderItemRepo.save(orderItem);
        }
    }

    public List<ProductSalesDTO> getTop5BestSellingProducts() {
        List<Object[]> results = orderItemRepo.findTop5BestSellingProducts();

        return results.stream()
                .limit(5) // Get top 5
                .map(result -> new ProductSalesDTO(
                        (Product) result[0],      // Product object
                        ((Long) result[1])       // Sum of quantity
                ))
                .collect(Collectors.toList());
    }

    /**
     *  Calculate total from order items (quantity * unit_price)
     */
    public BigDecimal calculateOrderTotal(Long orderId) {

        BigDecimal total = orderItemRepo.sumOrderItemsTotalByOrderId(orderId);// Calculate total from order items (quantity * unit_price)
        return total;
    }
}

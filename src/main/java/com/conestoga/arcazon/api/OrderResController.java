package com.conestoga.arcazon.api;


import com.conestoga.arcazon.model.*;
import com.conestoga.arcazon.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderResController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CategoryService categoryService;


    public OrderResController(OrderService orderService, OrderItemService orderItemService, CustomerService customerService,
                              ProductService productService, CategoryService categoryService) {
        this.orderService=orderService;
        this.orderItemService=orderItemService;
        this.customerService=customerService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // GET /orders/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.findAll();

            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "No orders found"));
            }

            // Convert to ProductResponse DTOs
            List<OrderResponse> response = orders.stream()
                    .map(OrderResponse::new)
                    .toList();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve orders",
                            "details", e.getMessage()
                    ));
        }
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.findOrderById(id);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Order not found with id: " + id));
            }
            return ResponseEntity.ok(new OrderResponse(order));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve order",
                            "details", e.getMessage()
                    ));
        }
    }

    // GET /orders/customer/{id}
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getAllOrdersByCustomerId(@PathVariable Long id) {
        try {
            List<Order> orders = orderService.findOrdersByCustomerId(id);
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "No orders found for customer id: " + id));
            }

            // Convert to ProductResponse DTOs
            List<OrderResponse> response = orders.stream()
                    .map(OrderResponse::new)
                    .toList();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve orders for customer id: " + id,
                            "details", e.getMessage()
                    ));
        }
    }

    // POST /Orders
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {

        try {
            // Validate request
            if (request == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Order request cannot be null"));
            }
            if (request.getCustomer_id()== null || request.getCustomer_id() == 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "customer_id is missing"));
            }
            if (request.getTotalAmount() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "totalAmount is missing"));
            }


            // Save and return response

            // 1. check the order items not empty
            if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "order-items are missing"));
            }

            // 2. check the product stock
            if (!productService.checkProductStockAPI(request.getOrderItems())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "one or more products have not enough stock"));

            }

            // 3. Create order
            Order savedOrder = orderService.createOrder(customerService.findCustomerById( request.getCustomer_id()), request.getTotalAmount());

            // 4. Save order items
            List<OrderItemRequest>items=request.getOrderItems().stream()
                    .map(apiItem -> {
                        // Find the product by ID
                        Product product = productService.findById(apiItem.getProductId());

                        // Create and populate OrderItemRequest
                        OrderItemRequest r = new OrderItemRequest();
                        r.setProduct(product);
                        r.setQuantity(apiItem.getQuantity());
                        return r;
                    })
                    .collect(Collectors.toList());
            orderItemService.saveOrderItemList(items, savedOrder);

            // 5. Update product stocks
            productService.updateProductsStock(items);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new OrderResponse(savedOrder));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to create order",
                            "details", e.getMessage()
                    ));
        }
    }
}


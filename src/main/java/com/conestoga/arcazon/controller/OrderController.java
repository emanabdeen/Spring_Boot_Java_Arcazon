package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.Order;
import com.conestoga.arcazon.model.OrderItemRequest;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.service.CustomerService;
import com.conestoga.arcazon.service.OrderItemService;
import com.conestoga.arcazon.service.OrderService;
import com.conestoga.arcazon.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, CustomerService customerService, ProductService productService) {
        this.orderService=orderService;
        this.orderItemService=orderItemService;
        this.customerService=customerService;
        this.productService = productService;
    }

    @PostMapping
    @Transactional
    public String postOrder(Long customerId, List<OrderItemRequest> orderItems, BigDecimal totalPrice,Model model) {

        try {
            // Get customer from session or request
            Customer customer = customerService.findCustomerById(customerId);

            // Create order
            Order order = orderService.createOrder(customer, orderItems, totalPrice);

            // Save order items
            orderItemService.saveOrderItemList(orderItems, order);

            // Update product stocks
            productService.updateProductsStock(orderItems);

            return "redirect:/orders/" + order.getId(); // Redirect to order confirmation page

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "orders/error"; // Return to error page
        }
    }
}

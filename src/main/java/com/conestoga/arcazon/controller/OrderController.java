package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.*;
import com.conestoga.arcazon.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, CustomerService customerService,
                           ProductService productService, CategoryService categoryService) {
        this.orderService=orderService;
        this.orderItemService=orderItemService;
        this.customerService=customerService;
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/products-list")
    public String getAllProducts( Double minPrice, Double maxPrice, Long categoryId,Model model, HttpSession session) {

        // Check if user is logged in
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customers/login";
        }
        model.addAttribute("customer", customer); // Add customer to model

        List<Product> products;
        if (minPrice != null || maxPrice != null || categoryId != null) {
            // Filter products
            products = productService.findByFilters(minPrice, maxPrice, categoryId);
        } else {
            // Get all products will return only the products that have stock >0
            products = productService.findAllHaveStock();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll()); // For category dropdown
        return "orders/products-list";
    }

    @GetMapping("/top-sellers")
    public String getTopSellers(Model model) {
        List<ProductSalesDTO> productSalesDTO = orderItemService.getTop5BestSellingProducts();
        List<Product> products = new ArrayList<>(); // Initialize the list

        for (ProductSalesDTO p : productSalesDTO) {
            products.add(p.getProduct());
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        return "orders/products-list";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam("productIds") List<Long> productIds,@RequestParam("quantities") List<Integer> quantities,
                              HttpSession session, Model model) {
        // Check if user is logged in
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customers/login";
        }
        model.addAttribute("customer", customer);

        // Combine into ProductQuantityDTO list
        List<ProductQuantityDTO> selectedProducts = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            ProductQuantityDTO dto = new ProductQuantityDTO();
            if (quantities.get(i) != null && quantities.get(i) > 0) {
                dto.setId(productIds.get(i));
                dto.setQuantity(quantities.get(i));
                selectedProducts.add(dto);
            }
        }

        //if no product with quantity >0 ... already we have this check at user side by javascript
        if (selectedProducts.isEmpty()) {
            return "redirect:/orders/products-list";
        }

        // Convert to OrderItemRequest list
        List<OrderItemRequest> orderItems = selectedProducts.stream()
                .map(pq -> {
                    Product product = productService.findById(pq.getId());
                    OrderItemRequest item = new OrderItemRequest();
                    item.setProduct(product);
                    item.setQuantity(pq.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = orderService.getTotalPrice(orderItems);

        // Store in session instead of model
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalPrice", totalPrice);
        return "orders/cart";
    }

    @PostMapping
    @Transactional
    public String postOrder(@RequestParam("productIds") List<Long> productIds,@RequestParam("quantities") List<Integer> quantities,
                            @RequestParam Long customerId, @RequestParam BigDecimal totalPrice,Model model,
                            RedirectAttributes redirectAttributes) {

        try {

            // Combine into ProductQuantityDTO list
            List<ProductQuantityDTO> selectedProducts = new ArrayList<>();
            for (int i = 0; i < productIds.size(); i++) {
                ProductQuantityDTO dto = new ProductQuantityDTO();
                if (quantities.get(i) != null && quantities.get(i) > 0) {
                    dto.setId(productIds.get(i));
                    dto.setQuantity(quantities.get(i));
                    selectedProducts.add(dto);
                }
            }

            // Convert to OrderItemRequest list
            List<OrderItemRequest> orderItems = selectedProducts.stream()
                    .map(pq -> {
                        Product product = productService.findById(pq.getId());
                        OrderItemRequest item = new OrderItemRequest();
                        item.setProduct(product);
                        item.setQuantity(pq.getQuantity());
                        return item;
                    })
                    .collect(Collectors.toList());


            // Get customer from session or request
            Customer customer = customerService.findCustomerById(customerId);

            // 1. check the order items not empty
            if (orderItems == null || orderItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("error","add quantity to at least one product");
                return "redirect:/orders/products-list";
            }

            // 2. check the product stock
            if (!productService.checkProductStock(orderItems)) {
                redirectAttributes.addFlashAttribute("error","one or more products have not enough stock");
                return "redirect:/orders/products-list";
            }
            // 3. Create order
            Order order = orderService.createOrder(customer, totalPrice);

            // 4. Save order items
            orderItemService.saveOrderItemList(orderItems, order);

            // 5. Update product stocks
            productService.updateProductsStock(orderItems);

            return "redirect:/orders/order/" + order.getId(); // Redirect to order confirmation page

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "orders/error"; // Return to error page
        }
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        return "orders/order";
    }

    @GetMapping("/orders-list")
    public String getOrders(Model model, Long customerId) {

        List<Order> orders;
        if (customerId != null) {
            // Filter orders by customer
            orders = orderService.findOrdersByCustomerId(customerId);
        } else {
            // Get all orders
            orders = orderService.findAll();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("customers", customerService.findAll()); // For customers dropdown

        return "orders/orders-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders/orders-list";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model, HttpSession session) {

        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);

        return "orders/order-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findOrderById(id));
        return "orders/edit-order-form";
    }


    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id,@ModelAttribute Order order,RedirectAttributes redirectAttributes) {
        try {

            Order updatedorder = orderService.findOrderById(id);
            updatedorder.setTotalAmount(order.getTotalAmount());
            orderService.updateOrder(updatedorder);
            redirectAttributes.addFlashAttribute("success","Order updated successfully!");
            return "redirect:/orders/orders-list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error updating order: " + e.getMessage());
            return "redirect:/orders/edit/" + id;
        }
    }

}

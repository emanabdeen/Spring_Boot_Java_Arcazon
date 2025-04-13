package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products-list")
    public String getAllProducts(Model model, HttpSession session) {
        /*// Check if user is logged in
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customers/login";
        }*/

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        //model.addAttribute("customer", customer);
        return "products/products-list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model, HttpSession session) {
        /*// Check if user is logged in
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customers/login";
        }*/

        Product product = productService.findById(id);
        model.addAttribute("product", product);
        //model.addAttribute("customer", customer); // Add customer to model
        return "products/product-details"; // Updated template path
    }
}
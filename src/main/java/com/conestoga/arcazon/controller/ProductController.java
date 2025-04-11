package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private  final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService=productService;
    }

    //Define the individuals methods

    // GET /products - get all products
    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products=productService.findAll();
        model.addAttribute("products", products);
        return "products"; //maps to a products.html page
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-details"; // You'll need to create this template
    }


}
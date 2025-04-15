package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.model.ProductSalesDTO;
import com.conestoga.arcazon.service.CategoryService;
import com.conestoga.arcazon.service.OrderItemService;
import com.conestoga.arcazon.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderItemService orderItemService;

    public ProductController(ProductService productService, CategoryService categoryService, OrderItemService orderItemService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/products-list")
    public String getAllProducts( Double minPrice, Double maxPrice, Long categoryId,Model model) {

        List<Product> products;
        if (minPrice != null || maxPrice != null || categoryId != null) {
            // Filter products
            products = productService.findByFilters(minPrice, maxPrice, categoryId);
        } else {
            // Get all products
            products = productService.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll()); // For category dropdown
        return "products/products-list";
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
        return "products/products-list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model, HttpSession session) {

        Product product = productService.findById(id);
        model.addAttribute("product", product);

        return "products/product-details"; // Updated template path
    }

    @GetMapping("/add-new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/add-product-form";
    }

    @PostMapping("/add-new")
    public String addProduct(@ModelAttribute Product product,@RequestParam Long categoryId,RedirectAttributes redirectAttributes) {
        try {

            product.setCategory(categoryService.findById(categoryId));
            productService.addNewProduct(product);
            redirectAttributes.addFlashAttribute("success", "Product added successfully!");
            return "redirect:/products/products-list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add product " );
            return "redirect:/products/add-new";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit-product-form";
    }


    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute Product product,@RequestParam Long categoryId,RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findById(categoryId);
            product.setCategory(category);
            productService.updateProduct(id, product);
            redirectAttributes.addFlashAttribute("success","Product updated successfully!");
            return "redirect:/products/products-list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error updating product: " + e.getMessage());
            return "redirect:/products/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/products-list";
    }
}
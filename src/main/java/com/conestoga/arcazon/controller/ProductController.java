package com.conestoga.arcazon.controller;

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

    //@GetMapping("/products-list")
    //public String getAllProducts(Model model, HttpSession session) {
        //*// Check if user is logged in
        //Customer customer = (Customer) session.getAttribute("customer");
        //if (customer == null) {
           // return "redirect:/customers/login";
       // }*/



        //List<Product> products = productService.findAll();
        //model.addAttribute("products", products);
        ////model.addAttribute("customer", customer);
        //return "products/products-list";
   // }


        @GetMapping("/products-list")
        public String getAllProducts(Double minPrice,Double maxPrice,Long categoryId,Model model) {

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

    @GetMapping("/add-new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/add-product-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit-product-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/products-list";
    }
}
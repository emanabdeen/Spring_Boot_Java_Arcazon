package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.model.ProductCreateRequest;
import com.conestoga.arcazon.service.CategoryService;
import com.conestoga.arcazon.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // GET /products - get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // GET /products/{id} - get an individual
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @RequestBody ProductCreateRequest request,
            @RequestParam Long categoryId) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Category category=categoryService.findById(categoryId);
        return productService.addNewProduct(product, category);
    }
}

package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.model.ProductRequest;
import com.conestoga.arcazon.model.ProductResponse;
import com.conestoga.arcazon.service.CategoryService;
import com.conestoga.arcazon.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAll();

            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "No products found"));
            }

            // Convert to ProductResponse DTOs
            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .toList();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve products",
                            "details", e.getMessage()
                    ));
        }
    }

    // GET /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Product not found with id: " + id));
            }
            return ResponseEntity.ok(new ProductResponse(product));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve product",
                            "details", e.getMessage()
                    ));
        }
    }

    // POST /products
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {

        try {
            // Validate request
            if (request == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product request cannot be null"));
            }

            // Create product
            Product product = new Product();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStock(request.getStock());

            // Handle category
            Category category = categoryService.findById(request.getCategoryId());
            if (category == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Category not found with id: " + request.getCategoryId()));
            }
            product.setCategory(category);

            // Save and return response
            Product savedProduct = productService.addNewProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ProductResponse(savedProduct));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to create product",
                            "details", e.getMessage()
                    ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody ProductRequest request) {
        try {
            // Validate input
            if (request == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product data cannot be null"));
            }

            // Get existing product
            Product existingProduct = productService.findById(id);
            if (existingProduct == null) {
                return ResponseEntity.notFound().build();
            }

            // Update fields
            existingProduct.setName(request.getName());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setStock(request.getStock());

            // Handle category
            Category category = categoryService.findById(request.getCategoryId());
            if (category == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Category not found"));
            }
            existingProduct.setCategory(category);

            // Save updates
            Product updatedProduct = productService.updateProduct(id, existingProduct);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", true,
                            "message", "Product updated successfully",
                            "product", new ProductResponse(updatedProduct)
                    ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to update product",
                            "details", e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            // Check if product exists
            Product product = productService.findById(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Delete the product
            productService.deleteProduct(id);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", true,
                            "message", "Product deleted successfully",
                            "deletedId", id
                    ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to delete product",
                            "details", e.getMessage(),
                            "productId", id
                    ));
        }
    }
}

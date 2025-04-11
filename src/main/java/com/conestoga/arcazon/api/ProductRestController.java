package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.model.ProductCreateRequest;
import com.conestoga.arcazon.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;
    public ProductRestController(ProductService productService) {
        this.productService = productService;
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

    /*@PostMapping(consumes = {"application/json"}) // More lenient
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @RequestBody Product product,
            @RequestParam Long categoryId) {
        return productService.addNewProduct(product, categoryId);
    }*/

    /*@PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody String requestBody, @RequestParam Long categoryId) {
        System.out.println("Received JSON: " + requestBody);
        System.out.println("Category ID: " + categoryId);
        return "Received";
    }*/

    /*@PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @RequestBody Product product,
            @RequestParam Long categoryId) {
        System.out.println("Received JSON: " + product);
        System.out.println("Category ID: " + categoryId);
        return productService.addNewProduct(product, categoryId);
    }*/

    /*@PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @RequestBody Map<String, Object> requestBody,
            @RequestParam Long categoryId) {
        System.out.println("Received Map: " + requestBody);
        System.out.println("Category ID: " + categoryId);
        // You would then manually create the Product object from the map
        Product product = new Product();
        if (requestBody.containsKey("name")) {
            product.setName((String) requestBody.get("name"));
        }
        if (requestBody.containsKey("description")) {
            product.setDescription((String) requestBody.get("description"));
        }
        if (requestBody.containsKey("price")) {
            product.setPrice(((Number) requestBody.get("price")).doubleValue());
        }
        if (requestBody.containsKey("stock")) {
            product.setStock(((Number) requestBody.get("stock")).intValue());
        }
        // ... set other fields if necessary

        return productService.addNewProduct(product, categoryId);
    }*/


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

        return productService.addNewProduct(product, categoryId);
    }
}

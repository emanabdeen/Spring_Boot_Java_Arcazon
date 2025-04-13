package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.OrderItemRequest;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo=productRepo;
    }


    //return all products from database
    public List<Product> findAll() {

        return productRepo.findAll();
    }

    //return products have stock >0
    public List<Product> findAllHaveStock() {
        return productRepo.findAllByStockIsGreaterThan(0);
    }

    public Product findById(long id) {
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product addNewProduct(Product product) {
        try {
            // Validate inputs
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (product.getPrice() == null) {
                throw new IllegalArgumentException("Price is required");
            }

            // Set timestamps if they're null
            if (product.getCreatedAt() == null) {
                product.setCreatedAt(Instant.now());
            }
            if (product.getUpdatedAt() == null) {
                product.setUpdatedAt(Instant.now());
            }

            // Save product
            Product savedProduct = productRepo.save(product);
            System.out.println("Saved product with ID: " + savedProduct.getId()); // Add this line
            return savedProduct;

        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
    }

    // Update a product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));

        // Update fields
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());

        product.setUpdatedAt(Instant.now());// update time


        //update product
        return productRepo.save(product);
    }

    // Delete a product
    public void deleteProduct(long id) {
        Product product = findById(id); // This will throw exception if not found
        productRepo.delete(product);
    }

    /**
     * This method to update the stock of a product. the stock have to be equal or greater than 0. if not it will throw error
     */
    //update product request
    public void updateProductsStock(List<OrderItemRequest> orderItems) {

        for (OrderItemRequest itemRequest : orderItems) {
            // Update product stock
            int newStock = itemRequest.getProduct().getStock() - itemRequest.getQuantity();
            Product product = productRepo.findById(itemRequest.getProduct().getId()).orElseThrow(()-> new RuntimeException("Product not found"));

            // Update fields
            product.setStock(newStock);

            // update category
            if (newStock >= 0) {
                // Update fields
                product.setStock(newStock);

                //update product
                productRepo.save(product);
            }
            else {
                throw new IllegalArgumentException("Stock cannot be negative");
            }
        }
    }

    public  Boolean checkProductStock(List<OrderItemRequest> orderItems) {
        for (OrderItemRequest itemRequest : orderItems) {
            // Update product stock
            int newStock = itemRequest.getProduct().getStock() - itemRequest.getQuantity();

            // update category
            if (newStock < 0) {
                return false;
            }
        }
        return true;
    }

    public List<Product> findByFilters(Double minPrice, Double maxPrice, Long categoryId) {
        if (categoryId != null) {
            if (minPrice != null && maxPrice != null) {
                return productRepo.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
            } else if (minPrice != null) {
                return productRepo.findByCategoryIdAndPriceGreaterThanEqual(categoryId, minPrice);
            } else if (maxPrice != null) {
                return productRepo.findByCategoryIdAndPriceLessThanEqual(categoryId, maxPrice);
            }
            return productRepo.findByCategory_Id(categoryId);
        } else {
            if (minPrice != null && maxPrice != null) {
                return productRepo.findByPriceBetween(minPrice, maxPrice);
            } else if (minPrice != null) {
                return productRepo.findByPriceGreaterThanEqual(minPrice);
            } else if (maxPrice != null) {
                return productRepo.findByPriceLessThanEqual(maxPrice);
            }
            return productRepo.findAll();
        }
    }

    // Find products within price range
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice) {
        return productRepo.findByPriceBetween(minPrice, maxPrice);
    }

    // Find products by category
    public List<Product> findByCategoryId(Long categoryId) {

        return productRepo.findByCategory_Id(categoryId);
    }

    // Find products by category
    public List<Product> findByCategoryName(String name) {

        return productRepo.findByCategory_Name(name);
    }

}

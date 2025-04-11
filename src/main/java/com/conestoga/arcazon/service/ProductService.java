package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.Product;
import com.conestoga.arcazon.model.ProductSalesDTO;
import com.conestoga.arcazon.repository.CategoryRepository;
import com.conestoga.arcazon.repository.OrderItemRepository;
import com.conestoga.arcazon.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private  final OrderItemRepository orderItemRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo, OrderItemRepository orderItemRepo) {
        this.productRepo=productRepo;
        this.categoryRepo=categoryRepo;
        this.orderItemRepo=orderItemRepo;
    }


    //return all products from database
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product findById(long id) {
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product addNewProduct(Product product, Long categoryId) {
        try {
            // Validate inputs
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (categoryId == null) {
                throw new IllegalArgumentException("Valid category ID is required");
            }
            if (product.getPrice() == null) {
                throw new IllegalArgumentException("Price is required");
            }

            // Get category object by id
            Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            product.setCategory(category);

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

   /* @Transactional
    public Product addNewProduct(Product product, Long categoryId) {
        // Validate inputs
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("Valid category ID is required");
        }

        //get category object by id
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: "+categoryId));
        product.setCategory(category);

        //Create product
        return productRepo.save(product);
    }*/

    /*// Create a new product
    public Product addNewProduct(Product product, Long categoryId) {
        // Validate inputs
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("Valid category ID is required");
        }

        //get category object by id
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: "+categoryId));
        product.setCategory(category);

        //Create product
        return productRepo.save(product);
    }*/

    // Update a product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));

        // Update fields
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        // update category
        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category category = categoryRepo.findById(productDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDetails.getCategory().getId()));
            product.setCategory(category);
        }

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
     * @param prductId
     * @param updatedStock
     */
    //update the stock of a product
    public void updateStock(Long prductId,int updatedStock) {
        Product product = productRepo.findById(prductId).orElseThrow(()-> new RuntimeException("Product not found"));

        // Update fields
        product.setStock(updatedStock);

        // update category
        if (updatedStock >= 0) {
            // Update fields
            product.setStock(updatedStock);

            //update product
            productRepo.save(product);
        }
        else {
            throw new IllegalArgumentException("Stock cannot be negative");
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

    public List<ProductSalesDTO> getTop5BestSellingProducts() {
        List<Object[]> results = orderItemRepo.findTop5BestSellingProducts();

        return results.stream()
                .limit(5) // Get top 5
                .map(result -> new ProductSalesDTO(
                        (Product) result[0],      // Product object
                        ((Long) result[1])       // Sum of quantity
                ))
                .collect(Collectors.toList());
    }
}

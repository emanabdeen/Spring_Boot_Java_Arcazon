package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.CategoryDTO;
import com.conestoga.arcazon.service.CategoryService;
import com.conestoga.arcazon.utils.CategoryUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /categories - get all categories
    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        try {
            categories = categoryService.findAll();
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "No categories found"));
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve categories",
                            "details", e.getMessage()
                    ));
        }
    }


    // GET /categories/{id} - get an individual category
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        try {
            Category categoryById = categoryService.findById(id);
            if (categoryById != null) {
                return ResponseEntity.ok(categoryById);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Category not found with id: " + id));
        } catch (Exception er) {
            System.out.println(er.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to retrieve category",
                            "details", er.getMessage()
                    ));
        }
    }

    // POST /categories - create a new category
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Request body cannot be null"));
        } else {
            try {
                categoryDTO.setCreatedAt(categoryDTO.getCreatedAt() == null ? Instant.now() : categoryDTO.getCreatedAt());
                categoryDTO.setUpdatedAt(categoryDTO.getUpdatedAt() == null ? Instant.now() : categoryDTO.getUpdatedAt());
                CategoryDTO newCategory = categoryService.addNewCategory(categoryDTO);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(newCategory);
            } catch (Exception e) {
                System.out.println("Error occurred while creating category: " + e.getMessage());
                return ResponseEntity.internalServerError()
                        .body(Map.of(
                                "error", "Failed to create category",
                                "details", e.getMessage()
                        ));
            }
        }
    }


    // PUT /categories/{id}
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", true,
                            "message", "Category updated successfully",
                            "category", CategoryUtils.entityToDto(updatedCategory)));
        } catch (Exception e) {
            System.out.println("Category not found or another error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        try {
            if (id == null || id.toString().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("Category not found or another error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error",e.getMessage()));
        }
    }
}

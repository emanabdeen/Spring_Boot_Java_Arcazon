package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.CategoryCreateRequest;
import com.conestoga.arcazon.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /categories - get all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }


    // GET /categories/{id} - get an individual category
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        try {
            Category categoryById = categoryService.findById(id);
            if (categoryById != null) {
                return categoryById;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Category not found or another error occurred: " + e.getMessage());
            return null;
        }
    }

    // POST /categories - create a new category
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryCreateRequest categoryDTO) {
        if (categoryDTO != null) {
            return categoryService.addNewCategory(categoryDTO.toCategory());
        } else {
            System.out.println("Category is null");
            return null;
        }
    }


    // PUT /categories/{id}
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryCreateRequest categoryDTO) {
        try {
            Category category = categoryService.findById(id);
            if (categoryDTO != null) {
                category.setUpdatedAt(Instant.now());
                category.setName(categoryDTO.getName());
                category.setDescription(categoryDTO.getDescription());
                return categoryService.updateCategory(id, category);
            } else {
                System.out.println("Category is null");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Category not found or another error occurred: " + e.getMessage());
            return null;
        }
    }

    // DELETE /categories/{id}
//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable Long id) {
//        try {
//            categoryService.deleteCategory(id);
//        } catch (Exception e) {
//            System.out.println("Category not found or another error occurred: " + e.getMessage());
//        }
//    }
}

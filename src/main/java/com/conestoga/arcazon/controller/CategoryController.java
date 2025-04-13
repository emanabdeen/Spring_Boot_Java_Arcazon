package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private  final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService=categoryService;
    }

    //Define the individuals methods

    // GET /categories - get all categories
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "/category/categories";
    }

    // GET /categories/{id} - get an individual category
    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "/category/category-details";
    }

    // POST /categories - create a new category
    @PostMapping
    public String createCategory() {
        return "redirect:/category/categories";
    }

    // PUT /categories/{id}
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id) {
        return "redirect:/category/categories";
    }

    // DELETE /categories/{id}
//    @DeleteMapping("/{id}")
//    public String deleteCategory(@PathVariable Long id) {
//        return "redirect:/category/categories";
//    }

}

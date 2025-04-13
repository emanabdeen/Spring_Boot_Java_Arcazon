package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping(value = "/categories-list")
    public String getAllCategories(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "category/categories-list";
    }

    // GET /categories/{id} - get an individual category
    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        try {
            Category category = categoryService.findById(id);
            model.addAttribute("category", category);
            return "category/category-details";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "redirect:category/categories-list";
        }
    }

    @GetMapping(value = "/add-new")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/add-category-form";
    }

    // POST /categories - create a new category
    @PostMapping(value = "/add-new")
    public String createCategory(@ModelAttribute Category category, RedirectAttributes redirectattributes) {
        try {
            Category savedCategory = categoryService.addNewCategory(category);
            redirectattributes.addFlashAttribute("success", "Category added successfully!");
            return "redirect:/categories/categories-list";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            redirectattributes.addFlashAttribute("error", "Error adding category: " + e.getMessage());
            return "redirect:/categories/add-new";
        }
    }

    // PUT /categories/{id}
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id) {
        return "redirect:category/categories-list";
    }

    // DELETE /categories/{id}
//    @DeleteMapping("/{id}")
//    public String deleteCategory(@PathVariable Long id) {
//        return "redirect:/category/categories";
//    }

}

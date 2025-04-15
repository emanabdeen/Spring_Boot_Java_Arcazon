package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.CategoryDTO;
import com.conestoga.arcazon.service.CategoryService;
import com.conestoga.arcazon.utils.CategoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
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
        List<CategoryDTO> categoryList = categoryService.findAll();
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
            CategoryDTO dto = CategoryUtils.entityToDto(category);
            dto.setCreatedAt(Instant.now());
            dto.setUpdatedAt(Instant.now());
            categoryService.addNewCategory(dto);
            redirectattributes.addFlashAttribute("success", "Category added successfully!");
            return "redirect:/categories/categories-list";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            redirectattributes.addFlashAttribute("error", "Error adding category: " + e.getMessage());
            return "redirect:category/add-category-form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model, RedirectAttributes redirectattributes) {
        try {
            System.out.println("Finding category for edit with ID: " + id);
            Category category = categoryService.findById(id);
            model.addAttribute("category", category);
            return "category/edit-category-form";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            redirectattributes.addAttribute("error", "Error finding category: " + e.getMessage());
            return "redirect:/categories/categories-list";
        }
    }

    // PUT /categories/{id}
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category, RedirectAttributes redirectattributes) {
        try {
            System.out.println("Updating category with ID: " + id);
            CategoryDTO dto = CategoryUtils.entityToDto(category);
            dto.setUpdatedAt(Instant.now());
            categoryService.updateCategory(id, dto);
            return "redirect:/categories/categories-list";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            redirectattributes.addFlashAttribute("error", "Error updating category: " + e.getMessage());
            return "redirect:category/edit-category-form";
        }
    }

    // DELETE /categories/{id}
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/categories/categories-list";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "redirect:/categories/categories-list";
        }
    }

}

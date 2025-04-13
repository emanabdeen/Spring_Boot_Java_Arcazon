package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Category findById(long id) {
        return categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    public Category addNewCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category replaceCategory = categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));

        // Update fields
        replaceCategory.setName(categoryDetails.getName());
        replaceCategory.setDescription(categoryDetails.getDescription());

        //update category
        return categoryRepo.save(replaceCategory);
    }

    public void deleteCategory(long id) {
        Category category = findById(id); // This will throw exception if not found
        categoryRepo.delete(category);
    }

}

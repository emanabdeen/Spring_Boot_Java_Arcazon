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

    public Category findById(long id) {
        return categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }
}

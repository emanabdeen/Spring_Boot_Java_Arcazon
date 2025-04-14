package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.CategoryDTO;
import com.conestoga.arcazon.repository.CategoryRepository;
import com.conestoga.arcazon.utils.CategoryUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryDTO> findAll() {
        return CategoryUtils.listEntityToListDto(categoryRepo.findAll());
    }

    public Category findById(long id) throws NoSuchElementException {
        return categoryRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Category not found with ID: " + id));
    }

    @Transactional
    public CategoryDTO addNewCategory(CategoryDTO category) throws Exception {
        try {
            Category newCategory = CategoryUtils.dtoToEntity(category);
            Category savedCategory = categoryRepo.save(newCategory);
            return CategoryUtils.entityToDto(savedCategory);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Category updateCategory(Long id, CategoryDTO categoryDTO) throws Exception {
        // Update fields
        try {
            Category category = findById(id);
            category.setUpdatedAt(Instant.now());
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            return categoryRepo.save(category);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteCategory(long id) throws Exception {
        try {
            Category category = findById(id); // This will throw exception if not found
            categoryRepo.delete(category);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

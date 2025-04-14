package com.conestoga.arcazon.utils;

import com.conestoga.arcazon.model.Category;
import com.conestoga.arcazon.model.CategoryDTO;
import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {

    public static Category dtoToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setCreatedAt(dto.getCreatedAt());
        category.setUpdatedAt(dto.getUpdatedAt());
        return category;
    }

    public static CategoryDTO entityToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    public static List<CategoryDTO> listEntityToListDto(List<Category> categoryList) {

        List<CategoryDTO> dtoList = new ArrayList<>();

        for (Category category : categoryList) {
            dtoList.add(entityToDto(category));
        }

        return dtoList;
    }

    public static List<Category> listDtoToListEntity(List<CategoryDTO> dtoList) {
        List<Category> categoryList = new ArrayList<>();

        for (CategoryDTO dto : dtoList) {
            categoryList.add(dtoToEntity(dto));
        }
        return categoryList;
    }
}

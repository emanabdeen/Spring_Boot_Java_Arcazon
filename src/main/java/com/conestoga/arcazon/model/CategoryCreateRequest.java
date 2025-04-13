package com.conestoga.arcazon.model;

import java.time.Instant;

public class CategoryCreateRequest {
    private Long id;
    private String name;
    private String description;

    public CategoryCreateRequest(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category toCategory() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());
        return category;
    }

}

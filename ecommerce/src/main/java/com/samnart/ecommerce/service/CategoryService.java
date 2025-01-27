package com.samnart.ecommerce.service;


import com.samnart.ecommerce.model.Category;
import com.samnart.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
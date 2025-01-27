package com.samnart.ecommerce.service;

import java.util.List;

import com.samnart.ecommerce.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
package com.samnart.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.samnart.ecommerce.model.Category;
import com.samnart.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> searchedCategory = categoryRepository.findById(categoryId);

        Category savedCategory = searchedCategory
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID: " + categoryId + " not found!"));

        categoryRepository.delete(savedCategory);

        return "Category with category ID: " + categoryId + " deleted successfully!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> searchedCategory = categoryRepository.findById(categoryId);
        
        Category savedCategory = searchedCategory
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID: " + categoryId + " not found!"));
        
        category.setCategoryId(categoryId);
        categoryRepository.save(category);

        return savedCategory;
    }}
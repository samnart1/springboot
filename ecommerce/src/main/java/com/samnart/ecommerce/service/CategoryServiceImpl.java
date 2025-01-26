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

    // List<Category> categories = new ArrayList<>();
    // private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        // category.setCategoryId(nextId++);
        categoryRepository.save(category); 
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        
        Category savedCategory = savedCategoryOptional
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource cannot be found!"));
        
        categoryRepository.delete(savedCategory);
        
        return "Category with categoryId: " + categoryId + " deleted successfully!!!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

        Category savedCategory = savedCategoryOptional
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;

        
    }
    
}

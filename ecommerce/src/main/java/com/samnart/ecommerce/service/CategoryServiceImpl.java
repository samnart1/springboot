package com.samnart.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.samnart.ecommerce.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

    List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category); 
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
            .filter(c -> categoryId != null && categoryId.equals(c.getCategoryId()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));

        categories.remove(category);
        
        return "Category with categoryId: " + categoryId + " deleted successfully!!!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> optionalCategory = categories.stream()
            .filter(c -> categoryId != null && categoryId.equals(c.getCategoryId()))
            .findFirst(); 

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        }
    }
    
}

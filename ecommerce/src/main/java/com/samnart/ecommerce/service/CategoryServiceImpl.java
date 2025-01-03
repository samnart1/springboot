package com.samnart.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
            .filter(x -> x.getCategoryId().equals(categoryId))
            .findFirst().orElse(null);

        if (category == null) {
            return "Category not found!!!";
        }

        categories.remove(category);
        
        return "Category with categoryId: " + categoryId + " deleted successfully!!!";
    }
    
}

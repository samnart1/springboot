package com.samnart.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samnart.ecommerce.exception.APIException;
import com.samnart.ecommerce.exception.ResourceNotFoundException;
import com.samnart.ecommerce.model.Category;
import com.samnart.ecommerce.payload.CategoryResponse;
import com.samnart.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> searchedCategories = categoryRepository.findAll();
        if (searchedCategories.isEmpty()) {
            throw new APIException("No categories found!");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .collect(Collectors.toList());

        
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
        String categoryNameLower = category.getCategoryName().toLowerCase();

        Category savedCategory = categoryRepository.findByCategoryName(categoryNameLower);

        if (savedCategory != null ) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists!");
        }

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> searchedCategory = categoryRepository.findById(categoryId);

        Category savedCategory = searchedCategory
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(savedCategory);

        return "Category with category ID: " + categoryId + " deleted successfully!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> searchedCategory = categoryRepository.findById(categoryId);
        
        Category savedCategory = searchedCategory
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        
        category.setCategoryId(categoryId);
        categoryRepository.save(category);

        return savedCategory;
    }}
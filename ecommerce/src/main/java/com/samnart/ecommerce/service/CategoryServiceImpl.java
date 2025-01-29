package com.samnart.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.samnart.ecommerce.exception.APIException;
import com.samnart.ecommerce.exception.ResourceNotFoundException;
import com.samnart.ecommerce.model.Category;
import com.samnart.ecommerce.payload.CategoryDTO;
import com.samnart.ecommerce.payload.CategoryResponse;
import com.samnart.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> searchedCategories = categoryPage.getContent();
        
        if (searchedCategories.isEmpty()) {
            throw new APIException("No categories found!");
        }

        // List<CategoryDTO> categoryDTOS = searchedCategories.stream()
        //     .map(category -> modelMapper.map(category, CategoryDTO.class))
        //     .collect(Collectors.toList());
        
        List<CategoryDTO> categoryDTOS = searchedCategories.stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .collect(Collectors.toList());

        
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        
        List<Category> allCategories = categoryRepository.findAll();
        
        boolean categoryExists = allCategories.stream()
            .anyMatch(cat -> cat.getCategoryName().equalsIgnoreCase(categoryDTO.getCategoryName()));
        
        if (categoryExists) {
            throw new APIException("Category with name " + categoryDTO.getCategoryName() + " already exists!");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
       

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> searchedCategory = categoryRepository.findById(categoryId);

        Category savedCategory = searchedCategory
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        
        categoryRepository.delete(savedCategory);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        
        Category savedCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        
        Category category = modelMapper.map(categoryDTO, Category.class);
        
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }}
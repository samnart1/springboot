package com.samnart.catalog.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samnart.catalog.dto.CategoryDTO;
import com.samnart.catalog.entity.Category;
import com.samnart.catalog.exceptions.ResourceNotFoundException;
import com.samnart.catalog.repository.CategoryRepository;
import com.samnart.catalog.service.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));        
        return convertToDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsByNameIgnoreCase(categoryDTO.getName())) {
            throw new IllegalArgumentException("Category with name: " + categoryDTO.getName() + " already exist.");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        Category savedCategory = categoryRepo.save(category);

        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " not found!"));

        if (!category.getName().equalsIgnoreCase(categoryDTO.getName()) && categoryRepo.existsByNameIgnoreCase(categoryDTO.getName())) {
            throw new IllegalArgumentException("Category already exist with name: " + categoryDTO.getName());
        }
        
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category savedCategory = categoryRepo.save(category);

        return convertToDTO(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with products in it!");
        }

        categoryRepo.delete(category);
    }
    
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setProductCount(category.getProducts().size());
        return dto;
    }
}

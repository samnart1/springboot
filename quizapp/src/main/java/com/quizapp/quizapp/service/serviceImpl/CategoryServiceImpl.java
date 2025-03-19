package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.CategoryRepo;
import com.quizapp.quizapp.service.serviceInt.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    
}
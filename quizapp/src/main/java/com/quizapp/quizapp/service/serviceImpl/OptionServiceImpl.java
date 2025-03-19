package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.OptionRepo;
import com.quizapp.quizapp.service.serviceInt.OptionService;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepo optionRepo;

    public OptionServiceImpl(OptionRepo optionRepo) {
        this.optionRepo = optionRepo;
    }
    
}
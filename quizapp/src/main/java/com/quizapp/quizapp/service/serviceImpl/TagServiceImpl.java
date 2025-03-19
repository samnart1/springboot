package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.TagRepo;
import com.quizapp.quizapp.service.serviceInt.TagService;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }
    
}
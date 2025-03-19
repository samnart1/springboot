package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.QuestionRepo;
import com.quizapp.quizapp.service.serviceInt.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionServiceImpl(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }
    
}
package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.QuizRepo;
import com.quizapp.quizapp.service.serviceInt.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepo quizRepo;

    public QuizServiceImpl(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }
    
}
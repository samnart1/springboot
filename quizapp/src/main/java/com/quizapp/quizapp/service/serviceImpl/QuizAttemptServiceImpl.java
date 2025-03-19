package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.QuizAttemptRepo;
import com.quizapp.quizapp.service.serviceInt.QuizAttemptService;

@Service
public class QuizAttemptServiceImpl implements QuizAttemptService {

    private final QuizAttemptRepo quizAttemptRepo;

    public QuizAttemptServiceImpl(QuizAttemptRepo quizAttemptRepo) {
        this.quizAttemptRepo = quizAttemptRepo;
    }

    
}
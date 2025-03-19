package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.QuizSettingRepo;
import com.quizapp.quizapp.service.serviceInt.QuizSettingService;

@Service
public class QuizSettingServiceImpl implements QuizSettingService {

    private final QuizSettingRepo quizSettingRepo;

    public QuizSettingServiceImpl(QuizSettingRepo quizSettingRepo) {
        this.quizSettingRepo = quizSettingRepo;
    }
    
}
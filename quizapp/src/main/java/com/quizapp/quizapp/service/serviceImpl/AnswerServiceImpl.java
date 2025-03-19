package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.AnswerRepo;
import com.quizapp.quizapp.service.serviceInt.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepo answerRepo;

    public AnswerServiceImpl(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }
}
package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.UserRepo;
import com.quizapp.quizapp.service.serviceInt.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    
}
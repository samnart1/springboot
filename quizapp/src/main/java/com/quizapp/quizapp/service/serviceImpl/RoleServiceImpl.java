package com.quizapp.quizapp.service.serviceImpl;

import com.quizapp.quizapp.repository.RoleRepo;
import com.quizapp.quizapp.service.serviceInt.RoleService;

public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    
}
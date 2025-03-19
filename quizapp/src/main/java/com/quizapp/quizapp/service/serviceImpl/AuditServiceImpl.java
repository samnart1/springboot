package com.quizapp.quizapp.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.repository.AuditRepo;
import com.quizapp.quizapp.service.serviceInt.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepo auditRepo;

    public AuditServiceImpl(AuditRepo auditRepo) {
        this.auditRepo = auditRepo;
    }
    
}
package com.quizapp.quizapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizapp.quizapp.models.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, UUID> {

    
}
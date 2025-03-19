package com.quizapp.quizapp.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;

    private boolean randomizeQuestions;
    private boolean randomizeOptions;
    private int timeLimitInMinutes;

    @OneToOne
    private Quiz quiz;
}
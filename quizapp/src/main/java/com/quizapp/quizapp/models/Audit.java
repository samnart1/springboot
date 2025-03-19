package com.quizapp.quizapp.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;

    private String action;
    private String entity;
    private LocalDateTime timestamp;

    @ManyToOne
    private User performedBy;
}
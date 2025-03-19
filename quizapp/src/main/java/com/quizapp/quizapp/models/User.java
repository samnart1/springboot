package com.quizapp.quizapp.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @NotNull
    private String username;
    
    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    private String role;

    @OneToMany(mappedBy = "user")
    private List<QuizAttempt> quizAttempts;

}
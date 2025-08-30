package com.samnart.user_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.samnart.user_service.model.User;
import com.samnart.user_service.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User create(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists!");
        } 
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use!");
        }
        return userRepo.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepo.findById(id)
            .map(user -> {
                user.setFirstName(userDetails.getFirstName());
                user.setLastName(user.getLastName());
                user.setEmail(userDetails.getEmail());
                return userRepo.save(user);
            })
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id + "!"));
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}

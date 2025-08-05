package com.samnart.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samnart.security.dto.RegisterRequest;
import com.samnart.security.entity.User;
import com.samnart.security.exception.UserAlreadyExistsException;
import com.samnart.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        
        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Username Already Exists");
        }

        if (userRepo.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Email Already Exists");
        }

        User user = new User(
            registerRequest.getUsername(),
            registerRequest.getEmail(),
            passwordEncoder.encode(registerRequest.getPassword()),
            registerRequest.getFirstName(),
            registerRequest.getLastName()
        );

        userRepo.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public User findByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Override
    public boolean existsByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepo.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }
    
}

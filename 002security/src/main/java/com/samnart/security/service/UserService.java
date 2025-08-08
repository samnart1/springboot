package com.samnart.security.service;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.samnart.security.dto.RegisterRequest;
import com.samnart.security.entity.User;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    // UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

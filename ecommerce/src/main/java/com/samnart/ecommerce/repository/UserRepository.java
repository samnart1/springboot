// package com.samnart.ecommerce.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.samnart.ecommerce.model.User;

// @Repository
// public interface UserRepository extends JpaRepository<User, Long> {}





package com.samnart.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
    
}

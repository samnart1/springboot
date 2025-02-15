// package com.samnart.ecommerce.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.samnart.ecommerce.model.Role;

// @Repository
// public interface RoleRepository extends JpaRepository<Role, Long> {}



package com.samnart.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.ecommerce.model.AppRole;
import com.samnart.ecommerce.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(AppRole roleUser);
    
}

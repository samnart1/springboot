package com.samnart.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.ecommerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}

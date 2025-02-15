package com.samnart.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.ecommerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
}

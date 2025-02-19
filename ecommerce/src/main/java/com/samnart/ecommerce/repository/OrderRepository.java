package com.samnart.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
}

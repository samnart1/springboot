package com.samnart.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.ecommerce.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}

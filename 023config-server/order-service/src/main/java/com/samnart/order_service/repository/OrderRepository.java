package com.samnart.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.order_service.model.Order;
import com.samnart.order_service.model.Order.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);
    List<Order> findByStatus(OrderStatus status);
}

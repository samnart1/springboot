package com.samnart.order_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samnart.order_service.client.UserServiceClient;
import com.samnart.order_service.model.Order;
import com.samnart.order_service.model.Order.OrderStatus;
import com.samnart.order_service.repository.OrderRepository;

@Service
public class UserService {
    
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private OrderRepository orderRepo;

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public List<Order> getOrdersByUserId(Long id) {
        return orderRepo.findByUserId(id);
    }

    public Order createOrder(Order order) {
        try {
            userServiceClient.getUserById(order.getUserId());

        } catch (Exception ex) {
            throw new IllegalArgumentException("User not found with id " + order.getUserId());
        }

        return orderRepo.save(order);
    }

    public Order updateOrderStatus(Long id, String status) {
        return orderRepo.findById(id)
            .map(order -> {
                order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
                return orderRepo.save(order);
            })
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id " + id));
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

}

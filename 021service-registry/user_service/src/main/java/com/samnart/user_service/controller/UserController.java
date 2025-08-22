package com.samnart.user_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.user_service.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Value("${server.port}")
    private String port;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    private List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User(1L, "Ali John", "alijohn@example.com", "ADMIN"));
        users.add(new User(1L, "Bob Smile", "bob@example.com", "USER"));
        users.add(new User(1L, "Carol Dave", "carol@example.com", "USER"));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId((long) (users.size() + 1));
        users.add(user);
        return user;
    }

    @GetMapping("/helath")
    public String health() {
        return "User Service is running on port" + port +
            " | Instance: " + instanceId +
            " | Status: UP";
    }

    @GetMapping("/info")
    public String info() {
        return "User Service - Manages user data and authentication" +
            " | Running on port: " + port +
            " | Total users: " + users.size();
    }
}

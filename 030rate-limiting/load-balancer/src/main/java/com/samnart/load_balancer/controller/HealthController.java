package com.samnart.load_balancer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.load_balancer.service.HealthCheckService;

@RestController
@RequestMapping("/actuator")
public class HealthController {
    
    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("loadBalancer", "ACTIVE");
        health.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(health, HttpStatus.OK);
    }

}

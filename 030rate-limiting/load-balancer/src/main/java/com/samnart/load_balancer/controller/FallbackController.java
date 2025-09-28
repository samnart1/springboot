package com.samnart.load_balancer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

        @RequestMapping("/fallback")
        public ResponseEntity<Map<String, Object>> fallback() {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Service temporarily unavailable");
            response.put("message", "All API Gateway instances are down. Please try again later.");
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());

            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    
}

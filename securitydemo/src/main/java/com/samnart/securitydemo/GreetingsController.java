package com.samnart.securitydemo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class GreetingsController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Missing my Painter's boobs!!!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint() {
        return new String("Hello, User!");
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return new String("Hello, Admin");
    }
    
    
    
}

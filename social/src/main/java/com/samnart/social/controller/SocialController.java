package com.samnart.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.social.models.SocialUser;
import com.samnart.social.service.SocialService;

@RestController
@RequestMapping("/api")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @GetMapping("/social/users")
    public ResponseEntity<List<SocialUser>> getUsers() {
        return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
    }
    
    @PostMapping("/social/users")
    public ResponseEntity<SocialUser> saveUser(@RequestBody SocialUser socialUser) {
        return new ResponseEntity<>(socialService.saveUser(socialUser), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/social/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        socialService.deleteUser(userId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
    
}

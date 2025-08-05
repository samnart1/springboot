package com.samnart.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private UserResponse user;
}

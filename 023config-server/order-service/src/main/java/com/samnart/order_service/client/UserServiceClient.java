package com.samnart.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import com.samnart.order_service.dto.UserDto;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserServiceClient {
    
    @GetExchange("/{id}")
    UserDto getUserById(@PathVariable Long id);
}

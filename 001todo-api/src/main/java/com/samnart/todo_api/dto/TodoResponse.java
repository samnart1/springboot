package com.samnart.todo_api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TodoResponse {
    
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

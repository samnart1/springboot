package com.samnart.auto.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    
    private String Id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    
}
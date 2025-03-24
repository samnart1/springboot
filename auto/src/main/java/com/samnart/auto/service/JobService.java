package com.samnart.auto.service;



import java.util.List;

import com.samnart.auto.model.Job;

public interface JobService {
    List<Job> findAll();
    void add(Job job);    
}

package com.samnart.auto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.samnart.auto.model.Job;

@Service
public class JobServiceImpl implements JobService {

    List<Job> jobs = new ArrayList<>(); 

    @Override
    public List<Job> findAll() {
        return jobs;
    }

    @Override
    public void add(Job job) {
        jobs.add(job);
    }
    
}

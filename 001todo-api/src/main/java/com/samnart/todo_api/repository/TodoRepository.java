package com.samnart.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.todo_api.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}

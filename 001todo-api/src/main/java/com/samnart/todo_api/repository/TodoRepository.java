package com.samnart.todo_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.samnart.todo_api.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByCompleted(Boolean completed);

    List<Todo> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Todo> findByTitleOrDescriptionContaining(@Param("keyword") String keyword);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = true")
    Long countCompletedTodos();

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = false")
    Long countPendingTodos();
    
    List<Todo> findAllByOrderByCreatedAtDesc();

    List<Todo> findAllByOrderByCompletedAscCreatedAtDesc();
}

package com.samnart.todo_api.service;

import java.util.List;

import com.samnart.todo_api.dto.TodoRequest;
import com.samnart.todo_api.dto.TodoResponse;

import lombok.Data;

public interface TodoService {
    TodoResponse createTodo(TodoRequest request);
    List<TodoResponse> getAllTodos();
    TodoResponse getTodoById(Long id);
    TodoResponse updateTodo(Long id, TodoRequest request);
    void deleteTodo(Long id);
    TodoResponse toggleTodoCompletion(Long id);
    List<TodoResponse> getTodosByCompletionStatus(Boolean completed);
    List<TodoResponse> searchTodos(String keyword);
    TodoStats getTodoStats();

    @Data
    class TodoStats {
        private final Long totalTodos;
        private final Long completedTodos;
        private final Long pendingTodos;
    }
}

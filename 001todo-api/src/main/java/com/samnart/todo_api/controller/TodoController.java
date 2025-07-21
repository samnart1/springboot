package com.samnart.todo_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.todo_api.dto.TodoRequest;
import com.samnart.todo_api.dto.TodoResponse;
import com.samnart.todo_api.service.TodoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request) {
        TodoResponse response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodoo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        TodoResponse todo = todoService.updateTodo(id, request);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
        // return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoResponse> toggleTodoCompletion(@PathVariable Long id) {
        TodoResponse todo = todoService.toggleTodoCompletion(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TodoResponse>> getTodoByStatus(@PathVariable Boolean completed) {
        List<TodoResponse> todos = todoService.getTodosByCompletionStatus(completed);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoResponse>> searchTodos(@RequestBody String keyword) {
        List<TodoResponse> todos = todoService.searchTodos(keyword);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<TodoService.TodoStats> getTodoStats() {
        TodoService.TodoStats stats = todoService.getTodoStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoResponse>> getCompletedTodos() {
        List<TodoResponse> todos = todoService.getTodosByCompletionStatus(true);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TodoResponse>> getPendingTodos() {
        List<TodoResponse> todos = todoService.getTodosByCompletionStatus(false);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}

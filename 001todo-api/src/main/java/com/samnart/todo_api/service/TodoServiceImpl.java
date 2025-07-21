package com.samnart.todo_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samnart.todo_api.dto.TodoRequest;
import com.samnart.todo_api.dto.TodoResponse;
import com.samnart.todo_api.entity.Todo;
import com.samnart.todo_api.exception.TodoNotFoundException;
import com.samnart.todo_api.repository.TodoRepository;

// import jakarta.transaction.Transactional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepo;

    public TodoServiceImpl(TodoRepository todoRepo) {
        this.todoRepo = todoRepo;
    }

    @Override
    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted());

        Todo savedTodo = todoRepo.save(todo);
        return TodoResponse.from(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        List<Todo> todos = todoRepo.findAllByOrderByCompletedAscCreatedAtDesc();
        return todos.stream()
            .map(TodoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepo.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        return TodoResponse.from(todo);
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo existingTodo = todoRepo.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        
        existingTodo.setTitle(request.getTitle());
        existingTodo.setDescription(request.getDescription());

        if (request.getCompleted() != null) {
            existingTodo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoRepo.save(existingTodo);
        return TodoResponse.from(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!todoRepo.existsById(id)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        todoRepo.deleteById(id);
    }

    @Override
    public TodoResponse toggleTodoCompletion(Long id) {
        Todo todo = todoRepo.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        todo.setCompleted(!todo.getCompleted());
        Todo updatedTodo = todoRepo.save(todo);
        return TodoResponse.from(updatedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByCompletionStatus(Boolean completed) {
        List<Todo> todos = todoRepo.findByCompleted(completed);
        return todos.stream()
            .map(TodoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> searchTodos(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllTodos();
        }

        List<Todo> todos = todoRepo.findByTitleOrDescriptionContaining(keyword.trim());
        return todos.stream()
            .map(TodoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TodoStats getTodoStats() {
        Long totalTodos = todoRepo.count();
        Long completedTodos = todoRepo.countCompletedTodos();
        Long pendingTodos = todoRepo.countPendingTodos();

        return new TodoStats(totalTodos, completedTodos, pendingTodos);
    }

    // private TodoResponse convertToResponse(Todo todo) {
    //     return TodoResponse.from(todo);
    // }

    // private void updateTodoFromRequest(Todo todo, TodoRequest request) {
    //     if (request.getTitle() != null) {
    //         todo.setTitle(request.getTitle());
    //     }
    //     if (request.getDescription() != null) {
    //         todo.setDescription(request.getDescription());
    //     }
    //     if (request.getCompleted() != null) {
    //         todo.setCompleted(request.getCompleted());  
    //     }
    // }
    
}

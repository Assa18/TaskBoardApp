package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.model.Todo;

import java.util.List;

public interface TodoService {

    Todo getById(Long id);

    List<Todo> getAllTodos();

    void addTodo(Todo todo);

    void deleteTodo(Long id);

    void updateTodo(Todo todo);
}

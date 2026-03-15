package edu.bbte.idde.mtim2378.repository;

import edu.bbte.idde.mtim2378.model.Todo;
import java.util.List;

public interface TodoRepository {

    Todo findById(Long id);

    List<Todo> findAll();

    Todo addTodo(Todo todo);

    Todo updateTodo(Todo todo);

    void deleteTodo(Long id);
}

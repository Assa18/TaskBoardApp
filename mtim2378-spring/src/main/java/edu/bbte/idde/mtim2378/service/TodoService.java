package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.controller.dto.MoveTodoDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;

import java.util.List;

public interface TodoService {

    RequestTodoDetailedDto getById(Long id);

    List<RequestTodoShortDto> getAllTodos();

    RequestTodoDetailedDto addTodo(Long boardId, SaveTodoDto todoDto);

    void deleteTodo(Long id);

    RequestTodoDetailedDto updateTodo(Long todoId, SaveTodoDto todoDto);

    RequestTodoDetailedDto moveTodo(Long todoId, MoveTodoDto todoDto);
}

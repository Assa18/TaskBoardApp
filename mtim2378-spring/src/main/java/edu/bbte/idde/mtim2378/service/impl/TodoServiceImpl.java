package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.controller.dto.MoveTodoDto;
import edu.bbte.idde.mtim2378.mapper.TodoMapper;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;
import edu.bbte.idde.mtim2378.model.Board;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import edu.bbte.idde.mtim2378.repository.TodoRepository;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.exception.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!datajpa")
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public RequestTodoDetailedDto getById(Long id) {
        try {
            if (!todoRepository.existsById(id)) {
                throw new TodoNotFoundException("Todo with id " + id + " not found!");
            }
            return todoMapper.toRequestDetailedDto(todoRepository.findById(id));
        } catch (RepositoryException e) {
            log.error("Failed to get todo by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<RequestTodoShortDto> getAllTodos() {
        try {
            return todoRepository.findAll().stream().map(todoMapper::toRequestShortDto).toList();
        } catch (RepositoryException e) {
            log.error("Failed to get todos!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public RequestTodoDetailedDto addTodo(Long boardId, SaveTodoDto todoDto) {
        try {
            Todo todo = todoMapper.toTodo(todoDto);
            Board board = new Board();
            board.setId(boardId);
            todo.setBoard(board);

            return todoMapper.toRequestDetailedDto(todoRepository.addTodo(todo));
        } catch (RepositoryException ex) {
            log.error("Failed to add todo!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteTodo(Long id) {
        try {
            if (!todoRepository.existsById(id)) {
                throw new TodoNotFoundException("Todo with id " + id + " not found!");
            }
            todoRepository.deleteTodo(id);
        } catch (RepositoryException ex) {
            log.error("Failed to delete todo by id!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public RequestTodoDetailedDto updateTodo(Long todoId, SaveTodoDto todoDto) {
        try {
            Todo todo = todoMapper.toTodo(todoDto);
            todo.setId(todoId);
            return todoMapper.toRequestDetailedDto(todoRepository.updateTodo(todo));
        } catch (RepositoryException ex) {
            log.error("Failed to update todo!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public RequestTodoDetailedDto moveTodo(Long todoId, MoveTodoDto todoDto) {
        try {
            if (!todoRepository.existsById(todoId)) {
                throw new TodoNotFoundException("Todo with id "
                        + todoDto.getNewBoardId() + " not found!");
            }
            Todo todo = todoRepository.findById(todoId);
            Board board = new Board();
            board.setId(todoDto.getNewBoardId());
            todo.setBoard(board);
            return todoMapper.toRequestDetailedDto(todoRepository.updateTodo(todo));
        } catch (RepositoryException ex) {
            log.error("Failed to move todo!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}

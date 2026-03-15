package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.controller.dto.MoveTodoDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;
import edu.bbte.idde.mtim2378.mapper.TodoMapper;
import edu.bbte.idde.mtim2378.model.Board;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.jpa.JpaBoardRepository;
import edu.bbte.idde.mtim2378.repository.jpa.JpaTodoRepository;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.exception.BoardNotFoundException;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.exception.TodoNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Profile("datajpa")
@Slf4j
@RequiredArgsConstructor
public class JpaTodoServiceImpl implements TodoService {

    private final JpaTodoRepository todoRepository;
    private final JpaBoardRepository boardRepository;
    private final TodoMapper todoMapper;

    @Override
    public RequestTodoDetailedDto getById(Long id) {
        try {
            if (!todoRepository.existsById(id)) {
                throw new TodoNotFoundException("Todo with id " + id + " not found!");
            }
            return todoMapper.toRequestDetailedDto(todoRepository.findById(id).get());
        } catch (DataAccessException e) {
            log.error("Failed to get todo by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<RequestTodoShortDto> getAllTodos() {
        try {
            return todoRepository.findAll().stream().map(todoMapper::toRequestShortDto).toList();
        } catch (DataAccessException e) {
            log.error("Failed to get todos!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public RequestTodoDetailedDto addTodo(Long boardId, SaveTodoDto todoDto) {
        try {
            Todo todo = todoMapper.toTodo(todoDto);
            Board board = boardRepository.findById(boardId).orElseThrow(() ->
                    new BoardNotFoundException("Board with id " + boardId + " not found!"));
            todo.setBoard(board);
            board.addTodo(todo);

            return todoMapper.toRequestDetailedDto(todoRepository.save(todo));
        } catch (DataAccessException ex) {
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
            todoRepository.deleteById(id);
        } catch (DataAccessException ex) {
            log.error("Failed to delete todo by id!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public RequestTodoDetailedDto updateTodo(Long todoId, SaveTodoDto todoDto) {
        try {
            Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                    new TodoNotFoundException("Todo with id " + todoId + " not found!"));
            todo.setSeverity(todoDto.getSeverity());
            todo.setTitle(todoDto.getTitle());
            todo.setDeadline(todoDto.getDeadline());
            todo.setDescription(todoDto.getDescription());
            return todoMapper.toRequestDetailedDto(todoRepository.save(todo));
        } catch (DataAccessException ex) {
            log.error("Failed to update todo by id!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public RequestTodoDetailedDto moveTodo(Long todoId, MoveTodoDto todoDto) {
        try {
            Board board = boardRepository.findById(todoDto.getNewBoardId()).orElseThrow(() ->
                    new BoardNotFoundException("Board with id "
                            + todoDto.getNewBoardId() + " not found!"));
            Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                    new TodoNotFoundException("Todo with id " + todoId + " not found!"));
            todo.setBoard(board);
            return todoMapper.toRequestDetailedDto(todoRepository.save(todo));
        } catch (DataAccessException ex) {
            log.error("Failed to move todo by id: " + todoId, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}

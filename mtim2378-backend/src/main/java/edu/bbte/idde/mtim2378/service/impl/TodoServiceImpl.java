package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import edu.bbte.idde.mtim2378.repository.TodoRepository;
import edu.bbte.idde.mtim2378.repository.RepositoryFactory;
import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository = RepositoryFactory.getInstance().getTodoRepository();
    private static final Logger LOG = LoggerFactory.getLogger(TodoServiceImpl.class);

    @Override
    public Todo getById(Long id) {
        try {
            return todoRepository.findById(id);
        } catch (RepositoryException e) {
            LOG.error("Failed to get todo by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public void addTodo(Todo todo) {
        try {
            todoRepository.addTodo(todo);
        } catch (RepositoryException ex) {
            LOG.error("Failed to add todo!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteTodo(Long id) {
        try {
            todoRepository.deleteTodo(id);
        } catch (RepositoryException ex) {
            LOG.error("Failed to delete todo by id!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateTodo(Todo todo) {
        try {
            todoRepository.updateTodo(todo);
        } catch (RepositoryException ex) {
            LOG.error("Failed to update todo by id!", ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}

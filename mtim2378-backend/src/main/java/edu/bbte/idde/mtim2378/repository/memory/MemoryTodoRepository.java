package edu.bbte.idde.mtim2378.repository.memory;

import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import edu.bbte.idde.mtim2378.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MemoryTodoRepository implements TodoRepository {

    private final Map<Long, Todo> data;

    private final Random random = new Random();

    private static final Logger LOG = LoggerFactory.getLogger(MemoryTodoRepository.class);

    public MemoryTodoRepository() {
        data = new HashMap<>();
    }

    @Override
    public Todo findById(Long id) {
        if (!data.containsKey(id)) {
            LOG.error("No such id {}", id);
            throw new RepositoryException("Todo with id " + id + " not found!");
        }

        return data.get(id);
    }

    @Override
    public List<Todo> findAll() {
        return data.values().stream().toList();
    }

    @Override
    public Todo addTodo(Todo todo) {
        Long id = random.nextLong(1, 1000);
        while (data.containsKey(id)) {
            id = random.nextLong(1, 1000);
        }

        todo.setId(id);
        data.put(id, todo);
        return todo;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        if (!data.containsKey(todo.getId())) {
            LOG.error("No such id {}", todo.getId());
            throw new RepositoryException("Todo with id " + todo.getId() + " not found!");
        }

        return data.replace(todo.getId(), todo);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!data.containsKey(id)) {
            LOG.error("No such id {}", id);
            throw new RepositoryException("Todo with id " + id + " not found!");
        }

        data.remove(id);
    }
}

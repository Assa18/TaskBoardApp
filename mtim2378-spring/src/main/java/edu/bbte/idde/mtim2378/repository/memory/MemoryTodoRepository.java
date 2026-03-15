package edu.bbte.idde.mtim2378.repository.memory;

import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import edu.bbte.idde.mtim2378.repository.TodoRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Repository
@Slf4j
@Profile("dev")
public class MemoryTodoRepository implements TodoRepository {

    private Map<Long, Todo> data;

    private Random random;

    @PostConstruct
    void initialize() {
        data = new HashMap<>();
        random = new Random();
    }

    @Override
    public Todo findById(Long id) {
        if (!data.containsKey(id)) {
            log.error("No such id {}", id);
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
            log.error("No such id {}", todo.getId());
            throw new RepositoryException("Todo with id " + todo.getId() + " not found!");
        }

        return data.replace(todo.getId(), todo);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!data.containsKey(id)) {
            log.error("No such id {}", id);
            throw new RepositoryException("Todo with id " + id + " not found!");
        }

        data.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return data.containsKey(id);
    }
}

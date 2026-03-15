package edu.bbte.idde.mtim2378.repository.memory;

import edu.bbte.idde.mtim2378.repository.RepositoryFactory;
import edu.bbte.idde.mtim2378.repository.TodoRepository;

public class MemoryRepositoryFactory extends RepositoryFactory {

    private TodoRepository instance;

    @Override
    public synchronized TodoRepository getTodoRepository() {
        if (instance == null) {
            instance = new MemoryTodoRepository();
        }

        return instance;
    }
}

package edu.bbte.idde.mtim2378.repository.jdbc;

import edu.bbte.idde.mtim2378.repository.RepositoryFactory;
import edu.bbte.idde.mtim2378.repository.TodoRepository;

public class JdbcRepositoryFactory extends RepositoryFactory {

    private TodoRepository instance;

    @Override
    public synchronized TodoRepository getTodoRepository() {
        if (instance == null) {
            instance = new JdbcTodoRepository();
        }

        return instance;
    }
}

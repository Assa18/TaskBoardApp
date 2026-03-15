package edu.bbte.idde.mtim2378.repository;

import edu.bbte.idde.mtim2378.config.DatabaseConfiguration;
import edu.bbte.idde.mtim2378.config.ConfigurationFactory;
import edu.bbte.idde.mtim2378.repository.jdbc.JdbcRepositoryFactory;
import edu.bbte.idde.mtim2378.repository.memory.MemoryRepositoryFactory;

public abstract class RepositoryFactory {

    private static RepositoryFactory instance;
    private static final DatabaseConfiguration configuration =
            ConfigurationFactory.getConfiguration().getDatabase();

    public abstract TodoRepository getTodoRepository();

    public static synchronized RepositoryFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        String repoImplType = configuration.getDbImplType();
        if ("jdbc".equals(repoImplType)) {
            instance = new JdbcRepositoryFactory();
        } else {
            instance = new MemoryRepositoryFactory();
        }

        return instance;
    }
}

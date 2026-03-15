package edu.bbte.idde.mtim2378.repository.jdbc;

import edu.bbte.idde.mtim2378.config.DatabaseConfiguration;
import edu.bbte.idde.mtim2378.config.ConfigurationFactory;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class ConnectionManager {

    private final List<Connection> connections;
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);
    private static final DatabaseConfiguration configuration =
            ConfigurationFactory.getConfiguration().getDatabase();
    private static ConnectionManager instance;

    private ConnectionManager() {
        connections = new LinkedList<>();

        try {
            Class.forName(configuration.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            LOG.error("Error creating connection manager!", e);
            throw new RepositoryException("Error creating connection manager: " + e.getMessage());
        }

        for (int i = 0; i < configuration.getDbConnectionPoolSize(); i++) {
            try {
                connections.add(DriverManager.getConnection(configuration.getJdbcUrl(),
                        configuration.getJdbcUsername(),
                        configuration.getJdbcPassword()));
            } catch (SQLException e) {
                LOG.error("Error creating connection pool!", e);
                throw new RepositoryException("Error creating connection pool: " + e.getMessage());
            }
        }
        LOG.info("Connection pool successfully initialized!");
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connections.isEmpty()) {
            throw new RepositoryException("No connections available!");
        }

        return connections.removeFirst();
    }

    public void returnConnection(Connection con) {
        connections.add(con);
    }
}

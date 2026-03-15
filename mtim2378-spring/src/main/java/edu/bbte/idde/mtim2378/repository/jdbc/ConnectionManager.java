package edu.bbte.idde.mtim2378.repository.jdbc;

import edu.bbte.idde.mtim2378.repository.RepositoryException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Repository
@Profile("default")
public class ConnectionManager {

    private List<Connection> connections;

    @Value("${database.jdbcDriver}")
    private String jdbcDriver;

    @Value("${database.dbConnectionPoolSize}")
    private int connectionPoolSize;

    @Value("${database.jdbcUrl}")
    private String jdbcUrl;

    @Value("${database.jdbcUsername}")
    private String jdbcUsername;

    @Value("${database.jdbcPassword}")
    private String jdbcUserPassword;

    @PostConstruct
    void initialize() {
        connections = new LinkedList<>();

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            log.error("Error creating connection manager!", e);
            throw new RepositoryException("Error creating connection manager: " + e.getMessage());
        }

        for (int i = 0; i < connectionPoolSize; i++) {
            try {
                connections.add(DriverManager.getConnection(jdbcUrl, jdbcUsername,
                        jdbcUserPassword));
            } catch (SQLException e) {
                log.error("Error creating connection pool!", e);
                throw new RepositoryException("Error creating connection pool: " + e.getMessage());
            }
        }
        log.info("Connection pool successfully initialized!");
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

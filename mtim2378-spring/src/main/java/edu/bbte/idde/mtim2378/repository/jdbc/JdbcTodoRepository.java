package edu.bbte.idde.mtim2378.repository.jdbc;

import edu.bbte.idde.mtim2378.model.Severity;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.repository.RepositoryException;
import edu.bbte.idde.mtim2378.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Profile("default")
public class JdbcTodoRepository implements TodoRepository {

    private final ConnectionManager conManager;

    @Override
    public Todo findById(Long id) {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * from Todos where id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                log.warn("Todo with id {} not found!", id);
                throw new RepositoryException("Todo not found with id: " + id);
            }

            return getTodoFromResultSet(rs);
        } catch (SQLException e) {
            log.error("Error finding id!", e);
            throw new RepositoryException("Error finding id: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }
    }

    @Override
    public List<Todo> findAll() {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * from Todos");
            ResultSet rs = ps.executeQuery();
            List<Todo> todoList = new LinkedList<>();
            while (rs.next()) {
                Todo todo = getTodoFromResultSet(rs);
                todoList.add(todo);
            }
            return todoList;
        } catch (SQLException e) {
            log.error("Error finding all todos!", e);
            throw new RepositoryException("Error finding all todos: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }
    }

    private Todo getTodoFromResultSet(ResultSet rs) throws SQLException {
        Todo todo = new Todo();
        todo.setId(rs.getLong("id"));
        todo.setTitle(rs.getString("title"));
        todo.setDescription(rs.getString("description"));
        todo.setDeadline(rs.getObject("deadline", LocalDateTime.class));
        todo.setSeverity(Severity.valueOf(rs.getString("severity")));

        return todo;
    }

    @Override
    public Todo addTodo(Todo todo) {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO Todos(title, description,"
                    + "deadline, severity, done) values(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTitle());
            statement.setString(2, todo.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(todo.getDeadline()));
            statement.setString(4, todo.getSeverity().toString());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                todo.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            log.error("Error creating todo!", e);
            throw new RepositoryException("Error creating todo: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }
        return todo;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Todos SET title=?, description=?,"
                    + "deadline=?, severity=?, done=? WHERE ID=?");
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(todo.getDeadline()));
            ps.setString(4, todo.getSeverity().toString());
            ps.setLong(6, todo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while updating todo!", e);
            throw new RepositoryException("Error while updating todo: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }

        return todo;
    }

    @Override
    public void deleteTodo(Long id) {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Todos WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting todo!", e);
            throw new RepositoryException("Error while deleting user: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }
    }

    @Override
    public boolean existsById(Long id) {
        Connection con = conManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Todos WHERE id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error("Error checking todo!", e);
            throw new RepositoryException("Error checking todo: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                conManager.returnConnection(con);
            }
        }
    }
}

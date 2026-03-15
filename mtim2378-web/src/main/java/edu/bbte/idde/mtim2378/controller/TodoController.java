package edu.bbte.idde.mtim2378.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.mtim2378.model.Severity;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/todos/*")
public class TodoController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private static final TodoService todoService = TodoServiceFactory.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("TodoController initialized!");
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || "/".equals(pathInfo) || pathInfo.isEmpty()) {
                List<Todo> todos = todoService.getAllTodos();

                resp.setStatus(200);
                objectMapper.writeValue(resp.getOutputStream(), todos);
                return;
            }

            long id;
            try {
                id = Long.parseLong(pathInfo.substring(1));
            } catch (NumberFormatException ex) {
                logger.error(ex.getMessage(), ex);
                resp.setStatus(400);
                objectMapper.writeValue(resp.getOutputStream(), Map.of("error", "Invalid id"));
                return;
            }

            Todo todo = todoService.getById(id);
            resp.setStatus(200);
            objectMapper.writeValue(resp.getOutputStream(), todo);
        } catch (ServiceException ex) {
            try {
                respondBadRequest(resp, "Todo not found");
            } catch (IOException e) {
                logger.error(ex.getMessage(), ex);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Map<String, Object> json = objectMapper.readValue(
                    req.getInputStream(),
                    new TypeReference<>() {}
            );

            Todo todo = validateAndSetTodo(resp, json);
            if (todo == null) {
                return;
            }

            todoService.addTodo(todo);

            resp.setStatus(201);
            resp.setHeader("Location", "/todos/" + todo.getId());
        } catch (StreamReadException | DatabindException ex) {
            logger.error(ex.getMessage(), ex);
            resp.setStatus(400);
        } catch (IOException ex) {
            logger.error("Failed to send response", ex);
            resp.setStatus(500);
        } catch (ServiceException ex) {
            logger.error("Not found", ex);
            resp.setStatus(404);
        }
    }

    private Todo validateAndSetTodo(HttpServletResponse resp,
                                    Map<String, Object> json) throws IOException {
        validateJson(json, resp);

        LocalDateTime deadLine;
        try {
            deadLine = LocalDateTime.parse((String) json.get("deadLine"));
        } catch (DateTimeParseException e) {
            respondBadRequest(resp, "Invalid date format for deadLine (expected ISO-8601 LocalDateTime)");
            return null;
        }

        Severity severity;
        try {
            severity = Severity.valueOf(((String) json.get("severity")).toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            respondBadRequest(resp, "Invalid severity (valid: URGENT, NORMAL, LAZY)");
            return null;
        }

        Todo todo = new Todo();
        todo.setTitle((String) json.get("title"));
        todo.setDescription((String) json.get("description"));
        todo.setDone((Boolean) json.get("done"));
        todo.setDeadLine(deadLine);
        todo.setSeverity(severity);

        return todo;
    }

    private void validateJson(Map<String, Object> json,
                              HttpServletResponse resp) throws IOException {

        Map<String, Class<?>> required = Map.of(
                "title", String.class,
                "description", String.class,
                "done", Boolean.class,
                "deadLine", String.class,
                "severity", String.class
        );

        for (var entry : required.entrySet()) {
            String field = entry.getKey();
            Class<?> type = entry.getValue();

            Object value = json.get(field);

            if (value == null || !type.isInstance(value)) {
                respondBadRequest(resp, "Missing or invalid field: " + field);
                return;
            }

            if (value instanceof String && ((String) value).isBlank()) {
                respondBadRequest(resp, "Missing or invalid field: " + field);
                return;
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || "/".equals(pathInfo) || pathInfo.isEmpty()) {
                respondBadRequest(resp, "Missing ID in URL. Expected /todos/{id}");
                return;
            }

            String idStr = pathInfo.substring(1);
            long id;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                respondBadRequest(resp, "Invalid ID format: " + idStr);
                return;
            }

            Todo existing = todoService.getById(id);

            Map<String, Object> json = objectMapper.readValue(
                    req.getInputStream(), new TypeReference<>() {}
            );

            validateJson(json, resp);

            existing.setTitle((String) json.get("title"));
            existing.setDescription((String) json.get("description"));
            existing.setDone((Boolean) json.get("done"));

            try {
                existing.setDeadLine(LocalDateTime.parse((String) json.get("deadLine")));
                existing.setSeverity(Severity.valueOf(((String) json.get("severity"))
                        .toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                respondBadRequest(resp, "Invalid input data!");
                return;
            }

            todoService.updateTodo(existing);

            resp.setStatus(200);
        } catch (ServiceException ex) {
            logger.error("Todo not found!", ex);
            resp.setStatus(404);
        } catch (StreamReadException | DatabindException ex) {
            logger.error(ex.getMessage(), ex);
            resp.setStatus(400);
        } catch (IOException ex) {
            logger.error("Failed to parse request JSON", ex);
            resp.setStatus(500);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || "/".equals(pathInfo) || pathInfo.isEmpty()) {
                respondBadRequest(resp, "Missing ID in URL. Expected /todos/{id}");
                return;
            }

            String idStr = pathInfo.substring(1);

            long id;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                respondBadRequest(resp, "Invalid id: " + idStr);
                return;
            }

            todoService.deleteTodo(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ServiceException ex) {
            logger.error("Service error during delete", ex);
            resp.setStatus(500);

        } catch (IOException ex) {
            logger.error("Failed to parse request JSON", ex);
            resp.setStatus(500);
        }
    }

    private void respondBadRequest(HttpServletResponse resp, String message) throws IOException {
        logger.error(message);
        resp.setStatus(400);
        objectMapper.writeValue(resp.getOutputStream(), Map.of("error", message));
    }
}

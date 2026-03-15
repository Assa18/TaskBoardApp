package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/index")
public class JspController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(JspController.class);
    private final TodoService todoService = TodoServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("todos", todoService.getAllTodos());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            logger.error("Error processing request!", ex);
        } catch (ServiceException ex) {
            logger.error("Error getting all todos: {}", ex.getMessage(), ex);
        }
    }
}

package edu.bbte.idde.mtim2378.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String userName = "admin";
    private static final String password = "admin";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        try {
            if (userName.equals(user) && password.equals(pass)) {
                req.getSession().setAttribute("loggedIn", true);
                resp.sendRedirect(req.getContextPath() + "/index");
            } else {
                req.setAttribute("error", "Invalid username or password");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (IOException | ServletException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

package edu.bbte.idde.mtim2378.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/index")
public class AuthFilter extends HttpFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
        try {
            String path = req.getRequestURI();
            boolean loggedIn = req.getSession().getAttribute("loggedIn") != null;

            boolean isLoginPage = path.endsWith("login.jsp");
            boolean isLoginServlet = path.endsWith("/login");
            boolean isLogout = path.endsWith("/logout");

            if (isLoginPage || isLoginServlet || isLogout) {
                chain.doFilter(req, resp);
                return;
            }

            if (!loggedIn) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            chain.doFilter(req, resp);
        } catch (ServletException | IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

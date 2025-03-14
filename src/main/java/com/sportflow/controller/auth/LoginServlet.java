// LoginServlet.java
package com.sportflow.controller.auth;

import com.sportflow.dao.UserDAO;
import com.sportflow.model.User;
import com.sportflow.util.HashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.findByUsername(username);

        if (user != null && HashUtil.checkPassword(password, user.getPassword())) {
            // Authentication successful
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on user role
            switch (user.getRole()) {
                case "ADMIN":
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    break;
                case "ENTRAINER":
                    response.sendRedirect(request.getContextPath() + "/entrainer/dashboard");
                    break;
                case "MEMBER":
                    response.sendRedirect(request.getContextPath() + "/member/dashboard");
                    break;
                default:
                    // Handle unexpected role (shouldn't happen)
                    response.sendRedirect(request.getContextPath() + "/login");
            }
        } else {
            // Authentication failed
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }
}
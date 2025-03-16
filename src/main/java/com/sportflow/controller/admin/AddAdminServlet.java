package com.sportflow.controller.admin;

import com.sportflow.dao.UserDAO;
import com.sportflow.model.User;
import com.sportflow.util.HashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddAdminServlet", urlPatterns = {"/admin/add-admin"})
public class AddAdminServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response);
            return;
        }

        try {
            User newAdmin = new User();
            newAdmin.setUsername(username);
            newAdmin.setEmail(email);
            newAdmin.setPassword(password);
            newAdmin.setRole("ADMIN");


            userDAO.createUser(newAdmin);

            response.sendRedirect(request.getContextPath() + "/admin/dashboard");

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                if(e.getMessage().contains("username"))
                    request.setAttribute("errorMessage", "Username already exists. Please choose a different username.");
                else
                    request.setAttribute("errorMessage", "Email already exists. Please choose a different Email.");
            } else {
                request.setAttribute("errorMessage", "A database error occurred. Please try again later.");

            }
            request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response);

        }
    }
}
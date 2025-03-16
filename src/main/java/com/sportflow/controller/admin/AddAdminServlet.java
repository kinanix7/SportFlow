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
        userDAO = new UserDAO(); // Initialize the UserDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Display the add-admin form
        request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic input validation (you should add more robust validation)
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response);
            return;
        }

        try {
            // Create a new User object
            User newAdmin = new User();
            newAdmin.setUsername(username);
            newAdmin.setEmail(email);
            newAdmin.setPassword(password); // Password will be hashed in the DAO
            newAdmin.setRole("ADMIN");

            // Use the UserDAO to create the user (and hash the password)
            userDAO.createUser(newAdmin);

            // Redirect to the admin dashboard (or a success page)
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");

        } catch (SQLException e) {
            // Handle database errors (e.g., duplicate username/email)
            if (e.getMessage().contains("Duplicate entry")) { // Check for duplicate key violation
                if(e.getMessage().contains("username"))
                    request.setAttribute("errorMessage", "Username already exists. Please choose a different username.");
                else
                    request.setAttribute("errorMessage", "Email already exists. Please choose a different Email.");
            } else {
                request.setAttribute("errorMessage", "A database error occurred. Please try again later.");

            }
            request.getRequestDispatcher("/WEB-INF/jsp/admin/add-admin.jsp").forward(request, response); // Go back to the form

        }
    }
}
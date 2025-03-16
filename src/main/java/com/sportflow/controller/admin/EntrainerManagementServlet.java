package com.sportflow.controller.admin;

import com.sportflow.dao.EntrainerDAO;
import com.sportflow.dao.UserDAO;
import com.sportflow.model.Entrainer;
import com.sportflow.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EntrainerManagementServlet", urlPatterns = {
        "/admin/entrainers",
        "/admin/entrainers/add",
        "/admin/entrainers/edit",
        "/admin/entrainers/delete"
})
public class EntrainerManagementServlet extends HttpServlet {

    private EntrainerDAO entrainerDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        entrainerDAO = new EntrainerDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Entrainer> entrainers = entrainerDAO.getAllEntrainersWithUserDetails(); // Fetch with User details
            request.setAttribute("entrainers", entrainers);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/entrainers.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/entrainers/add":
                    addEntrainer(request, response);
                    break;
                case "/admin/entrainers/edit":
                    editEntrainer(request, response);
                    break;
                case "/admin/entrainers/delete":
                    deleteEntrainer(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/entrainers");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void addEntrainer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setRole("ENTRAINER");
        userDAO.createUser(user);

        Entrainer entrainer = new Entrainer();
        entrainer.setUserId(user.getId());
        entrainer.setFirstName(request.getParameter("firstName"));
        entrainer.setLastName(request.getParameter("lastName"));
        entrainer.setSpecialty(request.getParameter("specialty"));
        entrainerDAO.createEntrainer(entrainer);

        response.sendRedirect(request.getContextPath() + "/admin/entrainers");
    }


    private void editEntrainer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int entrainerId = Integer.parseInt(request.getParameter("entrainerId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        Entrainer entrainer = entrainerDAO.getEntrainerById(entrainerId);
        User user = userDAO.getUserById(userId);

        entrainer.setFirstName(request.getParameter("firstName"));
        entrainer.setLastName(request.getParameter("lastName"));
        entrainer.setSpecialty(request.getParameter("specialty"));
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));

        entrainerDAO.updateEntrainer(entrainer);
        userDAO.updateUser(user);

        response.sendRedirect(request.getContextPath() + "/admin/entrainers");
    }

    private void deleteEntrainer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int entrainerId = Integer.parseInt(request.getParameter("entrainerId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        entrainerDAO.deleteEntrainer(entrainerId);
        userDAO.deleteUser(userId); // Delete User

        response.sendRedirect(request.getContextPath() + "/admin/entrainers");
    }
}
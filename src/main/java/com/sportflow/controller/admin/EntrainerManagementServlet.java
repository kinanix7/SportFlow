// EntrainerManagementServlet.java
package com.sportflow.controller.admin;

import com.sportflow.dao.EntrainerDAO;
import com.sportflow.dao.UserDAO;
import com.sportflow.model.Entrainer;
import com.sportflow.model.User;
import com.sportflow.util.HashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/entrainers")
public class EntrainerManagementServlet extends HttpServlet {

    private EntrainerDAO entrainerDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        entrainerDAO = new EntrainerDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listEntrainers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteEntrainer(request, response);
                break;
            default:
                listEntrainers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addEntrainer(request, response);
                break;
            case "update":
                updateEntrainer(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/entrainers");
        }
    }

    private void listEntrainers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entrainer> entrainers = entrainerDAO.getAllEntrainers();
        request.setAttribute("entrainers", entrainers);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/entrainers.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/add-entrainer.jsp").forward(request, response);
    }
    private void addEntrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String specialty = request.getParameter("specialty");


            // 1. Create User
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(HashUtil.hashPassword(password));  // Hash!
            user.setRole("ENTRAINER");
            User createdUser = userDAO.createUser(user);
            if (createdUser == null) {
                request.setAttribute("error", "Failed to create user account.");
                showAddForm(request, response);
                return;
            }


            // 2. Create Entrainer
            Entrainer entrainer = new Entrainer();
            entrainer.setUserId(createdUser.getId());
            entrainer.setFirstName(firstName);
            entrainer.setLastName(lastName);
            entrainer.setSpecialty(specialty);
            entrainerDAO.createEntrainer(entrainer);

            response.sendRedirect(request.getContextPath() + "/admin/entrainers");

        }catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            showAddForm(request, response); // Forward back to the add form
        }

    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int entrainerId = Integer.parseInt(request.getParameter("id"));
        Entrainer entrainer = entrainerDAO.getEntrainerById(entrainerId);
        request.setAttribute("entrainer", entrainer);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/edit-entrainer.jsp").forward(request, response);
    }

    private void updateEntrainer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            int entrainerId = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String specialty = request.getParameter("specialty");

            Entrainer entrainer = entrainerDAO.getEntrainerById(entrainerId);
            entrainer.setFirstName(firstName);
            entrainer.setLastName(lastName);
            entrainer.setSpecialty(specialty);

            entrainerDAO.updateEntrainer(entrainer);
            response.sendRedirect(request.getContextPath() + "/admin/entrainers");

        }catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            showEditForm(request, response); // Forward back to the edit form
        }

    }

    private void deleteEntrainer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int entrainerId = Integer.parseInt(request.getParameter("id"));
        entrainerDAO.deleteEntrainer(entrainerId);
        response.sendRedirect(request.getContextPath() + "/admin/entrainers");
    }

}
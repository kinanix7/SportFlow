// EntrainerDashboardServlet.java
package com.sportflow.controller.entrainer;

import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Session;
import com.sportflow.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/entrainer/dashboard")
public class EntrainerDashboardServlet extends HttpServlet {
    private SessionDAO sessionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session
        if (session == null || session.getAttribute("user") == null) {
            // Redirect to login page if no session or user
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Important: Stop processing!
        }

        User user = (User) session.getAttribute("user");

        // Now it's safe to access user.getId()
        List<Session> sessions = sessionDAO.getSessionsByEntrainerId(user.getId());
        request.setAttribute("sessions", sessions);
        request.getRequestDispatcher("/WEB-INF/jsp/entrainer/dashboard.jsp").forward(request, response);
    }
}
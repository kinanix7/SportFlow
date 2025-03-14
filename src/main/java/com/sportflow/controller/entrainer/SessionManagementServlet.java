// SessionManagementServlet.java (Corrected addSession method)
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import  com.sportflow.dao.EntrainerDAO; // Import EntrainerDAO
import com.sportflow.model.Entrainer;


@WebServlet("/entrainer/sessions")
public class SessionManagementServlet extends HttpServlet {

    private SessionDAO sessionDAO;
    private EntrainerDAO entrainerDAO; // Add EntrainerDAO instance

    @Override
    public void init() throws ServletException {
        sessionDAO = new SessionDAO();
        entrainerDAO = new EntrainerDAO(); // Initialize EntrainerDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listSessions(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteSession(request, response);
                break;
            default:
                listSessions(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addSession(request, response);
                break;
            case "update":
                updateSession(request, response);
                break;
            default:
                listSessions(request, response);
        }
    }

    private void listSessions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        List<Session> sessions = sessionDAO.getSessionsByEntrainerId(user.getId());
        request.setAttribute("sessions", sessions);
        request.getRequestDispatcher("/WEB-INF/jsp/entrainer/sessions.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/entrainer/add-session.jsp").forward(request, response);
    }

    private void addSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            User user = (User) session.getAttribute("user");

            // *** CRUCIAL CHANGE: Get Entrainer ID from Entrainer table ***
            Entrainer entrainer = entrainerDAO.getEntrainerByUserId(user.getId()); // Use a new method
            if (entrainer == null) {
                // Handle the case where the user is not associated with an entrainer.
                request.setAttribute("error", "You are not registered as an entrainer.");
                showAddForm(request,response); // Or redirect to an error page
                return;
            }
            Integer entrainerId = entrainer.getId(); // Use the entrainer's ID

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            LocalDate sessionDate = LocalDate.parse(request.getParameter("sessionDate"));
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
            Integer maxParticipants = Integer.parseInt(request.getParameter("maxParticipants"));

            Session newSession = new Session();
            newSession.setEntrainerId(entrainerId); // Use the correct entrainerId
            newSession.setTitle(title);
            newSession.setDescription(description);
            newSession.setSessionDate(sessionDate);
            newSession.setStartTime(startTime);
            newSession.setEndTime(endTime);
            newSession.setMaxParticipants(maxParticipants);

            sessionDAO.createSession(newSession);
            response.sendRedirect(request.getContextPath() + "/entrainer/sessions");

        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date or time format.");
            showAddForm(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format for max participants.");
            showAddForm(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            showAddForm(request, response);
        }
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sessionId = Integer.parseInt(request.getParameter("id"));
        Session session = sessionDAO.getSessionById(sessionId);
        request.setAttribute("session", session);
        request.getRequestDispatcher("/WEB-INF/jsp/entrainer/edit-session.jsp").forward(request, response);
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int sessionId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            LocalDate sessionDate = LocalDate.parse(request.getParameter("sessionDate"));
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
            Integer maxParticipants = Integer.parseInt(request.getParameter("maxParticipants"));

            Session session = sessionDAO.getSessionById(sessionId);

            session.setTitle(title);
            session.setDescription(description);
            session.setSessionDate(sessionDate);
            session.setStartTime(startTime);
            session.setEndTime(endTime);
            session.setMaxParticipants(maxParticipants);
            sessionDAO.updateSession(session);
            response.sendRedirect(request.getContextPath() + "/entrainer/sessions");

        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date/time format.");
            showEditForm(request, response); // Stay on the edit form and show the error
        } catch(NumberFormatException e) {
            request.setAttribute("error", "Invalid number format for Max Participants.");
            showEditForm(request, response);
        }
        catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            showEditForm(request, response);
        }

    }

    private void deleteSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int sessionId = Integer.parseInt(request.getParameter("id"));
            sessionDAO.deleteSession(sessionId);
            response.sendRedirect(request.getContextPath() + "/entrainer/sessions");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to delete session: " + e.getMessage());
            listSessions(request, response);
        }
    }
    // Helper method to get Entrainer ID from User ID

}
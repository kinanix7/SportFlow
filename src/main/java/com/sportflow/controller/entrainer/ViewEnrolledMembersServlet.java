package com.sportflow.controller.entrainer;

import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Member;
import com.sportflow.model.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ViewEnrolledMembersServlet", urlPatterns = {"/entrainer/sessions/viewMembers"})
public class ViewEnrolledMembersServlet extends HttpServlet {

    private SessionDAO sessionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int sessionId = Integer.parseInt(request.getParameter("sessionId"));

        try {
            Session session = sessionDAO.getSessionById(sessionId);
            if (session == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Session not found");
                return;
            }
            request.setAttribute("session", session);

            List<Member> enrolledMembers = sessionDAO.getMembersBySessionId(sessionId);
            request.setAttribute("enrolledMembers", enrolledMembers);

            request.getRequestDispatcher("/WEB-INF/jsp/entrainer/view-enrolled-members.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
package com.sportflow.controller.entrainer;

import com.sportflow.dao.BookingDAO;
import com.sportflow.dao.EntrainerDAO;
import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Booking;
import com.sportflow.model.Entrainer;
import com.sportflow.model.Session;
import com.sportflow.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
@WebServlet(name = "EntrainerDashboardServlet", urlPatterns = {"/entrainer/dashboard"})
public class EntrainerDashboardServlet extends HttpServlet {
    private SessionDAO sessionDAO;
    private BookingDAO bookingDAO;
    private EntrainerDAO entrainerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
        entrainerDAO = new EntrainerDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();

        try {

            Entrainer entrainer = entrainerDAO.getEntrainerByUserId(userId);

            List<Session> sessions = sessionDAO.getSessionsByEntrainerId(entrainer.getId());
            request.setAttribute("sessions", sessions);

            int totalMembers = sessions.stream()
                    .mapToInt(s -> {
                        try {
                            return bookingDAO.getBookingsBySessionId(s.getId()).size();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sum();

            request.setAttribute("totalMembers", totalMembers);


        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }


        request.getRequestDispatcher("/WEB-INF/jsp/entrainer/dashboard.jsp").forward(request, response);
    }
}
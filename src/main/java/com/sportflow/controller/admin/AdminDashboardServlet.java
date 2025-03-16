package com.sportflow.controller.admin;

import com.sportflow.dao.BookingDAO;
import com.sportflow.dao.MemberDAO;
import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Booking;
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

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private MemberDAO memberDAO;
    private SessionDAO sessionDAO;
    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        memberDAO = new MemberDAO();
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Member> members = memberDAO.getAllMembers();
            request.setAttribute("totalMembers", members.size());

            List<Session> sessions = sessionDAO.getAllSessions();
            request.setAttribute("totalSessions", sessions.size());

            int totalBookings = 0;
            for (Session session : sessions) {
                List<Booking> bookings = bookingDAO.getBookingsBySessionId(session.getId());
                totalBookings += bookings.size();
            }
            request.setAttribute("totalBookings", totalBookings);


        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
    }
}
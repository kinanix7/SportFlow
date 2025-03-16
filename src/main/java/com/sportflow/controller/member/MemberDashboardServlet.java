package com.sportflow.controller.member;

import com.sportflow.dao.BookingDAO;
import com.sportflow.dao.MemberDAO; // Import MemberDAO
import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Booking;
import com.sportflow.model.Member; // Import Member
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MemberDashboardServlet", urlPatterns = {"/member/dashboard"})
public class MemberDashboardServlet extends HttpServlet {

    private SessionDAO sessionDAO;
    private BookingDAO bookingDAO;
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
        memberDAO = new MemberDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            Member member = memberDAO.getMemberByUserId(user.getId());
            if (member == null) {
                request.setAttribute("errorMessage", "No member profile found for this user.");
                request.getRequestDispatcher("/WEB-INF/jsp/member/dashboard.jsp").forward(request, response);
                return;
            }

            List<Booking> bookings = bookingDAO.getBookingsByMemberId(member.getId()); // Use member.getId()
            List<Session> bookedSessions = new ArrayList<>();

            for (Booking booking : bookings) {
                Session sessionData = sessionDAO.getSessionById(booking.getSessionId());
                if (sessionData != null) {
                    bookedSessions.add(sessionData);
                }
            }

            request.setAttribute("bookedSessions", bookedSessions);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/member/dashboard.jsp").forward(request, response);
    }
}
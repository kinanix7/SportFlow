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
    private MemberDAO memberDAO; // Add MemberDAO instance

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
        memberDAO = new MemberDAO(); // Initialize MemberDAO
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        //Integer userId = user.getId(); // Get the user's ID -- Correct, but we'll use memberId

        try {
            // Get the Member object associated with the logged-in user
            Member member = memberDAO.getMemberByUserId(user.getId());
            if (member == null) {
                // Handle the case where the user isn't associated with a member (shouldn't happen normally)
                request.setAttribute("errorMessage", "No member profile found for this user.");
                request.getRequestDispatcher("/WEB-INF/jsp/member/dashboard.jsp").forward(request, response);
                return; // Important: Stop processing if no member is found
            }

            // Get bookings for the member (using the member's ID, NOT the user's ID)
            List<Booking> bookings = bookingDAO.getBookingsByMemberId(member.getId()); // Use member.getId()
            List<Session> bookedSessions = new ArrayList<>();

            // Fetch session details for each booking
            for (Booking booking : bookings) {
                Session sessionData = sessionDAO.getSessionById(booking.getSessionId());
                if (sessionData != null) {
                    bookedSessions.add(sessionData);
                }
            }

            request.setAttribute("bookedSessions", bookedSessions);

        } catch (SQLException e) {
            // Handle database errors appropriately (log them, display a user-friendly message)
            throw new ServletException("Database error", e); // Or handle more gracefully
        }
        request.getRequestDispatcher("/WEB-INF/jsp/member/dashboard.jsp").forward(request, response);
    }
}
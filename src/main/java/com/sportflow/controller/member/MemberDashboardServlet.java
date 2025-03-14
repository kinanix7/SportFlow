// MemberDashboardServlet.java
package com.sportflow.controller.member;

import com.sportflow.dao.BookingDAO;
import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Booking;
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
import java.util.stream.Collectors;

@WebServlet("/member/dashboard")
public class MemberDashboardServlet extends HttpServlet {

    private BookingDAO bookingDAO;
    private SessionDAO sessionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingDAO = new BookingDAO();
        sessionDAO = new SessionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Fetch bookings for the logged-in member
        List<Booking> bookings = bookingDAO.getBookingsByMemberId(user.getId());

        // Fetch session details for each booking
        List<Session> bookedSessions = bookings.stream()
                .map(booking -> sessionDAO.getSessionById(booking.getSessionId()))
                .collect(Collectors.toList());


        request.setAttribute("bookedSessions", bookedSessions);
        request.getRequestDispatcher("/WEB-INF/jsp/member/dashboard.jsp").forward(request, response);
    }
}
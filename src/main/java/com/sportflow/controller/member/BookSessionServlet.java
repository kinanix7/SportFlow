// BookSessionServlet.java
package com.sportflow.controller.member;

import com.sportflow.dao.SessionDAO;
import com.sportflow.dao.BookingDAO;
import com.sportflow.model.Session;
import com.sportflow.model.Booking;
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

@WebServlet("/member/book-session")
public class BookSessionServlet extends HttpServlet {

    private SessionDAO sessionDAO;
    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listAvailableSessions(request, response);
                break;
            case "book":
                bookSession(request, response);
                break;
            default:
                listAvailableSessions(request, response);
        }
    }


    private void listAvailableSessions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        List<Session> allSessions = sessionDAO.getAllSessions();

        // Get the IDs of sessions already booked by the member
        List<Integer> bookedSessionIds = bookingDAO.getBookingsByMemberId(user.getId()).stream()
                .map(Booking::getSessionId)
                .collect(Collectors.toList());

        // Filter out the sessions that the member has already booked
        List<Session> availableSessions = allSessions.stream()
                .filter(s -> !bookedSessionIds.contains(s.getId()))
                .collect(Collectors.toList());

        request.setAttribute("sessions", availableSessions);
        request.getRequestDispatcher("/WEB-INF/jsp/member/book-session.jsp").forward(request, response);
    }



    private void bookSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");


        Booking booking = new Booking();
        booking.setMemberId(user.getId());
        booking.setSessionId(sessionId);
        booking.setStatus("CONFIRMED"); // Or "PENDING", depending on your requirements
        bookingDAO.createBooking(booking);

        response.sendRedirect(request.getContextPath() + "/member/dashboard");
    }

}
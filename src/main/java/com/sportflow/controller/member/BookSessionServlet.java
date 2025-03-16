package com.sportflow.controller.member;

import com.sportflow.dao.BookingDAO;
import com.sportflow.dao.EntrainerDAO;
import com.sportflow.dao.MemberDAO;
import com.sportflow.dao.SessionDAO;
import com.sportflow.model.Booking;
import com.sportflow.model.Entrainer;
import com.sportflow.model.Member;
import com.sportflow.model.Session;
import com.sportflow.model.User;
import com.sportflow.util.DateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "BookSessionServlet", urlPatterns = {"/member/book-session", "/member/book-session/create", "/member/cancel-booking","/member/view-sessions"}) // Add URL pattern
public class BookSessionServlet extends HttpServlet {

    private SessionDAO sessionDAO;
    private BookingDAO bookingDAO;
    private EntrainerDAO entrainerDAO;
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionDAO = new SessionDAO();
        bookingDAO = new BookingDAO();
        entrainerDAO = new EntrainerDAO();
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();

        String action = request.getServletPath();

        try {
            if (action.equals("/member/book-session") || action.equals("/member/view-sessions")) {
                List<Session> allSessions = sessionDAO.getAllSessions();
                List<Session> availableSessions = new ArrayList<>();

                for (Session s : allSessions) {
                    List<Booking> bookingsForSession = bookingDAO.getBookingsBySessionId(s.getId());
                    if (bookingsForSession.size() < s.getMaxParticipants()) {
                        availableSessions.add(s);
                    }
                }
                request.setAttribute("sessions", availableSessions);

                List<Entrainer> entrainers = entrainerDAO.getAllEntrainers();
                request.setAttribute("entrainers", entrainers);
                request.getRequestDispatcher("/WEB-INF/jsp/member/view-sessions.jsp").forward(request, response); // Forward to view-sessions.jsp

            } else if (action.equals("/member/cancel-booking")) {
                int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                bookingDAO.deleteBooking(bookingId);
                response.sendRedirect(request.getContextPath() + "/member/dashboard");

            }


        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            Member member = memberDAO.getMemberByUserId(user.getId());
            if (member == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No member found for this user.");
                return;
            }
            int memberId = member.getId();
            int sessionId = Integer.parseInt(request.getParameter("sessionId"));

            if (bookingDAO.bookingExists(memberId, sessionId)) {
                request.setAttribute("errorMessage", "You have already booked this session.");
                doGet(request, response);
            } else {
                Booking booking = new Booking();
                booking.setMemberId(memberId);
                booking.setSessionId(sessionId);
                bookingDAO.createBooking(booking);
                response.sendRedirect(request.getContextPath() + "/member/dashboard");
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "A database error occurred. Please try again later.");
            doGet(request, response);
        }
    }

}
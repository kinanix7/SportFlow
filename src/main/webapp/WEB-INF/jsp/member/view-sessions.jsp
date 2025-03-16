<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@ page import="com.sportflow.model.Entrainer" %>
<%@ page import="com.sportflow.util.DateUtil" %>
<%@ page import="com.sportflow.dao.BookingDAO" %>
<%@ page import="com.sportflow.model.Booking" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
      integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
    /* Base styling */
    body {
        background-color: #f8fafc;
        font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
        color: #334155;
        line-height: 1.6;
    }

    /* Dashboard Container */
    .dashboard-container {
        padding: 30px 15px;
        max-width: 1400px;
        margin: 0 auto;
    }

    /* Dashboard Header */
    .dashboard-header {
        margin-bottom: 40px;
        padding-bottom: 20px;
        border-bottom: 2px solid rgba(0, 0, 0, 0.05);
        position: relative;
    }

    .dashboard-title {
        color: #0f172a;
        font-weight: 700;
        font-size: 2rem;
        position: relative;
        display: inline-block;
        margin-bottom: 10px;
    }

    .dashboard-title::after {
        content: '';
        position: absolute;
        bottom: -5px;
        left: 0;
        width: 60px;
        height: 4px;
        background: linear-gradient(to right, #3B82F6, #10B981);
        border-radius: 2px;
    }

    /* Enhanced table styling */
    .table-container {
        background-color: #ffffff;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        overflow: hidden;
        margin-bottom: 30px;
    }

    .table {
        margin-bottom: 0;
        width: 100%;
        background-color: transparent;
    }

    .table thead th {
        background-color: #f1f5f9;
        color: #475569;
        font-weight: 600;
        padding: 14px 16px;
        border-bottom: 2px solid #e2e8f0;
        font-size: 0.9rem;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }

    .table tbody td {
        padding: 12px 16px;
        border-bottom: 1px solid #f1f5f9;
        vertical-align: middle;
        font-size: 0.95rem;
    }

    .table tbody tr:last-child td {
        border-bottom: none;
    }

    .table tbody tr:hover {
        background-color: #f8fafc;
    }

    /* Button styling */
    .btn {
        border-radius: 8px;
        padding: 9px 18px;
        font-weight: 500;
        transition: all 0.3s ease;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.08);
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
    }

    .btn-primary {
        background-color: #3B82F6;
        border-color: #3B82F6;
    }

    .btn-primary:hover, .btn-primary:focus {
        background-color: #2563EB;
        border-color: #2563EB;
        box-shadow: 0 4px 10px rgba(59, 130, 246, 0.4);
    }

    /* Responsive styles */
    @media (max-width: 992px) {
        .dashboard-title {
            font-size: 1.8rem;
        }

        .table-responsive {
            border-radius: 12px;
            overflow: hidden;
        }
    }

    @media (max-width: 768px) {
        .dashboard-container {
            padding: 20px 10px;
        }

        .dashboard-header {
            margin-bottom: 25px;
        }
    }

    /* Action buttons in table */
    .action-buttons {
        display: flex;
        gap: 8px;
    }

    /* Error and success alerts */
    .alert {
        border-radius: 8px;
        border: none;
        padding: 15px 20px;
        margin-bottom: 25px;
        position: relative;
    }

    .alert-success {
        background-color: rgba(16, 185, 129, 0.15);
        color: #059669;
    }

    .alert-danger {
        background-color: rgba(239, 68, 68, 0.15);
        color: #DC2626;
    }

    .alert-info {
        background-color: rgba(59, 130, 246, 0.15);
        color: #2563EB;
    }
</style>

<div class="container dashboard-container">
    <div class="bg-pattern"></div>
    <div class="dashboard-header">
        <h1 class="dashboard-title">Available Sessions</h1>
    </div>

    <!-- Display error message if it exists -->
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger" role="alert">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <div class="table-container">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Entrainer</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Available Slots</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Session> sessions = (List<Session>) request.getAttribute("sessions");
                    List<Entrainer> entrainers = (List<Entrainer>) request.getAttribute("entrainers");
                    BookingDAO bookingDAO = new BookingDAO();

                    if (sessions != null && entrainers != null) {
                        for (Session availableSession : sessions) {
                            Entrainer entrainer = entrainers.stream()
                                    .filter(e -> e.getId().equals(availableSession.getEntrainerId()))
                                    .findFirst()
                                    .orElse(null);

                            // Calculate available slots
                            int availableSlots = 0;
                            try {
                                List<Booking> bookings = bookingDAO.getBookingsBySessionId(availableSession.getId());
                                availableSlots = availableSession.getMaxParticipants() - bookings.size();
                            } catch (SQLException e) {
                                availableSlots = -1; // Indicate an error
                                request.setAttribute("errorMessage", "Error fetching booking information.");
                            }
                %>
                <tr>
                    <td><%= availableSession.getTitle() %></td>
                    <td><%= availableSession.getDescription() %></td>
                    <td>
                        <% if (entrainer != null) { %>
                        <%= entrainer.getFirstName() %> <%= entrainer.getLastName() %>
                        <% } else { %>
                        Entrainer Not Found
                        <% } %>
                    </td>
                    <td><%= DateUtil.formatDate(availableSession.getSessionDate()) %></td>
                    <td><%= DateUtil.formatTime(availableSession.getStartTime()) %> - <%= DateUtil.formatTime(availableSession.getEndTime()) %></td>
                    <td><%= availableSlots %></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/member/book-session/create" method="post">
                            <input type="hidden" name="sessionId" value="<%= availableSession.getId() %>">
                            <button type="submit" class="btn btn-primary">Book</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No sessions available.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

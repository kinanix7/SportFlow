<!-- member/dashboard.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Member Dashboard</h1>
    <h2>Booked Sessions</h2>
    <%
        List<Session> bookedSessions = (List<Session>) request.getAttribute("bookedSessions");
        if (bookedSessions != null && !bookedSessions.isEmpty()) { %>
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
        </thead>
        <tbody>
        <% for (Session session : bookedSessions) { %>
        <tr>
            <td><%= session.getTitle() %></td>
            <td><%= session.getDescription() %></td>
            <td><%= session.getSessionDate() %></td>
            <td><%= session.getStartTime() %></td>
            <td><%= session.getEndTime() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p>You have not booked any sessions yet.</p>
    <% } %>

</div>

<%@include file="../common/footer.jsp" %>
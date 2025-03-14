<!-- member/book-session.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Available Sessions</h1>
    <%
        List<Session> sessions = (List<Session>) request.getAttribute("sessions");
        if (sessions != null && !sessions.isEmpty()) { %>
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Max Participants</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>

        <% for (Session session : sessions) { %>
        <tr>
            <td><%= session.getTitle() %></td>
            <td><%= session.getDescription() %></td>
            <td><%= session.getSessionDate() %></td>
            <td><%= session.getStartTime() %></td>
            <td><%= session.getEndTime() %></td>
            <td><%= session.getMaxParticipants() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/member/book-session?action=book&sessionId=<%= session.getId() %>" class="btn btn-primary">Book</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p>No sessions available to book at this time.</p>
    <% } %>
</div>

<%@include file="../common/footer.jsp" %>
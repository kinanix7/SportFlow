<!-- entrainer/dashboard.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Entrainer Dashboard</h1>
    <h2>My Sessions</h2>
    <%
        List<Session> sessions = (List<Session>) request.getAttribute("sessions");
        if (sessions != null && !sessions.isEmpty()) {
    %>
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Date</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Session entrainerSession : sessions) {  // Changed variable name
        %>
        <tr>
            <td><%= entrainerSession.getTitle() %></td>
            <td><%= entrainerSession.getDescription() %></td>
            <td><%= entrainerSession.getSessionDate() %></td>
            <td><%= entrainerSession.getStartTime() %> - <%= entrainerSession.getEndTime() %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <% } else { %>
    <p>You have no sessions scheduled.</p>
    <% } %>
</div>

<%@include file="../common/footer.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@ page import="com.sportflow.model.Entrainer" %>
<%@ page import="com.sportflow.util.DateUtil" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Book Session</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger" role="alert">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>
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
        <% List<Session> sessions = (List<Session>) request.getAttribute("sessions");
            List<Entrainer> entrainers = (List<Entrainer>) request.getAttribute("entrainers");

            if (sessions != null && entrainers != null) {
                for (Session availableSession : sessions) { // RENAMED VARIABLE HERE
                    // Find the entrainer for this session
                    Entrainer entrainer = entrainers.stream()
                            .filter(e -> e.getId().equals(availableSession.getEntrainerId())) // Use availableSession here
                            .findFirst()
                            .orElse(null); // Handle the case where the entrainer is not found
        %>

        <tr>
            <td><%= availableSession.getTitle() %></td> <%-- Use availableSession --%>
            <td><%= availableSession.getDescription() %></td> <%-- Use availableSession --%>
            <td>
                <% if (entrainer != null) { %>
                <%= entrainer.getFirstName() %> <%= entrainer.getLastName() %>
                <% } else { %>
                Entrainer Not Found
                <% } %>
            </td>

            <td><%= DateUtil.formatDate(availableSession.getSessionDate())%></td> <%-- Use availableSession --%>
            <td><%= DateUtil.formatTime(availableSession.getStartTime()) %> - <%= DateUtil.formatTime(availableSession.getEndTime()) %></td> <%-- Use availableSession --%>
            <td><%= availableSession.getMaxParticipants() %></td> <%-- Use availableSession --%>
            <td>
                <form action="<%= request.getContextPath() %>/member/book-session/create" method="post">
                    <input type="hidden" name="sessionId" value="<%= availableSession.getId() %>"> <%-- Use availableSession --%>
                    <button type="submit" class="btn btn-primary">Book</button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="7">No sessions available.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<%@ include file="../common/footer.jsp" %>
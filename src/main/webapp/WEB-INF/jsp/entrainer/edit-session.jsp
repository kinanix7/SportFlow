<!-- entrainer/edit-session.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sportflow.model.Session" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Edit Session</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <% Session session = (Session) request.getAttribute("session");
        if (session != null) { %>
    <form action="${pageContext.request.contextPath}/entrainer/sessions?action=update" method="post">
        <input type="hidden" name="id" value="<%= session.getId() %>">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title"  name="title" value="<%= session.getTitle()%>" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description"  class="form-control"><%= session.getDescription() %></textarea>
        </div>

        <div class="form-group">
            <label for="sessionDate">Session Date:</label>
            <input type="date" id="sessionDate" name="sessionDate" value="<%= session.getSessionDate()%>" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="startTime">Start Time:</label>
            <input type="time" id="startTime" name="startTime" value="<%= session.getStartTime() %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="endTime">End Time:</label>
            <input type="time" id="endTime" name="endTime" value="<%= session.getEndTime() %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="maxParticipants">Max Participants:</label>
            <input type="number" id="maxParticipants" name="maxParticipants" value="<%= session.getMaxParticipants() %>" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Update Session</button>
    </form>
    <% } else { %>
    <p>Session not found.</p>
    <% } %>
</div>
<%@include file="../common/footer.jsp" %>
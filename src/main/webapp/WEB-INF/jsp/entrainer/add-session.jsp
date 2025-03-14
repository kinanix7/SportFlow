<!-- entrainer/add-session.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Add Session</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/entrainer/sessions?action=add" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea class="form-control" id="description" name="description"></textarea>
        </div>
        <div class="form-group">
            <label for="sessionDate">Date:</label>
            <input type="date" class="form-control" id="sessionDate" name="sessionDate" required>
        </div>
        <div class="form-group">
            <label for="startTime">Start Time:</label>
            <input type="time" class="form-control" id="startTime" name="startTime" required>
        </div>
        <div class="form-group">
            <label for="endTime">End Time:</label>
            <input type="time" class="form-control" id="endTime" name="endTime" required>
        </div>
        <div class="form-group">
            <label for="maxParticipants">Max Participants:</label>
            <input type="number" class="form-control" id="maxParticipants" name="maxParticipants" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Session</button>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
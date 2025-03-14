<!-- entrainer/sessions.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Session" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h1>Sessions</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <a href="${pageContext.request.contextPath}/entrainer/sessions?action=add"
       class="btn btn-success mb-2">Add Session</a>
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
            <th>Start Time</th>
            <th>End Time</th>
            <th>Max Participants</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>

        <% for (Session entrainerSession : sessions) { %>
        <tr>
            <td><%= entrainerSession.getTitle() %>
            </td>
            <td><%= entrainerSession.getDescription() %>
            </td>
            <td><%= entrainerSession.getSessionDate() %>
            </td>
            <td><%= entrainerSession.getStartTime() %>
            </td>
            <td><%= entrainerSession.getEndTime() %>
            </td>
            <td><%= entrainerSession.getMaxParticipants() %>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/entrainer/sessions?action=edit&id=<%= entrainerSession.getId() %>"
                   class="btn btn-sm btn-primary">Edit</a>
                <form action="${pageContext.request.contextPath}/entrainer/sessions?action=delete" method="post"
                      style="display:inline;">
                    <input type="hidden" name="id" value="<%= entrainerSession.getId() %>">
                    <button type="submit" class="btn btn-sm btn-danger"
                            onclick="return confirm('Are you sure you want to delete this session?')">Delete</button>
                </form>

            </td>
        </tr>
        <% } %>

        </tbody>
    </table>
    <% } else { %>
    <p>You have no sessions scheduled.</p>
    <% } %>
</div>
<%@include file="../common/footer.jsp" %>
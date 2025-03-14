<!-- common/navbar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sportflow.model.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
    String role = (currentUser != null) ? currentUser.getRole() : null;
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">SportFlow</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <% if (currentUser == null) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
            </li>
            <% } else { %>
            <% if ("ADMIN".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/members">Members</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/entrainers">Entrainers</a>
            </li>
            <% } else if ("ENTRAINER".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/entrainer/dashboard">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/entrainer/sessions">Sessions</a>
            </li>
            <% } else if ("MEMBER".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/member/dashboard">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/member/book-session">Book Session</a>
            </li>
            <% } %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
            <% } %>
        </ul>
    </div>
</nav>
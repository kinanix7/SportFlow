<!-- admin/add-member.jsp (Modal - Integrated into members.jsp) -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Member" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h2>Add Member</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger" role="alert">
        <%= request.getAttribute("error")%>
    </div>
    <% } %>
    <form action="${pageContext.request.contextPath}/admin/members?action=add" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control" id="lastName" name="lastName" required>
        </div>
        <div class="form-group">
            <label for="birthDate">Birth Date:</label>
            <input type="date" class="form-control" id="birthDate" name="birthDate" required>
        </div>
        <div class="form-group">
            <label for="sport">Sport:</label>
            <input type="text" class="form-control" id="sport" name="sport" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Member</button>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
<!--admin/edit-member.jsp-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sportflow.model.Member" %>

<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h2>Edit Member</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <% Member member = (Member) request.getAttribute("member");
        if (member != null) { %>
    <form action="${pageContext.request.contextPath}/admin/members?action=update" method="post">
        <input type="hidden" name="id" value="<%= member.getId() %>">
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="<%= member.getFirstName() %>" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="<%= member.getLastName() %>" required>
        </div>
        <div class="form-group">
            <label for="birthDate">Birth Date:</label>
            <input type="date" class="form-control" id="birthDate" name="birthDate" value="<%= member.getBirthDate() %>" required>
        </div>
        <div class="form-group">
            <label for="sport">Sport:</label>
            <input type="text" class="form-control" id="sport" name="sport" value="<%= member.getSport() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Update Member</button>
    </form>
    <% } else { %>
    <p>Member not found.</p>
    <% } %>
</div>

<%@include file="../common/footer.jsp" %>
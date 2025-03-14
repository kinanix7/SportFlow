<!-- admin/edit-entrainer.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sportflow.model.Entrainer" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h2>Edit Entrainer</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <% Entrainer entrainer = (Entrainer) request.getAttribute("entrainer");
        if (entrainer != null) { %>
    <form action="${pageContext.request.contextPath}/admin/entrainers?action=update" method="post">
        <input type="hidden" name="id" value="<%= entrainer.getId() %>">
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="<%= entrainer.getFirstName() %>" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="<%= entrainer.getLastName() %>" required>
        </div>
        <div class="form-group">
            <label for="specialty">Specialty:</label>
            <input type="text" class="form-control" id="specialty" name="specialty" value="<%= entrainer.getSpecialty() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Update Entrainer</button>
    </form>
    <% } else { %>
    <p>Entrainer not found.</p>
    <% } %>
</div>

<%@include file="../common/footer.jsp" %>
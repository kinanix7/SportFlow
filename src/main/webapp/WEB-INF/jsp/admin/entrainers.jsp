<!-- admin/entrainers.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Entrainer" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Entrainers</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <a href="${pageContext.request.contextPath}/admin/entrainers?action=add" class="btn btn-success mb-3">Add Entrainer</a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Specialty</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% List<Entrainer> entrainers = (List<Entrainer>) request.getAttribute("entrainers");
            if(entrainers != null){
                for (Entrainer entrainer : entrainers) { %>
        <tr>
            <td><%= entrainer.getId() %></td>
            <td><%= entrainer.getFirstName() %></td>
            <td><%= entrainer.getLastName() %></td>
            <td><%= entrainer.getSpecialty() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/entrainers?action=edit&id=<%= entrainer.getId() %>" class="btn btn-sm btn-primary">Edit</a>
                <form action="${pageContext.request.contextPath}/admin/entrainers?action=delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= entrainer.getId() %>">
                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this entrainer?')">Delete</button>
                </form>
            </td>
        </tr>
        <% }
        }else{ %>
        <tr><td colspan="5">No entrainers found.</td></tr>
        <%}
        %>
        </tbody>
    </table>
</div>
<%@include file="../common/footer.jsp" %>
<!-- admin/members.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Member" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h1>Members</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <a href="${pageContext.request.contextPath}/admin/members?action=add" class="btn btn-success mb-3">Add Member</a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Birth Date</th>
            <th>Sport</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% List<Member> members = (List<Member>) request.getAttribute("members");
            if (members != null) {
                for (Member member : members) { %>
        <tr>
            <td><%= member.getId() %></td>
            <td><%= member.getFirstName() %></td>
            <td><%= member.getLastName() %></td>
            <td><%= member.getBirthDate() %></td>
            <td><%= member.getSport() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/members?action=edit&id=<%= member.getId() %>" class="btn btn-sm btn-primary">Edit</a>
                <!-- Delete Button (using a form for proper HTTP method) -->
                <form action="${pageContext.request.contextPath}/admin/members?action=delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= member.getId() %>">
                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr><td colspan="6">No members found.</td></tr>
        <%}
        %>
        </tbody>
    </table>
</div>
<%@include file="../common/footer.jsp" %>
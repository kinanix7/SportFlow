<!-- index.jsp (Redirect to login) -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.sendRedirect(request.getContextPath() + "/login"); %>
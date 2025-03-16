<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<style>
  /* Base styling */
  body {
    background-color: #f8fafc;
    font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
    color: #334155;
    line-height: 1.6;
  }

  /* Dashboard Container */
  .dashboard-container {
    padding: 30px 15px;
    max-width: 1400px;
    margin: 0 auto;
  }

  /* Dashboard Header */
  .dashboard-header {
    margin-bottom: 40px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(0,0,0,0.05);
    position: relative;
  }

  .dashboard-title {
    color: #0f172a;
    font-weight: 700;
    font-size: 2rem;
    position: relative;
    display: inline-block;
    margin-bottom: 10px;
  }

  .dashboard-title::after {
    content: '';
    position: absolute;
    bottom: -5px;
    left: 0;
    width: 60px;
    height: 4px;
    background: linear-gradient(to right, #3B82F6, #10B981);
    border-radius: 2px;
  }

  /* Form styling */
  .form-group {
    margin-bottom: 18px;
  }

  .form-group label {
    font-weight: 500;
    color: #475569;
    margin-bottom: 6px;
    display: block;
    font-size: 0.9rem;
  }

  .form-control {
    border-radius: 8px;
    border: 1px solid #e2e8f0;
    padding: 10px 16px;
    height: auto;
    font-size: 0.95rem;
    transition: all 0.25s ease;
  }

  .form-control:focus {
    border-color: #3B82F6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.25);
  }

  /* Button styling */
  .btn {
    border-radius: 8px;
    padding: 9px 18px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 2px 5px rgba(0,0,0,0.08);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
  }

  .btn-primary {
    background-color: #3B82F6;
    border-color: #3B82F6;
  }

  .btn-primary:hover, .btn-primary:focus {
    background-color: #2563EB;
    border-color: #2563EB;
    box-shadow: 0 4px 10px rgba(59, 130, 246, 0.4);
  }

  .btn-secondary {
    background-color: #94A3B8;
    border-color: #94A3B8;
  }

  .btn-secondary:hover, .btn-secondary:focus {
    background-color: #64748B;
    border-color: #64748B;
    box-shadow: 0 4px 10px rgba(148, 163, 184, 0.4);
  }

  /* Alert styling */
  .alert {
    border-radius: 8px;
    border: none;
    padding: 15px 20px;
    margin-bottom: 25px;
    position: relative;
  }

  .alert-danger {
    background-color: rgba(239, 68, 68, 0.15);
    color: #DC2626;
  }

  /* Responsive styles */
  @media (max-width: 992px) {
    .dashboard-title {
      font-size: 1.8rem;
    }
  }

  @media (max-width: 768px) {
    .dashboard-container {
      padding: 20px 10px;
    }

    .dashboard-header {
      margin-bottom: 25px;
    }
  }
</style>

<div class="container dashboard-container">
  <div class="dashboard-header">
    <h1 class="dashboard-title">Add New Admin</h1>
  </div>

  <%-- Display error message if it exists --%>
  <% if (request.getAttribute("errorMessage") != null) { %>
  <div class="alert alert-danger" role="alert">
    <%= request.getAttribute("errorMessage") %>
  </div>
  <% } %>

  <form action="<%= request.getContextPath() %>/admin/add-admin" method="post">
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
    <button type="submit" class="btn btn-primary">Add Admin</button>
  </form>
  <a href="<%= request.getContextPath() %>/admin/dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
</div>

<%@ include file="../common/footer.jsp" %>

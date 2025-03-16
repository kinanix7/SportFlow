<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Entrainer" %>
<%@ page import="com.sportflow.model.User" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
      integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> <%-- Changed from slim to full version --%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
        border-bottom: 2px solid rgba(0, 0, 0, 0.05);
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

    /* Card Styling */
    .dashboard-card {
        transition: all 0.3s ease;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        overflow: hidden;
        margin-bottom: 25px;
        border: none;
        height: 100%;
    }

    .dashboard-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
    }

    .stat-card {
        text-align: center;
        padding: 30px 20px;
        height: 100%;
        position: relative;
        overflow: hidden;
        z-index: 1;
        color: #ffffff;
    }

    /* Card color variants */
    .members-card {
        background: linear-gradient(135deg, #6366F1, #8B5CF6);
    }

    .sessions-card {
        background: linear-gradient(135deg, #10B981, #3B82F6);
    }

    .bookings-card {
        background: linear-gradient(135deg, #F59E0B, #EF4444);
    }

    /* Card content styling */
    .card-icon {
        font-size: 2.5rem;
        margin-bottom: 20px;
        animation: float 3s ease-in-out infinite;
    }

    .card-title {
        font-weight: 600;
        margin-bottom: 10px;
        color: rgba(255, 255, 255, 0.9);
        font-size: 1.1rem;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }

    .card-text {
        font-size: 2.2rem;
        font-weight: 700;
        color: #ffffff;
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    /* Enhanced table styling */
    .table-container {
        background-color: #ffffff;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        overflow: hidden;
        margin-bottom: 30px;
    }

    .table {
        margin-bottom: 0;
        width: 100%;
        background-color: transparent;
    }

    .table thead th {
        background-color: #f1f5f9;
        color: #475569;
        font-weight: 600;
        padding: 14px 16px;
        border-bottom: 2px solid #e2e8f0;
        font-size: 0.9rem;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }

    .table tbody td {
        padding: 12px 16px;
        border-bottom: 1px solid #f1f5f9;
        vertical-align: middle;
        font-size: 0.95rem;
    }

    .table tbody tr:last-child td {
        border-bottom: none;
    }

    .table tbody tr:hover {
        background-color: #f8fafc;
    }

    /* Button styling */
    .btn {
        border-radius: 8px;
        padding: 9px 18px;
        font-weight: 500;
        transition: all 0.3s ease;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.08);
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
    }

    .btn-sm {
        padding: 5px 12px;
        font-size: 0.85rem;
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

    .btn-danger {
        background-color: #EF4444;
        border-color: #EF4444;
    }

    .btn-danger:hover, .btn-danger:focus {
        background-color: #DC2626;
        border-color: #DC2626;
        box-shadow: 0 4px 10px rgba(239, 68, 68, 0.4);
    }

    .btn-success {
        background-color: #10B981;
        border-color: #10B981;
    }

    .btn-success:hover, .btn-success:focus {
        background-color: #059669;
        border-color: #059669;
        box-shadow: 0 4px 10px rgba(16, 185, 129, 0.4);
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

    /* Add member button styling */
    .add-member-btn {
        padding: 10px 20px;
        font-weight: 600;
        margin-bottom: 25px;
        display: inline-flex;
        align-items: center;
        gap: 8px;
    }

    .add-member-btn i {
        font-size: 1.1rem;
    }

    /* Modal styling */
    .sf-modal { /* Namespace modal styles */
        z-index: 1050; /* Ensure it's above other elements */
        overflow: auto; /* Enable scroll if needed for the modal container */
    }

    .sf-modal-dialog {
        /* Adjust modal dialog for better screen fitting */
        max-width: 80%; /* Limit width to 80% of viewport */
        margin: 1.75rem auto; /* Center the modal and give it some top margin */
    }

    .sf-modal-content { /* Namespace modal content styles */
        border-radius: 12px;
        border: none;
        box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
        overflow: hidden;
    }

    .sf-modal-header { /* Namespace modal header styles */
        border-bottom: 1px solid #f1f5f9;
        padding: 18px 24px;
        background-color: #f8fafc;
    }

    .sf-modal-title { /* Namespace modal title styles */
        font-weight: 600;
        color: #0f172a;
        font-size: 1.2rem;
    }

    .sf-modal-body { /* Namespace modal body styles */
        padding: 24px;
        overflow-y: auto; /* Add a vertical scrollbar if needed */
        max-height: 60vh; /* Limit the maximum height to 60% of viewport height */
    }

    .sf-modal-footer { /* Namespace modal footer styles */
        border-top: 1px solid #f1f5f9;
        padding: 16px 24px;
        background-color: #f8fafc;
    }

    .close {
        opacity: 0.6;
        transition: all 0.2s ease;
    }

    .close:hover {
        opacity: 1;
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

    /* Animations */
    @keyframes float {
        0% {
            transform: translateY(0px);
        }
        50% {
            transform: translateY(-8px);
        }
        100% {
            transform: translateY(0px);
        }
    }

    .fade-in {
        animation: fadeIn 0.8s ease-in-out forwards;
        opacity: 0;
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .fade-in-1 {
        animation-delay: 0.1s;
    }

    .fade-in-2 {
        animation-delay: 0.2s;
    }

    .fade-in-3 {
        animation-delay: 0.3s;
    }

    /* Background pattern */
    .bg-pattern {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%), linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%);
        background-size: 60px 60px;
        background-position: 0 0, 30px 30px;
        opacity: 0.2;
        z-index: -1;
    }

    /* Status indicators */
    .status-badge {
        display: inline-block;
        padding: 4px 10px;
        border-radius: 20px;
        font-size: 0.8rem;
        font-weight: 500;
    }

    .status-active {
        background-color: rgba(16, 185, 129, 0.15);
        color: #059669;
    }

    .status-inactive {
        background-color: rgba(239, 68, 68, 0.15);
        color: #DC2626;
    }

    /* Responsive styles */
    @media (max-width: 992px) {
        .dashboard-title {
            font-size: 1.8rem;
        }

        .table-responsive {
            border-radius: 12px;
            overflow: hidden;
        }
    }

    @media (max-width: 768px) {
        .dashboard-container {
            padding: 20px 10px;
        }

        .dashboard-header {
            margin-bottom: 25px;
        }

        .card-text {
            font-size: 1.8rem;
        }
    }

    /* Action buttons in table */
    .action-buttons {
        display: flex;
        gap: 8px;
    }

    /* Error and success alerts */
    .alert {
        border-radius: 8px;
        border: none;
        padding: 15px 20px;
        margin-bottom: 25px;
        position: relative;
    }

    .alert-success {
        background-color: rgba(16, 185, 129, 0.15);
        color: #059669;
    }

    .alert-danger {
        background-color: rgba(239, 68, 68, 0.15);
        color: #DC2626;
    }

    .alert-info {
        background-color: rgba(59, 130, 246, 0.15);
        color: #2563EB;
    }

    /* Stats cards section */
    .stats-summary {
        margin-bottom: 30px;
    }

    /* Explicit backdrop styling */
    .modal-backdrop { /* Target the Bootstrap backdrop */
        z-index: 1040; /* Ensure backdrop is below the modal (default Bootstrap value) */
        background-color: rgba(0, 0, 0, 0.5); /* Adjust backdrop color/opacity as needed */
    }

    .sf-modal { /* Namespace the modal */
        z-index: 1050; /* Ensure the modal is above the backdrop */
    }

    body.modal-open { /* Target the body when a modal is open */
        padding-right: 0 !important; /* Prevent body from shifting due to scrollbar */
    }
</style>

<div class="container dashboard-container">
    <div class="bg-pattern"></div>
    <div class="dashboard-header">
        <h1 class="dashboard-title">Entrainer Management</h1>
        <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addEntrainerModal"
                style="margin-left: 750px">Add Entrainer
        </button>
    </div>

    <!-- Add Entrainer Modal -->
    <div class="modal fade" id="addEntrainerModal" tabindex="-1" role="dialog" aria-labelledby="addEntrainerModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content sf-modal-content"> <%--Namespaced the modal content class--%>
                <div class="modal-header sf-modal-header"> <%--Namespaced the modal header class--%>
                    <h5 class="modal-title sf-modal-title" id="addEntrainerModalLabel">Add New Entrainer</h5> <%--Namespaced the modal title class--%>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body sf-modal-body">  <%--Namespaced the modal body class--%>
                    <form action="<%=request.getContextPath()%>/admin/entrainers/add" method="post">
                        <!-- User Fields -->
                        <div class="form-group">
                            <label for="addUsername">Username:</label>
                            <input type="text" class="form-control" id="addUsername" name="username" required>
                        </div>
                        <div class="form-group">
                            <label for="addPassword">Password:</label>
                            <input type="password" class="form-control" id="addPassword" name="password" required>
                        </div>
                        <div class="form-group">
                            <label for="addEmail">Email:</label>
                            <input type="email" class="form-control" id="addEmail" name="email" required>
                        </div>

                        <!-- Entrainer Fields -->
                        <div class="form-group">
                            <label for="addFirstName">First Name:</label>
                            <input type="text" class="form-control" id="addFirstName" name="firstName" required>
                        </div>
                        <div class="form-group">
                            <label for="addLastName">Last Name:</label>
                            <input type="text" class="form-control" id="addLastName" name="lastName" required>
                        </div>
                        <div class="form-group">
                            <label for="addSpecialty">Specialty:</label>
                            <input type="text" class="form-control" id="addSpecialty" name="specialty" required>
                        </div>
                        <div class="modal-footer sf-modal-footer"> <%--Namespaced the modal footer class--%>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add Entrainer</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Entrainer Table -->
    <div class="table-container">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Specialty</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Entrainer> entrainers = (List<Entrainer>) request.getAttribute("entrainers");
                    if (entrainers != null) {
                        for (Entrainer entrainer : entrainers) {
                %>
                <tr>
                    <td><%= entrainer.getId() %>
                    </td>
                    <td><%= entrainer.getUser() != null ? entrainer.getUser().getUsername() : "" %>
                    </td>
                    <td><%= entrainer.getUser() != null ? entrainer.getUser().getEmail() : "" %>
                    </td>
                    <td><%= entrainer.getFirstName() %>
                    </td>
                    <td><%= entrainer.getLastName() %>
                    </td>
                    <td><%= entrainer.getSpecialty() %>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                                    data-target="#editEntrainerModal<%= entrainer.getId() %>">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                            <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                    data-target="#deleteEntrainerModal<%= entrainer.getId() %>">
                                <i class="fas fa-trash-alt"></i> Delete
                            </button>
                        </div>
                    </td>
                </tr>

                <!-- Edit Entrainer Modal -->
                <div class="modal fade sf-modal" id="editEntrainerModal<%= entrainer.getId() %>" tabindex="-1"  <%--Namespaced the modal fade class--%>
                     role="dialog" aria-labelledby="editEntrainerModalLabel<%= entrainer.getId() %>"
                     aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content sf-modal-content">  <%--Namespaced the modal content class--%>
                            <div class="modal-header sf-modal-header">  <%--Namespaced the modal header class--%>
                                <h5 class="modal-title sf-modal-title" id="editEntrainerModalLabel<%= entrainer.getId() %>"> <%--Namespaced the modal title class--%>
                                    <i class="fas fa-user-edit mr-2"></i> Edit Entrainer
                                </h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body sf-modal-body">  <%--Namespaced the modal body class--%>
                                <form action="<%=request.getContextPath()%>/admin/entrainers/edit" method="post">
                                    <input type="hidden" name="entrainerId" value="<%= entrainer.getId() %>">
                                    <input type="hidden" name="userId" value="<%= entrainer.getUserId() %>">

                                    <!-- User Fields -->
                                    <div class="form-group">
                                        <label for="editUsername<%= entrainer.getId() %>">Username:</label>
                                        <input type="text" class="form-control"
                                               id="editUsername<%= entrainer.getId() %>" name="username"
                                               value="<%= entrainer.getUser() != null ? entrainer.getUser().getUsername() : "" %>"
                                               required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editEmail<%= entrainer.getId() %>">Email:</label>
                                        <input type="email" class="form-control"
                                               id="editEmail<%= entrainer.getId() %>" name="email"
                                               value="<%= entrainer.getUser() != null ? entrainer.getUser().getEmail() : "" %>"
                                               required>
                                    </div>

                                    <!-- Entrainer Fields -->
                                    <div class="form-group">
                                        <label for="editFirstName<%= entrainer.getId() %>">First Name:</label>
                                        <input type="text" class="form-control"
                                               id="editFirstName<%= entrainer.getId() %>" name="firstName"
                                               value="<%= entrainer.getFirstName() %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editLastName<%= entrainer.getId() %>">Last Name:</label>
                                        <input type="text" class="form-control"
                                               id="editLastName<%= entrainer.getId() %>" name="lastName"
                                               value="<%= entrainer.getLastName() %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editSpecialty<%= entrainer.getId() %>">Specialty:</label>
                                        <input type="text" class="form-control"
                                               id="editSpecialty<%= entrainer.getId() %>" name="specialty"
                                               value="<%= entrainer.getSpecialty() %>" required>
                                    </div>
                                    <div class="modal-footer sf-modal-footer">  <%--Namespaced the modal footer class--%>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fas fa-times"></i> Cancel
                                        </button>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-save"></i> Save Changes
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Delete Entrainer Modal -->
                <div class="modal fade sf-modal" id="deleteEntrainerModal<%= entrainer.getId() %>" tabindex="-1"  <%--Namespaced the modal fade class--%>
                     role="dialog" aria-labelledby="deleteEntrainerModalLabel<%= entrainer.getId() %>"
                     aria-hidden="true">
                    <div class="modal-dialog modal-sm" role="document">
                        <div class="modal-content sf-modal-content">  <%--Namespaced the modal content class--%>
                            <div class="modal-header sf-modal-header">  <%--Namespaced the modal header class--%>
                                <h5 class="modal-title sf-modal-title" id="deleteEntrainerModalLabel<%= entrainer.getId() %>"> <%--Namespaced the modal title class--%>
                                    <i class="fas fa-exclamation-triangle text-danger mr-2"></i> Confirm Delete
                                </h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body text-center sf-modal-body">  <%--Namespaced the modal body class--%>
                                <p>Are you sure you want to delete entrainer
                                    <strong><%= entrainer.getFirstName() + " " + entrainer.getLastName() %></strong>?
                                </p>
                                <p class="text-muted small">This action cannot be undone.</p>
                                <form action="<%=request.getContextPath()%>/admin/entrainers/delete" method="post">
                                    <input type="hidden" name="entrainerId" value="<%= entrainer.getId() %>">
                                    <input type="hidden" name="userId" value="<%= entrainer.getUserId() %>">
                                    <div class="modal-footer justify-content-center border-0 pt-0 sf-modal-footer">  <%--Namespaced the modal footer class--%>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fas fa-times"></i> Cancel
                                        </button>
                                        <button type="submit" class="btn btn-danger">
                                            <i class="fas fa-trash-alt"></i> Delete
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Pagination if needed -->
    <nav aria-label="Entrainer pagination" class="mt-4">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
</div>

<!-- JavaScript for enhancing the page -->
<script>
    $(document).ready(function () {
        // Initialize tooltips
        $('[data-toggle="tooltip"]').tooltip();

        // Add animation delay to table rows
        $('.table tbody tr').each(function (index) {
            $(this).css('animation-delay', (index * 0.05) + 's');
            $(this).addClass('fade-in');
        });

        // Flash effect for success messages
        $('.alert-success').fadeIn(500).delay(3000).fadeOut(500);

        // Initialize Bootstrap Modals (Ensure Bootstrap JS is working)
        $('.modal').modal({
            show: false // Prevent from automatically showing on page load
        });
    });
</script>

<%@ include file="../common/footer.jsp" %>
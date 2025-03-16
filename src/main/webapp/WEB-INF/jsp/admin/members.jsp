<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportflow.model.Member" %>
<%@ page import="com.sportflow.model.User" %>
<%@ page import="com.sportflow.util.DateUtil" %>
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

    /* Card Styling */
    .dashboard-card {
        transition: all 0.3s ease;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        overflow: hidden;
        margin-bottom: 25px;
        border: none;
        height: 100%;
    }

    .dashboard-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0,0,0,0.1);
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
        text-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    /* Enhanced table styling */
    .table-container {
        background-color: #ffffff;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.05);
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
        box-shadow: 0 2px 5px rgba(0,0,0,0.08);
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
    .modal-content {
        border-radius: 12px;
        border: none;
        box-shadow: 0 15px 30px rgba(0,0,0,0.15);
        overflow: hidden;
    }

    .modal-header {
        border-bottom: 1px solid #f1f5f9;
        padding: 18px 24px;
        background-color: #f8fafc;
    }

    .modal-title {
        font-weight: 600;
        color: #0f172a;
        font-size: 1.2rem;
    }

    .modal-body {
        padding: 24px;
    }

    .modal-footer {
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
        0% { transform: translateY(0px); }
        50% { transform: translateY(-8px); }
        100% { transform: translateY(0px); }
    }

    .fade-in {
        animation: fadeIn 0.8s ease-in-out forwards;
        opacity: 0;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .fade-in-1 { animation-delay: 0.1s; }
    .fade-in-2 { animation-delay: 0.2s; }
    .fade-in-3 { animation-delay: 0.3s; }

    /* Background pattern */
    .bg-pattern {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%),
        linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%);
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
</style>

<div class="container dashboard-container">
    <div class="bg-pattern"></div>
    <div class="dashboard-header">
        <h1 class="dashboard-title">Member Management</h1>
        <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addMemberModal" style="margin-left: 750px">Add Member</button>

    </div>

    <!-- Add Member Modal -->
    <div class="modal fade" id="addMemberModal" tabindex="-1" role="dialog" aria-labelledby="addMemberModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addMemberModalLabel">Add New Member</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<%=request.getContextPath()%>/admin/members/add" method="post">
                        <!-- User fields -->
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

                        <!-- Member fields -->
                        <div class="form-group">
                            <label for="addFirstName">First Name:</label>
                            <input type="text" class="form-control" id="addFirstName" name="firstName" required>
                        </div>
                        <div class="form-group">
                            <label for="addLastName">Last Name:</label>
                            <input type="text" class="form-control" id="addLastName" name="lastName" required>
                        </div>
                        <div class="form-group">
                            <label for="addBirthDate">Birth Date:</label>
                            <input type="date" class="form-control" id="addBirthDate" name="birthDate" required>
                        </div>
                        <div class="form-group">
                            <label for="addSport">Sport:</label>
                            <input type="text" class="form-control" id="addSport" name="sport" required>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add Member</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- End Add Member Modal -->
    <div class="table-container fade-in">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Birth Date</th>
                    <th>Sport</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Member> members = (List<Member>) request.getAttribute("members");
                    if (members != null) {
                        for (Member member : members) { %>
                <tr>
                    <td><%= member.getId() %></td>
                    <td><%= member.getUser() != null ? member.getUser().getUsername() : "" %></td>
                    <td><%= member.getUser() != null ? member.getUser().getEmail() : "" %></td>
                    <td><%= member.getFirstName() + " " + member.getLastName() %></td>
                    <td><%= member.getBirthDate() != null ? DateUtil.formatDate(member.getBirthDate()) : "" %></td>
                    <td><%= member.getSport() %></td>
                    <td>
                        <span class="status-badge status-active">Active</span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editMemberModal<%= member.getId() %>">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                            <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteMemberModal<%= member.getId() %>">
                                <i class="fas fa-trash-alt"></i> Delete
                            </button>
                        </div>
                    </td>
                </tr>
                <%      }
                } else { %>
                <tr>
                    <td colspan="8" class="text-center py-4">No members found</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>


    <!-- Edit Member Modals -->
    <% if (members != null) {
        for (Member member : members) { %>
    <div class="modal fade" id="editMemberModal<%= member.getId() %>" tabindex="-1" role="dialog" aria-labelledby="editMemberModalLabel<%= member.getId() %>" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editMemberModalLabel<%= member.getId() %>">
                        <i class="fas fa-user-edit mr-2"></i> Edit Member
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<%=request.getContextPath()%>/admin/members/edit" method="post">
                        <input type="hidden" name="memberId" value="<%= member.getId() %>">
                        <input type="hidden" name="userId" value="<%= member.getUserId() %>">

                        <!-- User fields -->
                        <div class="form-group">
                            <label for="editUsername<%= member.getId() %>">Username</label>
                            <input type="text" class="form-control" id="editUsername<%= member.getId() %>" name="username" value="<%= member.getUser() != null ? member.getUser().getUsername() : "" %>" required>
                        </div>
                        <div class="form-group">
                            <label for="editEmail<%= member.getId() %>">Email</label>
                            <input type="email" class="form-control" id="editEmail<%= member.getId() %>" name="email" value="<%= member.getUser() != null ? member.getUser().getEmail() : "" %>" required>
                        </div>

                        <!-- Member fields -->
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="editFirstName<%= member.getId() %>">First Name</label>
                                    <input type="text" class="form-control" id="editFirstName<%= member.getId() %>" name="firstName" value="<%= member.getFirstName() %>" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="editLastName<%= member.getId() %>">Last Name</label>
                                    <input type="text" class="form-control" id="editLastName<%= member.getId() %>" name="lastName" value="<%= member.getLastName() %>" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBirthDate<%= member.getId() %>">Birth Date</label>
                            <input type="date" class="form-control" id="editBirthDate<%= member.getId() %>" name="birthDate" value="<%= member.getBirthDate() != null ? DateUtil.formatDate(member.getBirthDate()) : "" %>" required>
                        </div>
                        <div class="form-group">
                            <label for="editSport<%= member.getId() %>">Sport</label>
                            <select class="form-control" id="editSport<%= member.getId() %>" name="sport" required>
                                <option value="Football" <%= "Football".equals(member.getSport()) ? "selected" : "" %>>Football</option>
                                <option value="Basketball" <%= "Basketball".equals(member.getSport()) ? "selected" : "" %>>Basketball</option>
                                <option value="Tennis" <%= "Tennis".equals(member.getSport()) ? "selected" : "" %>>Tennis</option>
                                <option value="Swimming" <%= "Swimming".equals(member.getSport()) ? "selected" : "" %>>Swimming</option>
                                <option value="Running" <%= "Running".equals(member.getSport()) ? "selected" : "" %>>Running</option>
                                <option value="Yoga" <%= "Yoga".equals(member.getSport()) ? "selected" : "" %>>Yoga</option>
                                <option value="Cycling" <%= "Cycling".equals(member.getSport()) ? "selected" : "" %>>Cycling</option>
                                <option value="Other" <%= "Other".equals(member.getSport()) ? "selected" : "" %>>Other</option>
                            </select>
                        </div>
                        <div class="modal-footer">
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

    <!-- Delete Member Modal -->
    <div class="modal fade" id="deleteMemberModal<%= member.getId() %>" tabindex="-1" role="dialog" aria-labelledby="deleteMemberModalLabel<%= member.getId() %>" aria-hidden="true">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteMemberModalLabel<%= member.getId() %>">
                        <i class="fas fa-exclamation-triangle text-danger mr-2"></i> Confirm Delete
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    <p>Are you sure you want to delete member <strong><%= member.getFirstName() + " " + member.getLastName() %></strong>?</p>
                    <p class="text-muted small">This action cannot be undone.</p>
                    <form action="<%=request.getContextPath()%>/admin/members/delete" method="post">
                        <input type="hidden" name="memberId" value="<%= member.getId() %>">
                        <input type="hidden" name="userId" value="<%= member.getUserId() %>">
                        <div class="modal-footer justify-content-center border-0 pt-0">
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
    <%  }
    } %>

    <!-- Pagination if needed -->
    <nav aria-label="Member pagination" class="mt-4">
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
    $(document).ready(function() {
        // Initialize tooltips
        $('[data-toggle="tooltip"]').tooltip();

        // Add animation delay to table rows
        $('.table tbody tr').each(function(index) {
            $(this).css('animation-delay', (index * 0.05) + 's');
            $(this).addClass('fade-in');
        });

        // Flash effect for success messages
        $('.alert-success').fadeIn(500).delay(3000).fadeOut(500);

        // Confirm password validation for add member form
        $('#addMemberForm').submit(function(e) {
            const password = $('#addPassword').val();
            const confirmPassword = $('#addConfirmPassword').val();

            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match!');
                return false;
            }
            return true;
        });
    });
</script>

<%@ include file="../common/footer.jsp" %>
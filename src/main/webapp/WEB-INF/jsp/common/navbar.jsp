<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sportflow.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("user");
%>

<!-- Add Font Awesome CDN for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<style>
    .sf-navbar {
        background: linear-gradient(135deg, #0061f2 0%, #00c6f9 100%);
        padding: 15px 0;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .sf-navbar-brand {
        color: #ffffff;
        font-weight: 700;
        font-size: 24px;
        padding: 5px 15px;
        border-radius: 4px;
        position: relative;
        transition: all 0.3s ease;
    }

    .sf-navbar-brand:hover {
        color: #ffffff;
        text-decoration: none;
        transform: scale(1.05);
    }

    .sf-navbar-brand::before {
        content: '';
        position: absolute;
        width: 30px;
        height: 30px;
        background-color: rgba(255, 255, 255, 0.1);
        border-radius: 50%;
        top: 50%;
        left: -15px;
        transform: translateY(-50%);
        z-index: -1;
    }

    .sf-navbar-toggler {
        border: none;
        background-color: rgba(255, 255, 255, 0.15);
        border-radius: 4px;
        padding: 8px 10px;
    }

    .sf-navbar-toggler:focus {
        outline: none;
        box-shadow: none;
    }

    .sf-navbar-toggler-icon {
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='30' height='30' viewBox='0 0 30 30'%3e%3cpath stroke='rgba(255, 255, 255, 1)' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e");
    }

    .sf-nav-link {
        color: rgba(255, 255, 255, 0.85);
        margin: 0 10px;
        font-weight: 500;
        position: relative;
        padding: 8px 0;
        transition: all 0.3s ease;
    }

    .sf-nav-link:hover, .sf-nav-link.active {
        color: #ffffff;
    }

    .sf-nav-link::after {
        content: '';
        position: absolute;
        width: 0;
        height: 2px;
        background-color: #ffffff;
        bottom: 0;
        left: 0;
        transition: width 0.3s ease;
    }

    .sf-nav-link:hover::after, .sf-nav-link.active::after {
        width: 100%;
    }

    .sf-navbar-user {
        background-color: rgba(255, 255, 255, 0.15);
        padding: 5px 15px;
        border-radius: 20px;
        color: #ffffff;
        font-weight: 500;
        display: flex;
        align-items: center;
        animation: pulse 2s infinite;
    }

    .sf-navbar-user i {
        margin-right: 8px;
    }

    @keyframes pulse {
        0% {
            box-shadow: 0 0 0 0 rgba(255, 255, 255, 0.4);
        }
        70% {
            box-shadow: 0 0 0 8px rgba(255, 255, 255, 0);
        }
        100% {
            box-shadow: 0 0 0 0 rgba(255, 255, 255, 0);
        }
    }

    /* Role-specific colors */
    .role-admin .sf-nav-link.active::after {
        background-color: #FF6B6B;
    }

    .role-member .sf-nav-link.active::after {
        background-color: #4ECDC4;
    }

    .role-entrainer .sf-nav-link.active::after {
        background-color: #FFD166;
    }

    /* Logout button */
    .sf-logout-btn {
        color: rgba(255, 255, 255, 0.85);
        background-color: rgba(255, 255, 255, 0.1);
        border: 1px solid rgba(255, 255, 255, 0.2);
        border-radius: 20px;
        padding: 5px 15px;
        transition: all 0.3s ease;
    }

    .sf-logout-btn:hover {
        background-color: rgba(255, 255, 255, 0.2);
        color: #ffffff;
    }

    /* Right-aligned user menu */
    .user-menu {
        margin-left: auto;
    }

    /* Mobile optimizations */
    @media (max-width: 991px) {
        .sf-navbar-collapse {
            background-color: rgba(0, 97, 242, 0.95);
            border-radius: 8px;
            margin-top: 10px;
            padding: 10px;
        }

        .sf-nav-link {
            padding: 10px 15px;
            margin: 5px 0;
        }

        .sf-navbar-user {
            margin: 10px 15px;
        }
    }
</style>

<nav class="navbar navbar-expand-lg sf-navbar role-${loggedInUser != null ? loggedInUser.getRole().toLowerCase() : 'guest'}">
    <div class="container">
        <a class="navbar-brand sf-navbar-brand" href="<%=request.getContextPath()%>/">
            <i class="fas fa-running me-2"></i>SportFlow
        </a>
        <button class="navbar-toggler sf-navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon sf-navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse sf-navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <% if (loggedInUser != null) { %>
                <% if ("ADMIN".equals(loggedInUser.getRole())) { %>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/admin/dashboard"><i class="fas fa-chart-line me-1"></i> Dashboard</a></li>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/admin/members"><i class="fas fa-users me-1"></i> Members</a></li>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/admin/entrainers"><i class="fas fa-user-tie me-1"></i> Entrainers</a></li>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%= request.getContextPath() %>/admin/add-admin"><i class="fas fa-user-plus me-1"></i> Add Admin</a></li>
                <% } else if ("MEMBER".equals(loggedInUser.getRole())) { %>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/member/dashboard"><i class="fas fa-home me-1"></i> Dashboard</a></li>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/member/book-session"><i class="fas fa-calendar-plus me-1"></i> Book Session</a></li>
                <% } else if ("ENTRAINER".equals(loggedInUser.getRole())) { %>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/entrainer/dashboard"><i class="fas fa-tachometer-alt me-1"></i> Dashboard</a></li>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/entrainer/sessions"><i class="fas fa-calendar-check me-1"></i> Sessions</a></li>
                <% } %>
                <% } else { %>
                <li class="nav-item"><a class="nav-link sf-nav-link" href="<%=request.getContextPath()%>/auth/login"><i class="fas fa-sign-in-alt me-1"></i> Login</a></li>
                <% } %>
            </ul>
        </div>

        <% if (loggedInUser != null) { %>
        <!-- User info and logout button, right-aligned -->
        <ul class="navbar-nav ms-auto d-flex align-items-center">
            <li class="nav-item">
                <span class="sf-navbar-user">
                    <i class="fas fa-user-circle"></i>
                    <%= loggedInUser.getUsername() %>
                </span>
            </li>
            <li class="nav-item ms-2">
                <a class="nav-link sf-logout-btn" href="<%=request.getContextPath()%>/auth/logout">
                    <i class="fas fa-sign-out-alt me-1"></i> Logout
                </a>
            </li>
        </ul>
        <% } %>
    </div>
</nav>

<!-- Add a small script to mark current page as active -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Get current path
        var path = window.location.pathname;

        // Add active class to the current page link
        document.querySelectorAll('.sf-nav-link').forEach(function(link) {
            if (link.getAttribute('href') === path) {
                link.classList.add('active');

                // Also add active class to parent if it's a dropdown item
                var parent = link.closest('.nav-item.dropdown');
                if (parent) {
                    parent.querySelector('.nav-link.dropdown-toggle').classList.add('active');
                }
            }
        });
    });
</script>
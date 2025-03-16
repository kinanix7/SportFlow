<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<!-- Add Font Awesome CDN in the head section if not already included -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- Custom CSS for animations and styling -->
<style>
    body {
        background-color: #f5f7fa;
    }

    .dashboard-container {
        padding: 30px 0;
    }

    .dashboard-card {
        transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
        border-radius: 12px;
        box-shadow: 0 6px 15px rgba(0,0,0,0.08);
        overflow: hidden;
        margin-bottom: 25px;
        border: none;
    }

    .dashboard-card:hover {
        transform: translateY(-8px);
        box-shadow: 0 12px 24px rgba(0,0,0,0.15);
    }

    .card-icon {
        font-size: 3rem;
        margin-bottom: 20px;
        animation: float 3s ease-in-out infinite;
    }

    /* Different colors for each card */
    .members-card {
        background: linear-gradient(135deg, #6366F1, #8B5CF6);
    }

    .sessions-card {
        background: linear-gradient(135deg, #10B981, #3B82F6);
    }

    .bookings-card {
        background: linear-gradient(135deg, #F59E0B, #EF4444);
    }

    .card-title {
        font-weight: 700;
        margin-bottom: 10px;
        color: rgba(255, 255, 255, 0.9);
        font-size: 1.25rem;
    }

    .card-text {
        font-size: 2.5rem;
        font-weight: 800;
        color: #ffffff;
        text-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .stat-card {
        text-align: center;
        padding: 30px 20px;
        height: 100%;
        position: relative;
        overflow: hidden;
        z-index: 1;
    }

    /* Animated background patterns */
    .stat-card::before {
        content: '';
        position: absolute;
        top: -50%;
        left: -50%;
        width: 200%;
        height: 200%;
        background-image: radial-gradient(circle, rgba(255,255,255,0.1) 10%, transparent 10.5%);
        background-size: 20px 20px;
        opacity: 0.3;
        z-index: -1;
        animation: rotate 60s linear infinite;
    }

    @keyframes rotate {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    @keyframes float {
        0% {
            transform: translateY(0px);
        }
        50% {
            transform: translateY(-10px);
        }
        100% {
            transform: translateY(0px);
        }
    }

    .dashboard-header {
        margin-bottom: 40px;
        padding-bottom: 20px;
        border-bottom: 2px solid rgba(0,0,0,0.05);
    }

    .dashboard-title {
        color: #1E293B;
        font-weight: 700;
        font-size: 2.2rem;
        position: relative;
        display: inline-block;
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

    /* Animations for cards appearing */
    .fade-in {
        animation: fadeIn 1s ease-in-out forwards;
        opacity: 0;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(30px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .fade-in-1 { animation-delay: 0.2s; }
    .fade-in-2 { animation-delay: 0.4s; }
    .fade-in-3 { animation-delay: 0.6s; }

    /* Counter animation */
    .counter {
        display: inline-block;
        animation: counter 2s ease-out forwards;
        animation-delay: 0.8s;
    }

    @keyframes counter {
        from { opacity: 0.3; }
        to { opacity: 1; }
    }

    /* Add a subtle pattern to the background */
    .bg-pattern {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%),
        linear-gradient(45deg, #f1f5f9 25%, transparent 25%, transparent 75%, #f1f5f9 75%);
        background-size: 20px 20px;
        background-position: 0 0, 10px 10px;
        opacity: 0.3;
        z-index: -1;
    }
</style>

<div class="bg-pattern"></div>

<div class="container dashboard-container">
    <div class="dashboard-header">
        <h1 class="dashboard-title"><i class="fas fa-tachometer-alt me-3"></i>Admin Dashboard</h1>
    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="dashboard-card fade-in fade-in-1">
                <div class="stat-card members-card">
                    <div class="card-body">
                        <i class="fas fa-users card-icon"></i>
                        <h5 class="card-title">Total Members</h5>
                        <p class="card-text"><span class="counter">${totalMembers}</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="dashboard-card fade-in fade-in-2">
                <div class="stat-card sessions-card">
                    <div class="card-body">
                        <i class="fas fa-calendar-check card-icon"></i>
                        <h5 class="card-title">Total Sessions</h5>
                        <p class="card-text"><span class="counter">${totalSessions}</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="dashboard-card fade-in fade-in-3">
                <div class="stat-card bookings-card">
                    <div class="card-body">
                        <i class="fas fa-ticket-alt card-icon"></i>
                        <h5 class="card-title">Total Bookings</h5>
                        <p class="card-text"><span class="counter">${totalBookings}</span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Optional: Add JavaScript for counter animation if you want the numbers to count up -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // This is just a placeholder for actual counter animation
        // You would need to implement the actual counting animation
        const counters = document.querySelectorAll('.counter');
        counters.forEach(counter => {
            counter.classList.add('counted');
        });
    });
</script>

<%@ include file="../common/footer.jsp" %>
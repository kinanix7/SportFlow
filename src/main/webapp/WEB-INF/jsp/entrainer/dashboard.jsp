<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/navbar.jsp" %>

<!-- Add Font Awesome CDN for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- Custom CSS for styling -->
<style>
    body {
        background-color: #f8f9fa;
        font-family: 'Arial', sans-serif;
    }

    .container {
        padding: 40px 0;
    }

    h1 {
        color: #343a40;
        font-weight: 700;
        margin-bottom: 30px;
        text-align: center;
        position: relative;
    }

    h1::after {
        content: '';
        position: absolute;
        bottom: -10px;
        left: 50%;
        transform: translateX(-50%);
        width: 80px;
        height: 4px;
        background: linear-gradient(to right, #6a11cb, #2575fc);
        border-radius: 2px;
    }

    .card {
        border: none;
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: all 0.3s ease;
        overflow: hidden;
    }

    .card:hover {
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        transform: translateY(-5px);
    }

    .card-body {
        padding: 30px;
        background: linear-gradient(135deg, #6a11cb, #2575fc);
        color: #ffffff;
        text-align: center;
    }

    .card-title {
        font-size: 1.75rem;
        margin-bottom: 20px;
        position: relative;
    }

    .card-title::after {
        content: '';
        position: absolute;
        bottom: -10px;
        left: 50%;
        transform: translateX(-50%);
        width: 50px;
        height: 3px;
        background: #ffffff;
        border-radius: 2px;
    }

    .card-text {
        font-size: 2.5rem;
        font-weight: 800;
        color: #ffffff;
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    }

    .card-icon {
        font-size: 3rem;
        margin-bottom: 20px;
        color: #ffffff;
    }
</style>

<div class="container">
    <h1>Entrainer Dashboard</h1>
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <div class="card">
                <div class="card-body">
                    <i class="fas fa-users card-icon"></i>
                    <h5 class="card-title">Total Members Enrolled</h5>
                    <p class="card-text">${totalMembers}</p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Glassmorphism Login Form</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <style media="screen">
        *,
        *:before,
        *:after {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body {
            background-color: #007BFF; /* Changed to blue */
            overflow: hidden;
        }
        .background {
            width: 430px;
            height: 520px;
            position: absolute;
            transform: translate(-50%, -50%);
            left: 50%;
            top: 50%;
        }
        .background .shape {
            height: 200px;
            width: 200px;
            position: absolute;
            border-radius: 50%;
            animation: float 6s ease-in-out infinite, rotate 6s linear infinite;
        }
        .shape:first-child {
            background: linear-gradient(
                    #1845ad,
                    #23a2f6
            );
            left: -80px;
            top: -80px;
            animation-delay: 0s;
        }
        .shape:last-child {
            background: linear-gradient(
                    to right,
                    #ff512f,
                    #f09819
            );
            right: -30px;
            bottom: -80px;
            animation-delay: 3s;
        }

        @keyframes float {
            0% {
                transform: translateY(0px) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(10deg);
            }
            100% {
                transform: translateY(0px) rotate(0deg);
            }
        }

        @keyframes rotate {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        .container {
            height: 520px;
            width: 400px;
            background-color: rgba(255, 255, 255, 0.13);
            position: absolute;
            transform: translate(-50%, -50%);
            top: 50%;
            left: 50%;
            border-radius: 10px;
            backdrop-filter: blur(10px);
            border: 2px solid rgba(255, 255, 255, 0.1);
            box-shadow: 0 0 40px rgba(8, 7, 16, 0.6);
            padding: 50px 35px;
            opacity: 0;
            animation: fadeIn 1.2s ease-in-out forwards;
        }

        @keyframes fadeIn {
            0% {
                opacity: 0;
                transform: translate(-50%, -60%);
            }
            100% {
                opacity: 1;
                transform: translate(-50%, -50%);
            }
        }

        .container * {
            font-family: 'Poppins', sans-serif;
            color: #ffffff;
            letter-spacing: 0.5px;
            outline: none;
            border: none;
        }
        .container h1 {
            font-size: 32px;
            font-weight: 500;
            line-height: 42px;
            text-align: center;
            opacity: 0;
            animation: slideDown 0.8s ease-in-out 0.5s forwards;
        }

        @keyframes slideDown {
            0% {
                opacity: 0;
                transform: translateY(-20px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .form-group {
            margin-top: 30px;
            opacity: 0;
            animation: slideUp 0.8s ease-in-out forwards;
        }

        .form-group:nth-child(1) {
            animation-delay: 0.8s;
        }

        .form-group:nth-child(2) {
            animation-delay: 1.2s;
        }

        @keyframes slideUp {
            0% {
                opacity: 0;
                transform: translateY(20px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        label {
            font-size: 16px;
            font-weight: 500;
            display: block;
        }
        input {
            display: block;
            height: 50px;
            width: 100%;
            background-color: rgba(255, 255, 255, 0.07);
            border-radius: 3px;
            padding: 0 10px;
            margin-top: 8px;
            font-size: 14px;
            font-weight: 300;
            transition: all 0.3s ease;
        }

        input:focus {
            background-color: rgba(255, 255, 255, 0.15);
            transform: scale(1.02);
            box-shadow: 0 0 10px rgba(255, 255, 255, 0.2);
        }

        ::placeholder {
            color: #e5e5e5;
        }
        button {
            margin-top: 50px;
            width: 100%;
            background-color: #ffffff;
            color: #080710;
            padding: 15px 0;
            font-size: 18px;
            font-weight: 600;
            border-radius: 5px;
            cursor: pointer;
            opacity: 0;
            animation: pulse 0.8s ease-in-out 1.5s forwards;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        @keyframes pulse {
            0% {
                opacity: 0;
                transform: scale(0.9);
            }
            70% {
                opacity: 1;
                transform: scale(1.05);
            }
            100% {
                opacity: 1;
                transform: scale(1);
            }
        }

        button:hover {
            background-color: #f0f0f0;
            transform: scale(1.03);
            box-shadow: 0 0 15px rgba(255, 255, 255, 0.3);
        }

        .alert {
            margin-top: 20px;
            padding: 10px;
            border-radius: 5px;
            animation: shake 0.5s cubic-bezier(.36,.07,.19,.97) both;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
            20%, 40%, 60%, 80% { transform: translateX(5px); }
        }

        .alert-danger {
            background-color: rgba(255, 0, 0, 0.2);
            color: #ffffff;
        }
    </style>
</head>
<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<div class="container">
    <h1>Login</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger" role="alert">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <form action="<%=request.getContextPath()%>/auth/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
</div>

<script>
    // Add ripple effect to button
    const button = document.querySelector('button');
    button.addEventListener('click', function(e) {
        let x = e.clientX - e.target.offsetLeft;
        let y = e.clientY - e.target.offsetTop;

        let ripple = document.createElement('span');
        ripple.style.left = x + 'px';
        ripple.style.top = y + 'px';
        ripple.style.width = '200px';
        ripple.style.height = '200px';
        ripple.style.backgroundColor = 'rgba(255, 255, 255, 0.3)';
        ripple.style.borderRadius = '50%';
        ripple.style.position = 'absolute';
        ripple.style.transform = 'translate(-50%, -50%) scale(0)';
        ripple.style.animation = 'ripple-effect 0.6s linear';

        this.appendChild(ripple);

        setTimeout(() => {
            ripple.remove();
        }, 600);
    });
</script>

<%@ include file="../common/footer.jsp" %>
</body>
</html>

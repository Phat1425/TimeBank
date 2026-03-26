<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên Mật Khẩu - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .step-indicator {
            display: flex;
            justify-content: center;
            gap: 0;
            margin-bottom: 25px;
        }
        .step-dot {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 13px;
            font-weight: bold;
            background: #e4e6eb;
            color: var(--text-secondary);
            position: relative;
        }
        .step-dot.active {
            background: var(--primary-color);
            color: #fff;
        }
        .step-dot.done {
            background: var(--success-color);
            color: #fff;
        }
        .step-line {
            width: 60px;
            height: 2px;
            background: #e4e6eb;
            align-self: center;
        }
        .step-line.done {
            background: var(--success-color);
        }
        .success-box {
            background: #d4edda;
            color: #155724;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 15px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-container">
            <h2>🔐 Quên Mật Khẩu</h2>
            <p style="margin-bottom: 20px; color: var(--text-secondary);">Time Bank - Khôi phục tài khoản</p>

            <%
                String step = (String) request.getAttribute("step");
                if (step == null) step = "verify";
            %>

            <!-- Step Indicator -->
            <div class="step-indicator">
                <div class="step-dot <%= "reset".equals(step) ? "done" : "active" %>">1</div>
                <div class="step-line <%= "reset".equals(step) ? "done" : "" %>"></div>
                <div class="step-dot <%= "reset".equals(step) ? "active" : "" %>">2</div>
            </div>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error-msg"><%= request.getAttribute("error") %></div>
            <% } %>

            <% if ("verify".equals(step)) { %>
                <!-- Bước 1: Nhập Email -->
                <p style="margin-bottom: 15px; font-size: 14px; color: var(--text-secondary); text-align: left;">
                    Nhập email đã đăng ký. Chúng tôi sẽ xác minh tài khoản của bạn.
                </p>
                <form action="forgot-password" method="POST" class="auth-form">
                    <input type="hidden" name="step" value="verify">
                    <input type="email" name="email" placeholder="📧 Email đã đăng ký" required>
                    <button type="submit" class="btn btn-primary">Xác Minh Email</button>
                </form>

            <% } else if ("reset".equals(step)) { %>
                <!-- Bước 2: Đặt mật khẩu mới -->
                <p style="margin-bottom: 15px; font-size: 14px; color: var(--text-secondary); text-align: left;">
                    Email <strong><%= request.getAttribute("email") %></strong> đã được xác minh.<br>
                    Hãy nhập mật khẩu mới của bạn.
                </p>
                <form action="forgot-password" method="POST" class="auth-form">
                    <input type="hidden" name="step" value="reset">
                    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
                    <input type="password" name="newPassword" placeholder="🔑 Mật khẩu mới" required>
                    <input type="password" name="confirmPassword" placeholder="🔑 Xác nhận mật khẩu" required>
                    <button type="submit" class="btn btn-success">Đặt Lại Mật Khẩu</button>
                </form>
            <% } %>

            <div class="auth-divider"></div>
            <a href="login" class="btn" style="background:#e4e6eb; color:#000; width: auto;">← Quay lại đăng nhập</a>
        </div>
    </div>
</body>
</html>

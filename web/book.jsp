<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt Lịch - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .book-container {
            background: var(--card-bg);
            padding: 30px;
            border-radius: var(--radius);
            box-shadow: var(--shadow-sm);
        }
        .service-info {
            background: #f0f2f5;
            padding: 20px;
            border-radius: var(--radius);
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    
    <div class="main-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <%@ include file="includes/sidebar.jsp" %>
        </div>
        
        <!-- Main Content -->
        <div class="content">
            <div class="book-container">
                <h2>Đặt Lịch Dịch Vụ</h2>
                <c:if test="${not empty error}">
                    <div class="error-msg">${error}</div>
                </c:if>
                
                <div class="service-info">
                    <h3>${service.title}</h3>
                    <p style="margin:10px 0;">Cung cấp bởi: <strong>${service.user.fullName}</strong></p>
                    <p>Mô tả: ${service.description}</p>
                    <p style="margin-top:10px; color:var(--success-color); font-weight:bold;">
                        Số Credit yêu cầu: ${service.hoursRequired} giờ
                    </p>
                    <p style="margin-top:5px;">
                        Số Credit của bạn: <strong>${sessionScope.user.walletHours} giờ</strong>
                    </p>
                </div>

                <form action="book" method="POST" class="auth-form">
                    <input type="hidden" name="serviceId" value="${service.serviceId}">
                    <input type="hidden" name="providerId" value="${service.user.userId}">
                    
                    <label style="font-weight:bold;">Thời gian dự kiến:</label>
                    <input type="datetime-local" name="scheduleTime" required>

                    <button type="submit" class="btn btn-primary">Xác nhận Đặt Lịch</button>
                    <a href="home" class="btn" style="background:#e4e6eb; color:#000;">Hủy</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Cá Nhân - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .profile-header {
            background: var(--card-bg);
            padding: 40px;
            border-radius: var(--radius-lg);
            border: 1px solid var(--border-color);
            text-align: center;
            margin-bottom: 30px;
            box-shadow: var(--shadow-sm);
        }
        .profile-avatar {
            width: 160px;
            height: 160px;
            border-radius: 50%;
            object-fit: cover;
            border: 6px solid #f1f5f9;
            box-shadow: var(--shadow-sm);
            margin-bottom: 20px;
        }
        .profile-name {
            font-size: 28px;
            font-weight: 800;
            color: var(--text-primary);
            letter-spacing: -0.025em;
        }
        .profile-stats {
            display: flex;
            justify-content: center;
            gap: 40px;
            margin-top: 24px;
        }
        .stat-item {
            display: flex;
            flex-direction: column;
            gap: 4px;
        }
        .stat-item .stat-value {
            font-size: 24px;
            font-weight: 800;
            color: var(--primary-color);
        }
        .stat-item .stat-label {
            font-size: 13px;
            font-weight: 600;
            color: var(--text-secondary);
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        .section-title {
            margin: 40px 0 20px;
            font-size: 20px;
            font-weight: 800;
            color: var(--text-primary);
            display: flex;
            align-items: center;
            gap: 12px;
        }
        .section-title::after {
            content: '';
            flex: 1;
            height: 1px;
            background: var(--border-color);
        }
        .card {
            background: var(--card-bg);
            padding: 24px;
            border-radius: var(--radius-lg);
            border: 1px solid var(--border-color);
            margin-bottom: 20px;
            transition: all 0.2s;
        }
        .card:hover {
            border-color: var(--primary-color);
            box-shadow: var(--shadow-md);
        }
        .review-item {
            border-bottom: 1px solid var(--border-color);
            padding: 16px 0;
        }
        .review-item:last-child {
            border-bottom: none;
        }
        .stars { color: #f59e0b; }
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
            <div class="profile-header">
                <c:choose>
                    <c:when test="${not empty targetUser.avatar}">
                        <img src="images/${targetUser.avatar}" alt="Avatar" class="profile-avatar" onerror="this.src='images/default_avatar.png'">
                    </c:when>
                    <c:otherwise>
                        <img src="images/default_avatar.png" alt="Avatar" class="profile-avatar">
                    </c:otherwise>
                </c:choose>
                <div class="profile-name">${targetUser.fullName}</div>
                <div class="profile-stats">
                    <div class="stat-item">
                        <span class="stat-value">${targetUser.walletHours}</span>
                        <span class="stat-label">Giờ (Credit)</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-value"><fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> ⭐</span>
                        <span class="stat-label">Đánh giá</span>
                    </div>
                </div>
                
                <c:if test="${sessionScope.user.userId != targetUser.userId}">
                    <a href="chat?to=${targetUser.userId}" class="btn btn-primary" style="margin-top: 20px;">Nhắn tin</a>
                </c:if>
                <c:if test="${sessionScope.user.userId == targetUser.userId}">
                    <a href="logout" class="btn " style="margin-top:20px; background:#e4e6eb; color:#000;">Đăng xuất</a>
                </c:if>
            </div>
            
            <h3 class="section-title">Dịch vụ đã đăng</h3>
            <c:forEach items="${services}" var="s">
                <div class="card">
                    <h4>${s.title}</h4>
                    <p style="color: var(--text-secondary); font-size: 14px; margin-bottom: 10px;">
                        Yêu cầu: <strong>${s.hoursRequired} giờ</strong> | Ngày đăng: ${s.createdAt}
                    </p>
                    <p>${s.description}</p>
                    <c:if test="${sessionScope.user.userId != targetUser.userId}">
                        <a href="book?serviceId=${s.serviceId}" class="btn btn-primary" style="margin-top:10px; padding: 8px 15px; font-size:14px;">Đặt lịch</a>
                    </c:if>
                </div>
            </c:forEach>
            <c:if test="${empty services}">
                <p>Chưa có dịch vụ nào.</p>
            </c:if>
            
            <h3 class="section-title">Đánh giá từ người đã thuê</h3>
            <div class="card">
                <c:forEach items="${reviews}" var="r">
                    <div class="review-item">
                        <div style="display:flex; align-items:center; gap: 10px;">
                            <strong>${r.reviewer.fullName}</strong>
                            <span class="stars">
                                <c:forEach begin="1" end="${r.rating}">⭐</c:forEach>
                            </span>
                        </div>
                        <p style="margin-top: 5px;">${r.comment}</p>
                    </div>
                </c:forEach>
                <c:if test="${empty reviews}">
                    <p>Chưa có đánh giá nào.</p>
                </c:if>
            </div>
        </div>
        
        <!-- Right Sidebar -->
        <div class="right-sidebar">
            <%@ include file="includes/right-sidebar.jsp" %>
        </div>
    </div>
</body>
</html>

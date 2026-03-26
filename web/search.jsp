<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kết Quả Tìm Kiếm - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    
    <div class="main-container">
        <div class="sidebar">
            <%@ include file="includes/sidebar.jsp" %>
        </div>
        
        <div class="content">
            <h2 class="section-title">Kết Quả Tìm Kiếm cho "${keyword}"</h2>

            <c:forEach items="${services}" var="s">
                <div class="post-card">
                    <div style="display:flex; align-items:center; gap:12px; margin-bottom:16px;">
                        <c:choose>
                            <c:when test="${not empty s.user.avatar}">
                                <img src="images/${s.user.avatar}" alt="Avatar" class="avatar-sm">
                            </c:when>
                            <c:otherwise>
                                <img src="images/default_avatar.png" alt="Avatar" class="avatar-sm">
                            </c:otherwise>
                        </c:choose>
                        <div>
                            <a href="profile?id=${s.user.userId}" style="font-weight:700; color:var(--text-primary); text-decoration:none; font-size: 15px;">${s.user.fullName}</a>
                            <div style="font-size:12px; color:var(--text-secondary); font-weight: 500;">${s.createdAt}</div>
                        </div>
                    </div>
                    <div style="font-size: 20px; font-weight:800; color: var(--text-primary); margin-bottom:8px; letter-spacing: -0.01em;">${s.title}</div>
                    <div style="margin-bottom:16px; color: var(--text-secondary); line-height: 1.6;">${s.description}</div>
                    <div style="font-weight:700; color:var(--success-color); margin-bottom:20px; font-size: 14px; display: flex; align-items: center; gap: 6px;">
                        <span>⏱️</span> Yêu cầu: ${s.hoursRequired} Giờ
                    </div>
                    
                    <c:if test="${sessionScope.user.userId != s.user.userId}">
                        <div style="display: flex; justify-content: flex-end;">
                            <a href="book?serviceId=${s.serviceId}" class="btn btn-primary" style="padding: 10px 24px; font-size: 14px;">Đặt lịch ngay</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
            <c:if test="${empty services}">
                <p>Không tìm thấy dịch vụ nào phù hợp với từ khóa.</p>
            </c:if>
        </div>
        
        <div class="right-sidebar">
            <%@ include file="includes/right-sidebar.jsp" %>
        </div>
    </div>
</body>
</html>

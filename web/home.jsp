<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bảng Tin - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
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
            <!-- Create Post area -->
            <div style="background: rgba(255,255,255,0.05); backdrop-filter:blur(30px); padding:32px; border-radius:var(--radius-xl); border: 1px solid rgba(255,255,255,0.1); margin-bottom: 40px; display:flex; gap:24px; align-items:center; box-shadow: var(--shadow-xl); transition: all 0.4s ease;">
                <c:choose>
                    <c:when test="${not empty sessionScope.user.avatar}">
                        <img src="images/${sessionScope.user.avatar}" class="avatar-sm" style="width: 60px; height: 60px; border-radius: 20px; border: 3px solid rgba(255,255,255,0.2); box-shadow: 0 10px 20px rgba(0,0,0,0.2);">
                    </c:when>
                    <c:otherwise>
                        <img src="images/default_avatar.png" class="avatar-sm" style="width: 60px; height: 60px; border-radius: 20px; border: 3px solid rgba(255,255,255,0.2); box-shadow: 0 10px 20px rgba(0,0,0,0.2);">
                    </c:otherwise>
                </c:choose>
                <a href="post" style="flex:1; background:rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); padding:20px 30px; border-radius:30px; text-decoration:none; color:#e2e8f0; font-weight: 700; font-size: 17px; transition: all 0.4s cubic-bezier(0.4, 0, 1, 1); cursor: text;">
                    <span style="opacity: 0.6 ;">Có điều gì bạn muốn chia sẻ hôm nay?</span>
                </a>
                <div onclick="location.href='post'" style="background: var(--primary-gradient); color: white; width: 56px; height: 56px; border-radius: 18px; display: flex; align-items: center; justify-content: center; font-size: 28px; box-shadow: 0 10px 25px rgba(99, 102, 241, 0.4); cursor: pointer; transition: transform 0.2s;" onmouseover="this.style.transform='rotate(90deg)'" onmouseout="this.style.transform='rotate(0deg)'">+</div>
            </div>

            <!-- Feed -->
            <c:forEach items="${services}" var="s">
                <div class="post-card">
                    <div class="post-header">
                        <c:choose>
                            <c:when test="${not empty s.user.avatar}">
                        <img src="images/${s.user.avatar}" alt="Avatar" class="avatar-sm" style="width: 48px; height: 48px; border-radius: 14px;" onerror="this.src='images/default_avatar.png'">
                    </c:when>
                    <c:otherwise>
                        <img src="images/default_avatar.png" alt="Avatar" class="avatar-sm" style="width: 48px; height: 48px; border-radius: 14px;">
                    </c:otherwise>
                </c:choose>
                <div>
                    <a href="profile?id=${s.user.userId}" class="post-author" style="font-size: 17px; letter-spacing: -0.01em;">${s.user.fullName}</a>
                    <div class="post-time" style="font-size: 13px; opacity: 0.7; margin-top: 2px;">⚡ ${s.createdAt}</div>
                </div>
                    </div>
                    <div style="display: flex; align-items: center; gap: 12px; margin-bottom: 12px;">
                        <span class="badge ${s.type == 'request' ? 'badge-request' : 'badge-offer'}" style="padding: 6px 14px; border-radius: 10px; font-weight: 800; font-size: 10px;">
                            ${s.type == 'request' ? '🤝 NEED' : '💎 OFFER'}
                        </span>
                        <div class="post-title" style="margin-bottom: 0; font-size: 22px; letter-spacing: -0.02em;">${s.title}</div>
                    </div>
                    <div class="post-desc" style="font-size: 16px; opacity: 0.85; margin-bottom: 20px;">${s.description}</div>
                    <div class="post-price" style="font-size: 18px; font-weight: 800; color: #fbbf24; margin-bottom: 24px;">Yêu cầu: ${s.hoursRequired} Giờ</div>
                    <div class="post-actions">
                        <c:if test="${sessionScope.user.userId != s.user.userId}">
                            <a href="book?serviceId=${s.serviceId}" class="btn btn-primary" style="padding: 8px 20px;">Đặt lịch</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty services}">
                <div class="post-card" style="text-align:center; padding: 30px;">
                    Chưa có dịch vụ nào được đăng.
                </div>
            </c:if>
        </div>
        
        <!-- Right Sidebar -->
        <div class="right-sidebar">
            <%@ include file="includes/right-sidebar.jsp" %>
        </div>
    </div>
</body>
</html>

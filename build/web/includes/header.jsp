<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar" style="background: rgba(15, 23, 42, 0.6); backdrop-filter: blur(20px); border-bottom: 1px solid rgba(255,255,255,0.08); height: 80px;">
    <a href="home" class="nav-brand" style="font-size: 28px; background: var(--primary-gradient); -webkit-background-clip: text; background-clip: text; -webkit-text-fill-color: transparent; font-weight: 800;">Time Bank</a>
    <div class="nav-search">
        <form action="search" method="GET">
            <input type="text" name="q" placeholder="🔍 Khám phá dịch vụ mới..." style="background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); color: white; width: 400px; padding: 12px 24px; border-radius: 30px;">
        </form>
    </div>
    <div class="nav-user">
        <c:if test="${sessionScope.user.email == 'admin@timebank.com'}">
            <a href="admin" class="btn btn-primary" style="background: #8b5cf6; padding: 10px 24px; border-radius: 12px; font-weight: 700;">⚙️ Dashboard</a>
        </c:if>
        <c:if test="${sessionScope.user.email != 'admin@timebank.com'}">
            <a href="post" class="btn btn-primary" style="padding: 10px 24px; border-radius: 12px; font-weight: 700;">+ Đăng tin</a>
        </c:if>
        <a href="chat" style="position: relative; text-decoration: none; font-size: 22px; margin-left: 10px; transition: transform 0.2s;" onmouseover="this.style.transform='scale(1.1)'" onmouseout="this.style.transform='scale(1)'">
            💬
            <c:if test="${not empty unreadCount && unreadCount > 0}">
                <span style="position: absolute; top: -5px; right: -5px; background: #ef4444; color: white; font-size: 10px; font-weight: 800; padding: 2px 6px; border-radius: 10px; border: 2px solid #020617; box-shadow: 0 4px 8px rgba(239, 68, 68, 0.4); min-width: 20px; text-align: center;">
                    ${unreadCount}
                </span>
            </c:if>
        </a>
        <a href="profile" style="text-decoration:none; color:inherit; display:flex; align-items:center; gap:12px; padding: 8px 16px; border-radius: 16px; background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.05); transition: all 0.3s; margin-left:10px;">
            <c:choose>
                <c:when test="${not empty sessionScope.user.avatar}">
                    <img src="images/${sessionScope.user.avatar}" alt="Avatar" class="avatar-sm" style="width: 36px; height: 36px; border-radius: 10px; border: 2px solid rgba(255,255,255,0.2);">
                </c:when>
                <c:otherwise>
                    <img src="images/default_avatar.png" alt="Avatar" class="avatar-sm" style="width: 36px; height: 36px; border-radius: 10px; border: 2px solid rgba(255,255,255,0.2);">
                </c:otherwise>
            </c:choose>
            <div style="display: flex; flex-direction: column;">
                <span style="font-size: 14px; font-weight: 700; color: #fff;">${sessionScope.user.fullName}</span>
                <span style="font-size: 10px; color: var(--text-secondary); text-transform: uppercase; letter-spacing: 0.05em;">Thành viên</span>
            </div>
        </a>
        <a href="logout" style="text-decoration:none; font-size: 20px; color: var(--text-secondary); opacity: 0.6; padding: 8px; transition: all 0.3s;" title="Đăng xuất" onmouseover="this.style.opacity='1'" onmouseout="this.style.opacity='0.6'">🚪</a>
    </div>
</div>

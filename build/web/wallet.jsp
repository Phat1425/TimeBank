<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ví Thời Gian - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .wallet-header {
            background: linear-gradient(135deg, var(--primary-color), #4facfe);
            color: #fff;
            padding: 40px;
            border-radius: var(--radius);
            box-shadow: var(--shadow-sm);
            text-align: center;
            margin-bottom: 20px;
        }
        .wallet-balance {
            font-size: 48px;
            font-weight: bold;
            margin: 10px 0;
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
            <div class="wallet-header">
                <h3>Số dư khả dụng</h3>
                <div class="wallet-balance"><fmt:formatNumber value="${sessionScope.user.walletHours}" maxFractionDigits="2"/> Giờ</div>
                <p>Bạn có thể dùng quỹ thời gian này để thuê người khác làm dịch vụ cho mình.</p>
            </div>

            <h3 style="margin: 20px 0 10px;">Lịch sử giao dịch</h3>
            <div style="background:var(--card-bg); padding:15px; border-radius:var(--radius); box-shadow:var(--shadow-sm);">
                <table style="width:100%; border-collapse: collapse;">
                    <tr style="border-bottom: 1px solid var(--border-color); text-align: left;">
                        <th style="padding:10px;">Thời gian</th>
                        <th style="padding:10px;">Chi tiết</th>
                        <th style="padding:10px;">Số giờ</th>
                    </tr>
                    <c:forEach items="${transactions}" var="t">
                        <tr style="border-bottom: 1px solid var(--border-color);">
                            <td style="padding:10px;">${t.createdAt}</td>
                            <td style="padding:10px;">
                                <c:choose>
                                    <c:when test="${t.fromUser == sessionScope.user.userId}">
                                        Chuyển cho <a href="profile?id=${t.toUser}">${t.toUserObj.fullName}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Nhận từ <a href="profile?id=${t.fromUser}">${t.fromUserObj.fullName}</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="padding:10px; font-weight:bold; color:${t.fromUser == sessionScope.user.userId ? 'var(--danger-color)' : 'var(--success-color)'};">
                                ${t.fromUser == sessionScope.user.userId ? '-' : '+'}${t.hours}
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty transactions}">
                        <tr><td colspan="3" style="padding:10px; text-align:center;">Bạn chưa có giao dịch nào.</td></tr>
                    </c:if>
                </table>
            </div>

        </div>
    </div>
</body>
</html>

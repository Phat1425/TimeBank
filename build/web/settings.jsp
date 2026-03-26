<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cài Đặt - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <div class="container main-layout">
        <jsp:include page="includes/sidebar.jsp" />
        
        <div class="content">
            <h2 class="section-title">Cài Đặt Tài Khoản</h2>
            
            <c:if test="${not empty msg}">
                <div style="background: var(--success-color); color: white; padding: 12px; border-radius: 12px; margin-bottom: 20px;">
                    ✅ ${msg}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div style="background: #ef4444; color: white; padding: 12px; border-radius: 12px; margin-bottom: 20px;">
                    ❌ ${error}
                </div>
            </c:if>

            <div class="post-card" style="max-width: 600px;">
                <form action="settings" method="POST" class="auth-form" style="padding: 0; box-shadow: none;">
                    <div style="margin-bottom: 20px;">
                        <label style="display: block; margin-bottom: 8px; font-weight: 600;">Họ và tên</label>
                        <input type="text" name="fullName" value="${sessionScope.user.fullName}" required>
                    </div>
                    
                    <div style="margin-bottom: 20px;">
                        <label style="display: block; margin-bottom: 8px; font-weight: 600;">Số điện thoại</label>
                        <input type="text" name="phone" value="${sessionScope.user.phone}" required>
                    </div>
                    
                    <div style="margin-bottom: 20px;">
                        <label style="display: block; margin-bottom: 8px; font-weight: 600;">Địa chỉ</label>
                        <input type="text" name="address" value="${sessionScope.user.address}">
                    </div>
                    
                    <div style="margin-bottom: 20px;">
                        <label style="display: block; margin-bottom: 8px; font-weight: 600;">Giới tính</label>
                        <select name="gender" style="width: 100%; padding: 12px; border: 1px solid #e2e8f0; border-radius: 12px; height: auto;">
                            <option value="Nam" ${sessionScope.user.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                            <option value="Nữ" ${sessionScope.user.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                            <option value="Khác" ${sessionScope.user.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                        </select>
                    </div>
                    
                    <div style="margin-bottom: 30px;">
                        <label style="display: block; margin-bottom: 8px; font-weight: 600;">Mật khẩu mới (để trống nếu không đổi)</label>
                        <input type="password" name="password" placeholder="Nhập mật khẩu mới">
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="width: 100%; padding: 14px;">Lưu Thay Đổi</button>
                </form>
            </div>
        </div>
        
        <jsp:include page="includes/right-sidebar.jsp" />
    </div>
</body>
</html>

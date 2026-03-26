<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-container">
            <h2 style="font-weight: 800; letter-spacing: -0.05em;">Tham gia Time Bank</h2>
            <p style="margin-bottom: 30px; color: var(--text-secondary); font-weight: 500;">Bắt đầu chia sẻ giá trị thời gian của bạn</p>
            
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="error-msg"><%=error%></div>
            <%
                }
            %>
            
            <form action="register" method="POST" class="auth-form">
                <input type="text" name="fullName" placeholder="Họ và tên" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Mật khẩu" required>
                <input type="text" name="phone" placeholder="Số điện thoại" required>
                <input type="text" name="cccd" placeholder="CCCD">
                <input type="text" name="address" placeholder="Địa chỉ">
                
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 8px; font-size: 14px; font-weight: 600; color: var(--text-primary);">Giới tính</label>
                    <select name="gender" style="width: 100%; padding: 12px; border: 1px solid #e2e8f0; border-radius: 12px; background: white;">
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary" style="padding: 14px; border-radius: 12px; font-weight: 700;">Tạo tài khoản</button>
            </form>
            
            <div class="auth-divider"></div>
            
            <a href="login" class="btn btn-primary">Đã có tài khoản?</a>
        </div>
    </div>
</body>
</html>

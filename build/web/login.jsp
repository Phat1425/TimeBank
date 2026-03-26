<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-container">
            <h2 style="font-weight: 800; letter-spacing: -0.05em;">Time Bank</h2>
            <p style="margin-bottom: 30px; color: var(--text-secondary); font-weight: 500;">Kết nối cộng đồng qua giá trị thời gian</p>
            
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="error-msg"><%=error%></div>
            <%
                }
            %>
            <%
                String msg = request.getParameter("msg");
                if ("reset_success".equals(msg)) {
            %>
                <div style="background:#d4edda; color:#155724; padding:10px; border-radius:6px; margin-bottom:15px; font-size:14px;">
                    ✅ Mật khẩu đã được đặt lại thành công. Hãy đăng nhập lại!
                </div>
            <%
                }
            %>
            
            <form action="login" method="POST" class="auth-form" autocomplete="off">
                <input type="email" name="email" placeholder="Email của bạn" required autocomplete="off">
                <input type="password" name="password" placeholder="Mật khẩu" required autocomplete="new-password">
                <button type="submit" class="btn btn-primary">Đăng Nhập</button>
            </form>
            
            <div style="margin-top: 24px; text-align: left; background: #f1f5f9; padding: 16px; border-radius: 12px; font-size: 13px; color: #475569; border: 1px dashed #cbd5e1;">
                <strong style="color: var(--primary-color);">💡 Tài khoản thử nghiệm:</strong><br>
                <div style="margin-top: 8px;">1️⃣ a@gmail.com / 123 (Khánh Vy)</div>
                <div style="margin-top: 4px;">2️⃣ b@gmail.com / 123 (Thị Nhàn)</div>
            </div>
            
            <div style="text-align: right; margin-top: 8px;">
                <a href="forgot-password" style="font-size: 13px; color: var(--primary-color); text-decoration: none;">Quên mật khẩu?</a>
            </div>

            <div class="auth-divider"></div>
            
            <a href="register" class="btn btn-success" style="width: 100%; border-radius: 12px; background: var(--success-color); padding: 14px;">Tạo tài khoản mới</a>
        </div>
    </div>
</body>
</html>

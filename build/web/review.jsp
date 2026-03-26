<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đánh Giá Dịch Vụ - Time Bank</title>
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
            <div style="background:var(--card-bg); padding:30px; border-radius:var(--radius); box-shadow:var(--shadow-sm);">
                <h2>Đánh Giá Dịch Vụ</h2>
                <p>Cảm ơn bạn đã sử dụng dịch vụ. Hãy đánh giá để giúp cộng đồng tốt hơn.</p>
                <form action="review" method="POST" class="auth-form" style="margin-top:20px;">
                    <input type="hidden" name="bookingId" value="${booking.bookingId}">
                    
                    <label style="font-weight:bold;">Đánh giá sao (1-5):</label>
                    <select name="rating" style="padding:10px; border-radius:6px; border:1px solid var(--border-color);">
                        <option value="5">⭐⭐⭐⭐⭐ Tuyệt vời</option>
                        <option value="4">⭐⭐⭐⭐ Rất tốt</option>
                        <option value="3">⭐⭐⭐ Tạm được</option>
                        <option value="2">⭐⭐ Kém</option>
                        <option value="1">⭐ Tồi tệ</option>
                    </select>
                    
                    <label style="font-weight:bold; margin-top:10px;">Nhận xét:</label>
                    <textarea name="comment" rows="4" placeholder="Viết chia sẻ của bạn..." required></textarea>
                    
                    <button type="submit" class="btn btn-primary" style="margin-top:10px;">Gửi Đánh Giá</button>
                    <a href="bookings" class="btn" style="background:#e4e6eb; color:#000; text-align:center;">Bỏ qua</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

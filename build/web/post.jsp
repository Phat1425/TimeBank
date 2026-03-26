<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Dịch Vụ - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .post-form-container {
            background: var(--card-bg);
            padding: 30px;
            border-radius: var(--radius);
            box-shadow: var(--shadow-sm);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-size: 16px;
            outline: none;
            transition: border-color 0.2s;
        }
        .form-group input:focus, .form-group textarea:focus {
            border-color: var(--primary-color);
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
            <div class="post-form-container">
                <h2 style="margin-bottom: 20px;">Đăng Dịch Vụ Mới</h2>
                <c:if test="${not empty error}">
                    <div class="error-msg">${error}</div>
                </c:if>
                <form action="post" method="POST">
                    <div class="form-group">
                        <label>Tôi muốn...</label>
                        <select name="type" style="width: 100%; padding: 12px; border: 1px solid var(--border-color); border-radius: 6px; font-size: 16px;">
                            <option value="offer">💎 Chia sẻ kỹ năng (Dịch vụ cung cấp)</option>
                            <option value="request">🤝 Cần giúp đỡ (Yêu cầu dịch vụ)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Tiêu đề ngắn gọn</label>
                        <input type="text" name="title" placeholder="VD: Sửa máy tính, Học tiếng Anh..." required>
                    </div>
                    <div class="form-group">
                        <label>Mô tả chi tiết</label>
                        <textarea name="description" rows="5" placeholder="Mô tả công việc bạn có thể làm..." required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Số giờ yêu cầu (Credit)</label>
                        <input type="number" step="0.5" min="0.5" name="hoursRequired" placeholder="VD: 1.5" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="width:100%;">Đăng bài</button>
                    <a href="home" class="btn" style="display:block; text-align:center; margin-top:10px; background:#e4e6eb; color:#000;">Hủy</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

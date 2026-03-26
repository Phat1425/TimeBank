<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body { background: #f0f2f5; }

        .admin-wrapper {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }

        .admin-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background: linear-gradient(135deg, #1877f2, #0d5ec4);
            color: #fff;
            padding: 20px 30px;
            border-radius: 12px;
            margin-bottom: 25px;
            box-shadow: 0 4px 15px rgba(24,119,242,0.3);
        }

        .admin-header h1 { font-size: 24px; margin: 0; }
        .admin-header p { margin: 4px 0 0; opacity: 0.85; font-size: 14px; }
        .admin-header a { color: #fff; text-decoration: none; background: rgba(255,255,255,0.2);
                          padding: 8px 16px; border-radius: 6px; font-size: 13px; }
        .admin-header a:hover { background: rgba(255,255,255,0.3); }

        /* Stats Cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 18px;
            margin-bottom: 25px;
        }

        .stat-card {
            background: #fff;
            border-radius: 12px;
            padding: 22px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .stat-card:hover { transform: translateY(-3px); box-shadow: 0 6px 20px rgba(0,0,0,0.1); }
        .stat-icon { font-size: 36px; margin-bottom: 8px; }
        .stat-value { font-size: 32px; font-weight: bold; color: #1877f2; }
        .stat-label { color: #65676b; font-size: 14px; margin-top: 4px; }

        .stat-card.green .stat-value { color: #42b72a; }
        .stat-card.orange .stat-value { color: #f5a623; }
        .stat-card.purple .stat-value { color: #8b5cf6; }

        /* Section card */
        .section-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
            margin-bottom: 25px;
            overflow: hidden;
        }
        .section-header {
            padding: 16px 20px;
            border-bottom: 1px solid #dadde1;
            font-size: 16px;
            font-weight: 600;
            color: #050505;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* Table */
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #f0f2f5; font-size: 14px; }
        th { background: #f8f9fa; font-weight: 600; color: #65676b; text-transform: uppercase; font-size: 12px; letter-spacing: 0.5px; }
        tr:hover td { background: #f8f9fb; }

        .badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        .badge-pending  { background: #fff3cd; color: #856404; }
        .badge-accepted { background: #cce5ff; color: #004085; }
        .badge-completed{ background: #d4edda; color: #155724; }
        .badge-canceled { background: #f8d7da; color: #721c24; }

        .btn-danger-sm {
            padding: 4px 10px;
            background: #e41e3f;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 12px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-danger-sm:hover { background: #c01530; }

        .alert-success {
            background: #d4edda;
            color: #155724;
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        @media (max-width: 900px) {
            .stats-grid { grid-template-columns: repeat(2, 1fr); }
        }
        @media (max-width: 550px) {
            .stats-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>
<div class="admin-wrapper">

    <!-- Header -->
    <div class="admin-header">
        <div>
            <h1>⚙️ Admin Dashboard</h1>
            <p>Xin chào, ${sessionScope.user.fullName} — Quản trị viên Time Bank</p>
        </div>
        <a href="home">← Về trang chủ</a>
    </div>

    <!-- Alert -->
    <c:if test="${param.msg == 'deleted'}">
        <div class="alert-success">✅ Đã xóa tài khoản người dùng thành công.</div>
    </c:if>

    <!-- Stats -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon">👥</div>
            <div class="stat-value">${totalUsers}</div>
            <div class="stat-label">Người dùng</div>
        </div>
        <div class="stat-card green">
            <div class="stat-icon">📋</div>
            <div class="stat-value">${totalServices}</div>
            <div class="stat-label">Dịch vụ đăng</div>
        </div>
        <div class="stat-card orange">
            <div class="stat-icon">📅</div>
            <div class="stat-value">${totalBookings}</div>
            <div class="stat-label">Lịch đặt</div>
        </div>
        <div class="stat-card purple">
            <div class="stat-icon">⏱️</div>
            <div class="stat-value"><fmt:formatNumber value="${totalHours}" maxFractionDigits="1"/></div>
            <div class="stat-label">Giờ giao dịch</div>
        </div>
    </div>

    <!-- User Management -->
    <div class="section-card">
        <div class="section-header">👥 Danh Sách Người Dùng</div>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>SĐT</th>
                    <th>Giới tính</th>
                    <th>Địa chỉ</th>
                    <th>Số dư (giờ)</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${allUsers}" var="u">
                    <tr>
                        <td>${u.userId}</td>
                        <td>
                            <a href="profile?id=${u.userId}" style="color:#1877f2; text-decoration:none; font-weight:500;">
                                ${u.fullName}
                            </a>
                        </td>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>${u.gender}</td>
                        <td>${u.address}</td>
                        <td><strong style="color:#42b72a;">${u.walletHours}</strong></td>
                        <td>
                            <a href="admin?action=delete&userId=${u.userId}"
                               class="btn-danger-sm"
                               onclick="return confirm('Xóa tài khoản ${u.fullName}?\nThao tác này không thể hoàn tác!');">
                               🗑️ Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty allUsers}">
                    <tr><td colspan="7" style="text-align:center; padding:20px; color:#65676b;">Không có người dùng nào.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <!-- Recent Bookings -->
    <div class="section-card">
        <div class="section-header">📅 Booking Gần Đây (10 mới nhất)</div>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Dịch vụ</th>
                    <th>Người thuê</th>
                    <th>Người làm</th>
                    <th>Thời gian</th>
                    <th>Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${recentBookings}" var="b">
                    <tr>
                        <td>${b[0]}</td>
                        <td><strong>${b[3]}</strong></td>
                        <td>${b[1]}</td>
                        <td>${b[2]}</td>
                        <td style="font-size:13px; color:#65676b;">${b[4]}</td>
                        <td>
                            <c:if test="${b[5] == 'pending'}">  <span class="badge badge-pending">Chờ nhận</span></c:if>
                            <c:if test="${b[5] == 'accepted'}"> <span class="badge badge-accepted">Đang làm</span></c:if>
                            <c:if test="${b[5] == 'completed'}"><span class="badge badge-completed">Hoàn thành</span></c:if>
                            <c:if test="${b[5] == 'canceled'}"> <span class="badge badge-canceled">Đã hủy</span></c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty recentBookings}">
                    <tr><td colspan="6" style="text-align:center; padding:20px; color:#65676b;">Chưa có booking nào.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>

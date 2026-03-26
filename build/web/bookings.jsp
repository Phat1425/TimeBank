<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch Đặt Của Tôi - Time Bank</title>
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
            <h2 style="margin-bottom: 20px;">Lịch Đặt Của Tôi</h2>

            <c:if test="${not empty param.msg}">
                <div style="background:#d4edda; color:#155724; padding:10px; border-radius:6px; margin-bottom:15px;">
                    ${param.msg}
                </div>
            </c:if>

            <h3 style="margin: 20px 0 10px;">Dịch vụ tôi đặt (Đi thuê)</h3>
            <div style="background:var(--card-bg); padding:15px; border-radius:var(--radius); box-shadow:var(--shadow-sm);">
                <table style="width:100%; border-collapse: collapse;">
                    <tr style="border-bottom: 1px solid var(--border-color); text-align: left;">
                        <th style="padding:10px;">Dịch vụ</th>
                        <th style="padding:10px;">Người cung cấp</th>
                        <th style="padding:10px;">Thời gian</th>
                        <th style="padding:10px;">Trạng thái</th>
                        <th style="padding:10px;">Hành động</th>
                    </tr>
                    <c:forEach items="${requestedBookings}" var="b">
                        <tr style="border-bottom: 1px solid var(--border-color);">
                            <td style="padding:10px;">${b.service.title}</td>
                            <td style="padding:10px;">
                                <a href="profile?id=${b.providerId}">${b.provider.fullName}</a>
                            </td>
                            <td style="padding:10px;">${b.scheduleTime}</td>
                            <td style="padding:10px;">
                                <c:if test="${b.status == 'pending'}"><span style="color:#ffc107; font-weight:bold;">Đang chờ nhận</span></c:if>
                                <c:if test="${b.status == 'accepted'}"><span style="color:#17a2b8; font-weight:bold;">Đang tiến hành</span></c:if>
                                <c:if test="${b.status == 'completed'}"><span style="color:var(--success-color); font-weight:bold;">Đã hoàn thành</span></c:if>
                                <c:if test="${b.status == 'canceled'}"><span style="color:var(--danger-color); font-weight:bold;">Đã hủy</span></c:if>
                            </td>
                            <td style="padding:10px;">
                                <c:if test="${b.status == 'pending' || b.status == 'accepted'}">
                                    <form action="updateBooking" method="POST" style="display:inline;">
                                        <input type="hidden" name="bookingId" value="${b.bookingId}">
                                        <input type="hidden" name="action" value="complete">
                                        <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size:12px;" onclick="return confirm('Bạn xác nhận dịch vụ đã xong? Tiền sẽ được chuyển cho đối tác (trừ 5% phí).');">Hoàn thành</button>
                                    </form>
                                    <form action="updateBooking" method="POST" style="display:inline;">
                                        <input type="hidden" name="bookingId" value="${b.bookingId}">
                                        <input type="hidden" name="action" value="cancel">
                                        <button type="submit" class="btn btn-danger" style="padding: 5px 10px; font-size:12px;" onclick="return confirm('Hủy dịch vụ và nhận lại tiền cọc?');">Hủy</button>
                                    </form>
                                </c:if>
                                <c:if test="${b.status == 'completed'}">
                                    <a href="review?bookingId=${b.bookingId}" class="btn btn-primary" style="padding: 5px 10px; font-size:12px;">Đánh giá</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty requestedBookings}">
                        <tr><td colspan="5" style="padding:10px; text-align:center;">Bạn chưa đặt dịch vụ nào.</td></tr>
                    </c:if>
                </table>
            </div>

            <h3 style="margin: 30px 0 10px;">Dịch vụ tôi cung cấp (Nhận làm)</h3>
            <div style="background:var(--card-bg); padding:15px; border-radius:var(--radius); box-shadow:var(--shadow-sm);">
                <table style="width:100%; border-collapse: collapse;">
                    <tr style="border-bottom: 1px solid var(--border-color); text-align: left;">
                        <th style="padding:10px;">Dịch vụ</th>
                        <th style="padding:10px;">Người thuê</th>
                        <th style="padding:10px;">Thời gian</th>
                        <th style="padding:10px;">Trạng thái</th>
                        <th style="padding:10px;">Hành động</th>
                    </tr>
                    <c:forEach items="${providedBookings}" var="p">
                        <tr style="border-bottom: 1px solid var(--border-color);">
                            <td style="padding:10px;">${p.service.title}</td>
                            <td style="padding:10px;">
                                <a href="profile?id=${p.requesterId}">${p.requester.fullName}</a>
                            </td>
                            <td style="padding:10px;">${p.scheduleTime}</td>
                            <td style="padding:10px;">
                                <c:if test="${p.status == 'pending'}"><span style="color:#ffc107; font-weight:bold;">Khách đang đợi nhận</span></c:if>
                                <c:if test="${p.status == 'accepted'}"><span style="color:#17a2b8; font-weight:bold;">Đang thực hiện</span></c:if>
                                <c:if test="${p.status == 'completed'}"><span style="color:var(--success-color); font-weight:bold;">Đã hoàn thành</span></c:if>
                                <c:if test="${p.status == 'canceled'}"><span style="color:var(--danger-color); font-weight:bold;">Đã bị hủy</span></c:if>
                            </td>
                            <td style="padding:10px;">
                                <c:if test="${p.status == 'pending'}">
                                    <form action="updateBooking" method="POST" style="display:inline;">
                                        <input type="hidden" name="bookingId" value="${p.bookingId}">
                                        <input type="hidden" name="action" value="accept">
                                        <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size:12px;">Nhận & Làm</button>
                                    </form>
                                    <form action="updateBooking" method="POST" style="display:inline;">
                                        <input type="hidden" name="bookingId" value="${p.bookingId}">
                                        <input type="hidden" name="action" value="cancel">
                                        <button type="submit" class="btn btn-danger" style="padding: 5px 10px; font-size:12px;">Từ chối</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty providedBookings}">
                        <tr><td colspan="4" style="padding:10px; text-align:center;">Bạn chưa nhận dịch vụ nào.</td></tr>
                    </c:if>
                </table>
            </div>

        </div>
    </div>
</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tin Nhắn - Time Bank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .chat-container {
            display: flex;
            background: var(--card-bg);
            border-radius: var(--radius);
            box-shadow: var(--shadow-sm);
            height: 70vh;
            overflow: hidden;
        }
        .chat-sidebar {
            width: 300px;
            border-right: 1px solid var(--border-color);
            overflow-y: auto;
        }
        .chat-user-item {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 15px;
            text-decoration: none;
            color: var(--text-primary);
            border-bottom: 1px solid var(--border-color);
            transition: background 0.2s;
        }
        .chat-user-item:hover, .chat-user-item.active {
            background: #f0f2f5;
        }
        .chat-main {
            flex: 1;
            display: flex;
            flex-direction: column;
        }
        .chat-header {
            padding: 15px;
            border-bottom: 1px solid var(--border-color);
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .chat-messages {
            flex: 1;
            padding: 15px;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
            gap: 10px;
            background: #e4e6eb; /* messenger style bg */
        }
        .msg-bubble {
            max-width: 60%;
            padding: 10px 15px;
            border-radius: 20px;
            font-size: 15px;
        }
        .msg-sent {
            align-self: flex-end;
            background: var(--primary-color);
            color: #fff;
            border-bottom-right-radius: 5px;
        }
        .msg-received {
            align-self: flex-start;
            background: #fff;
            color: #000;
            border-bottom-left-radius: 5px;
        }
        .chat-input-area {
            padding: 15px;
            border-top: 1px solid var(--border-color);
            display: flex;
            gap: 10px;
            background: #fff;
        }
        .chat-input-area input {
            flex: 1;
            padding: 10px 15px;
            border: 1px solid var(--border-color);
            border-radius: 20px;
            outline: none;
        }
    </style>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    
    <div class="main-container" style="max-width: 1000px; margin: 80px auto;">
        <div class="chat-container">
            <!-- Chat Users Sidebar -->
            <div class="chat-sidebar">
                <h4 style="padding: 15px; border-bottom: 1px solid var(--border-color);">Đoạn chat</h4>
                <c:forEach items="${chattedUsers}" var="u">
                    <a href="chat?to=${u.userId}" class="chat-user-item ${not empty toUser and toUser.userId == u.userId ? 'active' : ''}">
                        <c:choose>
                            <c:when test="${not empty u.avatar}">
                                <img src="images/${u.avatar}" class="avatar-sm">
                            </c:when>
                            <c:otherwise>
                                <img src="images/default_avatar.png" class="avatar-sm">
                            </c:otherwise>
                        </c:choose>
                        <div style="flex: 1; display: flex; justify-content: space-between; align-items: center;">
                            <span>${u.fullName}</span>
                            <c:if test="${unreadCounts[u.userId] > 0}">
                                <span style="background: #ef4444; color: white; font-size: 10px; font-weight: 800; padding: 2px 6px; border-radius: 10px; min-width: 18px; text-align: center;">
                                    ${unreadCounts[u.userId]}
                                </span>
                            </c:if>
                        </div>
                    </a>
                </c:forEach>
                <c:if test="${empty chattedUsers}">
                    <div style="padding: 15px; color: var(--text-secondary); text-align: center;">Chưa có đoạn chat nào.</div>
                </c:if>
            </div>
            
            <!-- Chat Content -->
            <div class="chat-main">
                <c:if test="${empty toUser}">
                    <div style="flex:1; display:flex; align-items:center; justify-content:center; color:var(--text-secondary);">
                        Chọn một đoạn chat để bắt đầu nhắn tin.
                    </div>
                </c:if>
                <c:if test="${not empty toUser}">
                    <div class="chat-header">
                        <c:choose>
                            <c:when test="${not empty toUser.avatar}">
                                <img src="images/${toUser.avatar}" class="avatar-sm">
                            </c:when>
                            <c:otherwise>
                                <img src="images/default_avatar.png" class="avatar-sm">
                            </c:otherwise>
                        </c:choose>
                        ${toUser.fullName}
                    </div>
                    <div class="chat-messages" id="chatMessages">
                        <c:forEach items="${conversation}" var="msg">
                            <div class="msg-bubble ${msg.senderId == sessionScope.user.userId ? 'msg-sent' : 'msg-received'}">
                                ${msg.content}
                            </div>
                        </c:forEach>
                    </div>
                    <form action="chat" method="POST" class="chat-input-area">
                        <input type="hidden" name="receiverId" value="${toUser.userId}">
                        <input type="text" name="content" placeholder="Nhập tin nhắn..." required autocomplete="off" autofocus>
                        <button type="submit" class="btn btn-primary" style="border-radius:20px;">Gửi</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    
    <script>
        // Scroll to bottom of chat
        var chatBox = document.getElementById("chatMessages");
        if (chatBox) {
            chatBox.scrollTop = chatBox.scrollHeight;
        }
    </script>
</body>
</html>

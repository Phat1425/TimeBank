<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, utils.DBContext" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>DB Debug</title>
<style>body{font-family:monospace;padding:20px;} table{border-collapse:collapse;} td,th{border:1px solid #ccc;padding:8px;}</style>
</head>
<body>
<h2>🔍 Debug: Kiểm tra tài khoản trong DB</h2>
<%
    try {
        Connection conn = new DBContext().getConnection();
        out.println("<p style='color:green'>✅ Kết nối DB thành công!</p>");
        
        PreparedStatement ps = conn.prepareStatement("SELECT user_id, full_name, email, password FROM Users");
        ResultSet rs = ps.executeQuery();
        out.println("<table><tr><th>ID</th><th>Tên</th><th>Email</th><th>Password</th></tr>");
        while(rs.next()) {
            out.println("<tr><td>" + rs.getInt(1) + "</td><td>" + rs.getString(2) 
                + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td></tr>");
        }
        out.println("</table>");
        rs.close(); ps.close(); conn.close();
    } catch(Exception e) {
        out.println("<p style='color:red'>❌ Lỗi: " + e.getMessage() + "</p>");
    }
%>
<hr>
<h2>🔍 Test Login Query</h2>
<%
    String testEmail = "admin@timebank.com";
    String testPass  = "adminpass";
    try {
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");
        ps.setString(1, testEmail);
        ps.setString(2, testPass);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            out.println("<p style='color:green'>✅ Admin login query thành công! ID = " + rs.getInt("user_id") + "</p>");
        } else {
            out.println("<p style='color:red'>❌ Không tìm thấy admin với email='" + testEmail + "' và password='" + testPass + "'</p>");
        }
        rs.close(); ps.close(); conn.close();
    } catch(Exception e) {
        out.println("<p style='color:red'>❌ Lỗi query: " + e.getMessage() + "</p>");
    }
%>
<hr>
<a href="login">← Về trang đăng nhập</a>
</body>
</html>

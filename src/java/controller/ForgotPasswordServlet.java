package controller;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String step = request.getParameter("step");
        UserDAO dao = new UserDAO();

        if ("verify".equals(step)) {
            // Bước 1: Kiểm tra email tồn tại
            String email = request.getParameter("email");
            User user = dao.getUserByEmail(email);
            if (user == null) {
                request.setAttribute("error", "Email này chưa được đăng ký trong hệ thống!");
                request.setAttribute("step", "verify");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            } else {
                // Email hợp lệ → chuyển sang bước nhập mật khẩu mới
                request.setAttribute("email", email);
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }

        } else if ("reset".equals(step)) {
            // Bước 2: Đặt lại mật khẩu
            String email = request.getParameter("email");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword == null || newPassword.trim().isEmpty()) {
                request.setAttribute("error", "Mật khẩu mới không được để trống!");
                request.setAttribute("email", email);
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                request.setAttribute("email", email);
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }

            if (dao.updatePassword(email, newPassword)) {
                response.sendRedirect("login?msg=reset_success");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
                request.setAttribute("email", email);
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        }
    }
}

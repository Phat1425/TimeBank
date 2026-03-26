package controller;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "SettingsServlet", urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");

        currentUser.setFullName(fullName);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        currentUser.setGender(gender);
        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(password);
        }

        UserDAO dao = new UserDAO();
        // We need an update method in UserDAO
        if (dao.updateUser(currentUser)) {
            session.setAttribute("user", currentUser);
            request.setAttribute("msg", "Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        }
        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }
}

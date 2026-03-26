package controller;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String cccd = request.getParameter("cccd");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String avatar = "default_avatar.png"; 

        User u = new User(0, fullName, email, password, phone, cccd, address, avatar, gender, 2.0);
        UserDAO dao = new UserDAO();
        if(dao.register(u)) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Email đã tồn tại hoặc lỗi hệ thống!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

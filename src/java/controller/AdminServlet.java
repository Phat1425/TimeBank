package controller;

import dao.AdminDAO;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User u = (User) session.getAttribute("user");
        return u != null && "admin@timebank.com".equals(u.getEmail());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect("home");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            AdminDAO adminDAO = new AdminDAO();
            adminDAO.deleteUser(userId);
            response.sendRedirect("admin?msg=deleted");
            return;
        }

        // Load tất cả dữ liệu cho dashboard
        AdminDAO adminDAO = new AdminDAO();
        request.setAttribute("totalUsers", adminDAO.getTotalUsers());
        request.setAttribute("totalServices", adminDAO.getTotalServices());
        request.setAttribute("totalBookings", adminDAO.getTotalBookings());
        request.setAttribute("totalHours", adminDAO.getTotalTransactionHours());
        request.setAttribute("allUsers", adminDAO.getAllUsers());
        request.setAttribute("recentBookings", adminDAO.getRecentBookings(10));

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

package controller;

import dao.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Service;
import model.User;

@WebServlet(name = "PostServiceServlet", urlPatterns = {"/post"})
public class PostServiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double hoursRequired = Double.parseDouble(request.getParameter("hoursRequired"));
        String type = request.getParameter("type"); // offer or request

        Service s = new Service();
        s.setUserId(user.getUserId());
        s.setTitle(title);
        s.setDescription(description);
        s.setHoursRequired(hoursRequired);
        s.setType(type);

        ServiceDAO dao = new ServiceDAO();
        if (dao.createService(s)) {
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Không thể đăng dịch vụ, vui lòng thử lại!");
            request.getRequestDispatcher("post.jsp").forward(request, response);
        }
    }
}

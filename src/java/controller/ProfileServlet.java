package controller;

import dao.ReviewDAO;
import dao.ServiceDAO;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Review;
import model.Service;
import model.User;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");
        
        String idParam = request.getParameter("id");
        int targetUserId = -1;
        
        if (idParam != null && !idParam.isEmpty()) {
            targetUserId = Integer.parseInt(idParam);
        } else if (loggedInUser != null) {
            targetUserId = loggedInUser.getUserId();
        } else {
            response.sendRedirect("login");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User targetUser = userDAO.getUserById(targetUserId);

        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> services = serviceDAO.getServicesByUser(targetUserId);

        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = reviewDAO.getReviewsByProvider(targetUserId);
        
        // Calculate average rating
        double avgRating = 0;
        if (!reviews.isEmpty()) {
            double total = 0;
            for (Review r : reviews) {
                total += r.getRating();
            }
            avgRating = total / reviews.size();
        }

        request.setAttribute("targetUser", targetUser);
        request.setAttribute("services", services);
        request.setAttribute("reviews", reviews);
        request.setAttribute("avgRating", avgRating);
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}

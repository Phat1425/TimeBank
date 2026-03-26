package controller;

import dao.BookingDAO;
import dao.ReviewDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.Review;
import model.User;

@WebServlet(name = "ReviewServlet", urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        BookingDAO bDao = new BookingDAO();
        Booking b = bDao.getBookingById(bookingId);
        
        // Prevent unauthorized reviews
        if (b == null || b.getRequesterId() != user.getUserId() || !"completed".equals(b.getStatus())) {
            response.sendRedirect("bookings");
            return;
        }
        
        // Check if already reviewed
        ReviewDAO rDao = new ReviewDAO();
        if (rDao.getReviewByBooking(bookingId) != null) {
            response.sendRedirect("bookings?msg=Review_already_submitted");
            return;
        }

        request.setAttribute("booking", b);
        request.getRequestDispatcher("review.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review r = new Review();
        r.setBookingId(bookingId);
        r.setRating(rating);
        r.setComment(comment);

        ReviewDAO rDao = new ReviewDAO();
        rDao.createReview(r);

        response.sendRedirect("bookings?msg=Review_success");
    }
}

package controller;

import dao.BookingDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.User;

@WebServlet(name = "MyBookingsServlet", urlPatterns = {"/bookings"})
public class MyBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        BookingDAO bDao = new BookingDAO();
        List<Booking> requestedBookings = bDao.getBookingsByRequester(user.getUserId());
        List<Booking> providedBookings = bDao.getBookingsByProvider(user.getUserId());

        request.setAttribute("requestedBookings", requestedBookings);
        request.setAttribute("providedBookings", providedBookings);

        request.getRequestDispatcher("bookings.jsp").forward(request, response);
    }
}

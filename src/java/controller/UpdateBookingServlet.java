package controller;

import dao.BookingDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import model.Booking;
import model.Transaction;
import model.User;

@WebServlet(name = "UpdateBookingServlet", urlPatterns = {"/updateBooking"})
public class UpdateBookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String action = request.getParameter("action");

        BookingDAO bDao = new BookingDAO();
        Booking b = bDao.getBookingById(bookingId);

        if (b == null) {
            response.sendRedirect("bookings");
            return;
        }

        UserDAO uDao = new UserDAO();
        double amount = b.getService().getHoursRequired();

        if ("complete".equals(action) && b.getRequesterId() == user.getUserId() && ("pending".equals(b.getStatus()) || "accepted".equals(b.getStatus()))) {
            // Tiền đã bị trừ lúc đặt lịch (Escrow). Giờ chuyển cho provider (trừ 5% phí nền tảng)
            double fee = amount * 0.05;
            double receivedAmount = amount - fee;

            uDao.updateWallet(b.getProviderId(), receivedAmount);
            bDao.updateStatus(bookingId, "completed");

            TransactionDAO tDao = new TransactionDAO();
            Transaction t = new Transaction();
            t.setFromUser(b.getRequesterId());
            t.setToUser(b.getProviderId());
            t.setHours(receivedAmount);
            tDao.createTransaction(t);
            
            User admin = uDao.getAdminUser();
            if (admin != null) {
                uDao.updateWallet(admin.getUserId(), fee);
                Transaction tAdmin = new Transaction();
                tAdmin.setFromUser(b.getRequesterId());
                tAdmin.setToUser(admin.getUserId());
                tAdmin.setHours(fee);
                tDao.createTransaction(tAdmin);
            }
            
            response.sendRedirect("review?bookingId=" + bookingId);

        } else if ("accept".equals(action) && b.getProviderId() == user.getUserId() && "pending".equals(b.getStatus())) {
            bDao.updateStatus(bookingId, "accepted");
            response.sendRedirect("bookings");

        } else if ("cancel".equals(action) && ("pending".equals(b.getStatus()) || "accepted".equals(b.getStatus()))) {
            // Hoàn tiền (Refund Escrow)
            uDao.updateWallet(b.getRequesterId(), amount);
            bDao.updateStatus(bookingId, "canceled");
            
            if (b.getRequesterId() == user.getUserId()) {
                User freshRequester = uDao.getUserById(user.getUserId());
                session.setAttribute("user", freshRequester);
            }
            response.sendRedirect("bookings?msg=" + URLEncoder.encode("Đã hủy và hoàn lại " + amount + " giờ.", "UTF-8"));

        } else {
            response.sendRedirect("bookings");
        }
    }
}

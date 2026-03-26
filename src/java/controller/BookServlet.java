package controller;

import dao.BookingDAO;
import dao.ServiceDAO;
import dao.UserDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.Service;
import model.User;

@WebServlet(name = "BookServlet", urlPatterns = {"/book"})
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String serviceIdStr = request.getParameter("serviceId");
        if (serviceIdStr == null || serviceIdStr.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int serviceId = Integer.parseInt(serviceIdStr);
        ServiceDAO sDao = new ServiceDAO();
        Service service = sDao.getServiceById(serviceId);

        if (service == null) {
            response.sendRedirect("home");
            return;
        }

        request.setAttribute("service", service);
        request.getRequestDispatcher("book.jsp").forward(request, response);
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

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        int providerId = Integer.parseInt(request.getParameter("providerId"));
        String timeStr = request.getParameter("scheduleTime"); // "yyyy-MM-dd'T'HH:mm"

        ServiceDAO sDao = new ServiceDAO();
        Service service = sDao.getServiceById(serviceId);

        if (user.getWalletHours() < service.getHoursRequired()) {
            request.setAttribute("error", "Số dư ví không đủ để đặt dịch vụ này!");
            request.setAttribute("service", service);
            request.getRequestDispatcher("book.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date scheduleTime = sdf.parse(timeStr);

            Booking b = new Booking();
            b.setServiceId(serviceId);
            b.setRequesterId(user.getUserId());
            b.setProviderId(providerId);
            b.setScheduleTime(scheduleTime);

            BookingDAO bDao = new BookingDAO();
            int bookingId = bDao.createBooking(b);
            if (bookingId > 0) {
                // Escrow: Trừ điểm ngay lập tức giữ trong nền tảng
                UserDAO uDao = new UserDAO();
                uDao.updateWallet(user.getUserId(), -service.getHoursRequired());
                
                user.setWalletHours(user.getWalletHours() - service.getHoursRequired());
                session.setAttribute("user", user);

                response.sendRedirect("bookings");
            } else {
                request.setAttribute("error", "Đặt lịch thất bại, vui lòng thử lại!");
                request.setAttribute("service", service);
                request.getRequestDispatcher("book.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Định dạng thời gian không hợp lệ!");
            request.setAttribute("service", service);
            request.getRequestDispatcher("book.jsp").forward(request, response);
        }
    }
}

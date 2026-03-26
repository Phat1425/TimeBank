package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.Service;
import model.User;
import utils.DBContext;

public class BookingDAO {

    public int createBooking(Booking b) {
        String query = "INSERT INTO Bookings(service_id, requester_id, provider_id, schedule_time, status) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getServiceId());
            ps.setInt(2, b.getRequesterId());
            ps.setInt(3, b.getProviderId());
            ps.setTimestamp(4, new java.sql.Timestamp(b.getScheduleTime().getTime()));
            ps.setString(5, "pending");
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateStatus(int bookingId, String status) {
        String query = "UPDATE Bookings SET status = ? WHERE booking_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getBookingsByRequester(int requesterId) {
        List<Booking> list = new ArrayList<>();
        String query = "SELECT b.*, s.title, u.full_name AS provider_name FROM Bookings b JOIN Services s ON b.service_id = s.service_id JOIN Users u ON b.provider_id = u.user_id WHERE b.requester_id = ? ORDER BY b.schedule_time DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, requesterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("service_id"),
                        rs.getInt("requester_id"),
                        rs.getInt("provider_id"),
                        rs.getTimestamp("schedule_time"),
                        rs.getString("status")
                    );
                    Service s = new Service();
                    s.setTitle(rs.getString("title"));
                    b.setService(s);
                    
                    User provider = new User();
                    provider.setFullName(rs.getString("provider_name"));
                    b.setProvider(provider);
                    
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Booking> getBookingsByProvider(int providerId) {
        List<Booking> list = new ArrayList<>();
        String query = "SELECT b.*, s.title, u.full_name AS requester_name FROM Bookings b JOIN Services s ON b.service_id = s.service_id JOIN Users u ON b.requester_id = u.user_id WHERE b.provider_id = ? ORDER BY b.schedule_time DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("service_id"),
                        rs.getInt("requester_id"),
                        rs.getInt("provider_id"),
                        rs.getTimestamp("schedule_time"),
                        rs.getString("status")
                    );
                    Service s = new Service();
                    s.setTitle(rs.getString("title"));
                    b.setService(s);
                    
                    User requester = new User();
                    requester.setFullName(rs.getString("requester_name"));
                    b.setRequester(requester);
                    
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Booking getBookingById(int bookingId) {
        String query = "SELECT b.*, s.hours_required FROM Bookings b JOIN Services s ON b.service_id = s.service_id WHERE b.booking_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("service_id"),
                        rs.getInt("requester_id"),
                        rs.getInt("provider_id"),
                        rs.getTimestamp("schedule_time"),
                        rs.getString("status")
                    );
                    Service s = new Service();
                    s.setHoursRequired(rs.getDouble("hours_required"));
                    b.setService(s);
                    return b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

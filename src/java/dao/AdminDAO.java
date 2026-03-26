package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;
import utils.DBContext;

public class AdminDAO {

    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users WHERE email != 'admin@timebank.com'";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getTotalServices() {
        String sql = "SELECT COUNT(*) FROM Services";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) FROM Bookings";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public double getTotalTransactionHours() {
        String sql = "SELECT ISNULL(SUM(hours), 0) FROM Transactions";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users WHERE email != 'admin@timebank.com' ORDER BY user_id DESC";
        List<User> list = new ArrayList<>();
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
                    rs.getString("password"), rs.getString("phone"), rs.getString("cccd"),
                    rs.getString("address"), rs.getString("avatar"), rs.getString("gender"), rs.getDouble("wallet_hours")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getRecentBookings(int limit) {
        String sql = "SELECT TOP(?) b.booking_id, u1.full_name as requester, u2.full_name as provider, " +
                     "s.title, b.schedule_time, b.status " +
                     "FROM Bookings b " +
                     "JOIN Users u1 ON b.requester_id = u1.user_id " +
                     "JOIN Users u2 ON b.provider_id = u2.user_id " +
                     "JOIN Services s ON b.service_id = s.service_id " +
                     "ORDER BY b.booking_id DESC";
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getInt("booking_id"),
                        rs.getString("requester"),
                        rs.getString("provider"),
                        rs.getString("title"),
                        rs.getString("schedule_time"),
                        rs.getString("status")
                    });
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean resetUserWallet(int userId) {
        String sql = "UPDATE Users SET wallet_hours = 0 WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteUser(int userId) {
        try (Connection conn = new DBContext().getConnection()) {
            // Xóa messages liên quan
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Messages WHERE sender_id=? OR receiver_id=?");
            ps1.setInt(1, userId); ps1.setInt(2, userId); ps1.executeUpdate();

            // Xóa reviews liên quan (qua bookings)
            PreparedStatement ps2 = conn.prepareStatement(
                "DELETE FROM Reviews WHERE booking_id IN (SELECT booking_id FROM Bookings WHERE requester_id=? OR provider_id=?)");
            ps2.setInt(1, userId); ps2.setInt(2, userId); ps2.executeUpdate();

            // Xóa transactions
            PreparedStatement ps3 = conn.prepareStatement("DELETE FROM Transactions WHERE from_user=? OR to_user=?");
            ps3.setInt(1, userId); ps3.setInt(2, userId); ps3.executeUpdate();

            // Xóa bookings
            PreparedStatement ps4 = conn.prepareStatement("DELETE FROM Bookings WHERE requester_id=? OR provider_id=?");
            ps4.setInt(1, userId); ps4.setInt(2, userId); ps4.executeUpdate();

            // Xóa services
            PreparedStatement ps5 = conn.prepareStatement("DELETE FROM Services WHERE user_id=?");
            ps5.setInt(1, userId); ps5.executeUpdate();

            // Xóa user
            PreparedStatement ps6 = conn.prepareStatement("DELETE FROM Users WHERE user_id=?");
            ps6.setInt(1, userId);
            return ps6.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean addHoursToUser(int userId, double hours) {
        String sql = "UPDATE Users SET wallet_hours = wallet_hours + ? WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, hours);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}

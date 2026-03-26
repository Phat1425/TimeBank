package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Service;
import model.User;
import utils.DBContext;

public class ServiceDAO {

    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        // Join with Users to get poster's info
        String query = "SELECT s.*, u.full_name, u.avatar FROM Services s JOIN Users u ON s.user_id = u.user_id ORDER BY s.created_at DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Service s = new Service(
                    rs.getInt("service_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("hours_required"),
                    rs.getString("type"),
                    rs.getTimestamp("created_at")
                );
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setAvatar(rs.getString("avatar"));
                s.setUser(u);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Service> getServicesByUser(int userId) {
        List<Service> list = new ArrayList<>();
        String query = "SELECT s.*, u.full_name, u.avatar FROM Services s JOIN Users u ON s.user_id = u.user_id WHERE s.user_id = ? ORDER BY s.created_at DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Service s = new Service(
                        rs.getInt("service_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("hours_required"),
                        rs.getString("type"),
                        rs.getTimestamp("created_at")
                    );
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setAvatar(rs.getString("avatar"));
                    s.setUser(u);
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service getServiceById(int serviceId) {
        String query = "SELECT s.*, u.full_name, u.avatar FROM Services s JOIN Users u ON s.user_id = u.user_id WHERE s.service_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Service s = new Service(
                        rs.getInt("service_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("hours_required"),
                        rs.getString("type"),
                        rs.getTimestamp("created_at")
                    );
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setAvatar(rs.getString("avatar"));
                    s.setUser(u);
                    return s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createService(Service s) {
        String query = "INSERT INTO Services(user_id, title, description, hours_required, type, created_at) VALUES(?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getTitle());
            ps.setString(3, s.getDescription());
            ps.setDouble(4, s.getHoursRequired());
            ps.setString(5, s.getType());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editService(Service s) {
        String query = "UPDATE Services SET title=?, description=?, hours_required=?, type=? WHERE service_id=? AND user_id=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, s.getTitle());
            ps.setString(2, s.getDescription());
            ps.setDouble(3, s.getHoursRequired());
            ps.setString(4, s.getType());
            ps.setInt(5, s.getServiceId());
            ps.setInt(6, s.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteService(int serviceId, int userId) {
        String query = "DELETE FROM Services WHERE service_id=? AND user_id=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, serviceId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Service> searchServices(String keyword) {
        List<Service> list = new ArrayList<>();
        String query = "SELECT s.*, u.full_name, u.avatar FROM Services s JOIN Users u ON s.user_id = u.user_id WHERE s.title LIKE ? OR s.description LIKE ? ORDER BY s.created_at DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Service s = new Service(
                        rs.getInt("service_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("hours_required"),
                        rs.getString("type"),
                        rs.getTimestamp("created_at")
                    );
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setAvatar(rs.getString("avatar"));
                    s.setUser(u);
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

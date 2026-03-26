package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import utils.DBContext;

public class UserDAO {
    
    public User login(String email, String password) throws Exception {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("cccd"),
                        rs.getString("address"),
                        rs.getString("avatar"),
                        rs.getString("gender"),
                        rs.getDouble("wallet_hours")
                    );
                }
            }
        }
        return null;
    }

    public boolean register(User u) {
        String query = "INSERT INTO Users(full_name, email, password, phone, cccd, address, avatar, gender, wallet_hours) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getCccd());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getAvatar());
            ps.setString(8, u.getGender());
            ps.setDouble(9, u.getWalletHours()); // Default 0
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("cccd"),
                        rs.getString("address"),
                        rs.getString("avatar"),
                        rs.getString("gender"),
                        rs.getDouble("wallet_hours")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateWallet(int userId, double amount) {
        String query = "UPDATE Users SET wallet_hours = wallet_hours + ? WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            // The original code had 'return ps.executeUpdate() > 0;'.
            // The instruction's snippet changed it to just 'ps.executeUpdate();'.
            // To maintain the boolean return type, we'll keep the original logic.
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public User getAdminUser() {
        String query = "SELECT * FROM Users WHERE email = 'admin@timebank.com'";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("phone"), rs.getString("cccd"),
                        rs.getString("address"), rs.getString("avatar"), rs.getString("gender"), rs.getDouble("wallet_hours")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("phone"), rs.getString("cccd"),
                        rs.getString("address"), rs.getString("avatar"), rs.getString("gender"), rs.getDouble("wallet_hours")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE Users SET password = ? WHERE email = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User u) {
        String query = "UPDATE Users SET full_name=?, phone=?, address=?, gender=?, password=? WHERE user_id=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getPhone());
            ps.setString(3, u.getAddress());
            ps.setString(4, u.getGender());
            ps.setString(5, u.getPassword());
            ps.setInt(6, u.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

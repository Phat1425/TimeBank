package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Message;
import utils.DBContext;

public class MessageDAO {

    public boolean sendMessage(Message m) {
        String query = "INSERT INTO Messages(sender_id, receiver_id, content, sent_at) VALUES(?, ?, ?, GETDATE())";
        System.out.println("Sending message from " + m.getSenderId() + " to " + m.getReceiverId());
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, m.getSenderId());
            ps.setInt(2, m.getReceiverId());
            ps.setString(3, m.getContent());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Message> getConversation(int user1, int user2) {
        List<Message> list = new ArrayList<>();
        String query = "SELECT * FROM Messages " +
                       "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                       "ORDER BY sent_at ASC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boolean isRead = false;
                    try {
                        isRead = rs.getBoolean("is_read");
                    } catch (Exception e) {
                        // is_read column might be missing
                    }
                    list.add(new Message(
                        rs.getInt("msg_id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("content"),
                        rs.getTimestamp("sent_at"),
                        isRead
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR in getConversation: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    // Get list of users the current user has chatted with
    public List<Integer> getChattedUserIds(int userId) {
        List<Integer> list = new ArrayList<>();
        String query = "SELECT DISTINCT sender_id AS user_id FROM Messages WHERE receiver_id = ? " +
                       "UNION " +
                       "SELECT DISTINCT receiver_id AS user_id FROM Messages WHERE sender_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("user_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markAsRead(int senderId, int receiverId) {
        String query = "UPDATE Messages SET is_read = 1 WHERE sender_id = ? AND receiver_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUnreadCount(int userId) {
        String query = "SELECT COUNT(*) FROM Messages WHERE receiver_id = ? AND is_read = 0";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            // Might be missing is_read column, fall back to 0 without hanging
            System.err.println("UnreadCount error: " + e.getMessage());
        }
        return 0;
    }

    public int getUnreadFromUserCount(int receiverId, int senderId) {
        String query = "SELECT COUNT(*) FROM Messages WHERE receiver_id = ? AND sender_id = ? AND is_read = 0";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, receiverId);
            ps.setInt(2, senderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

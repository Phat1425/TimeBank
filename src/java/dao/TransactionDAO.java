package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import model.User;
import utils.DBContext;

public class TransactionDAO {

    public boolean createTransaction(Transaction t) {
        String query = "INSERT INTO Transactions(from_user, to_user, hours, created_at) VALUES(?, ?, ?, GETDATE())";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getFromUser());
            ps.setInt(2, t.getToUser());
            ps.setDouble(3, t.getHours());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT t.*, u1.full_name AS from_name, u2.full_name AS to_name " +
                       "FROM Transactions t " +
                       "LEFT JOIN Users u1 ON t.from_user = u1.user_id " +
                       "LEFT JOIN Users u2 ON t.to_user = u2.user_id " +
                       "WHERE t.from_user = ? OR t.to_user = ? ORDER BY t.created_at DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction(
                        rs.getInt("trans_id"),
                        rs.getInt("from_user"),
                        rs.getInt("to_user"),
                        rs.getDouble("hours"),
                        rs.getTimestamp("created_at")
                    );
                    
                    User fromUser = new User();
                    fromUser.setFullName(rs.getString("from_name"));
                    t.setFromUserObj(fromUser);
                    
                    User toUser = new User();
                    toUser.setFullName(rs.getString("to_name"));
                    t.setToUserObj(toUser);
                    
                    list.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

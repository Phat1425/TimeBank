package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Review;
import model.User;
import utils.DBContext;

public class ReviewDAO {

    public boolean createReview(Review r) {
        String query = "INSERT INTO Reviews(booking_id, rating, comment) VALUES(?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, r.getBookingId());
            ps.setInt(2, r.getRating());
            ps.setString(3, r.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Review> getReviewsByProvider(int providerId) {
        List<Review> list = new ArrayList<>();
        // Join Bookings to get the requester who wrote the review for the provider
        String query = "SELECT r.*, u.full_name, u.avatar " +
                       "FROM Reviews r " +
                       "JOIN Bookings b ON r.booking_id = b.booking_id " +
                       "JOIN Users u ON b.requester_id = u.user_id " +
                       "WHERE b.provider_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                    );
                    User reviewer = new User();
                    reviewer.setFullName(rs.getString("full_name"));
                    reviewer.setAvatar(rs.getString("avatar"));
                    r.setReviewer(reviewer);
                    
                    list.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Review getReviewByBooking(int bookingId) {
        String query = "SELECT r.*, u.full_name, u.avatar " +
                       "FROM Reviews r " +
                       "JOIN Bookings b ON r.booking_id = b.booking_id " +
                       "JOIN Users u ON b.requester_id = u.user_id " +
                       "WHERE r.booking_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Review r = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                    );
                    User reviewer = new User();
                    reviewer.setFullName(rs.getString("full_name"));
                    reviewer.setAvatar(rs.getString("avatar"));
                    r.setReviewer(reviewer);
                    return r;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

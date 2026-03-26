package model;

public class Review {
    private int reviewId;
    private int bookingId;
    private int rating;
    private String comment;
    
    // Extracted requester details to show in UI
    private User reviewer;

    public Review() {
    }

    public Review(int reviewId, int bookingId, int rating, String comment) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public User getReviewer() { return reviewer; }
    public void setReviewer(User reviewer) { this.reviewer = reviewer; }
}

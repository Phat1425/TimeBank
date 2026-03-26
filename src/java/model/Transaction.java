package model;

import java.util.Date;

public class Transaction {
    private int transId;
    private int fromUser;
    private int toUser;
    private double hours;
    private Date createdAt;
    
    private User fromUserObj;
    private User toUserObj;

    public Transaction() {
    }

    public Transaction(int transId, int fromUser, int toUser, double hours, Date createdAt) {
        this.transId = transId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.hours = hours;
        this.createdAt = createdAt;
    }

    public int getTransId() { return transId; }
    public void setTransId(int transId) { this.transId = transId; }

    public int getFromUser() { return fromUser; }
    public void setFromUser(int fromUser) { this.fromUser = fromUser; }

    public int getToUser() { return toUser; }
    public void setToUser(int toUser) { this.toUser = toUser; }

    public double getHours() { return hours; }
    public void setHours(double hours) { this.hours = hours; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getFromUserObj() { return fromUserObj; }
    public void setFromUserObj(User fromUserObj) { this.fromUserObj = fromUserObj; }

    public User getToUserObj() { return toUserObj; }
    public void setToUserObj(User toUserObj) { this.toUserObj = toUserObj; }
}

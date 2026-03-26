package model;

import java.util.Date;

public class Service {
    private int serviceId;
    private int userId;
    private String title;
    private String description;
    private double hoursRequired;
    private String type; // offer or request
    private Date createdAt;
    
    // Aggregates for UI
    private User user;

    public Service() {
    }

    public Service(int serviceId, int userId, String title, String description, double hoursRequired, String type, Date createdAt) {
        this.serviceId = serviceId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.hoursRequired = hoursRequired;
        this.type = type;
        this.createdAt = createdAt;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getHoursRequired() { return hoursRequired; }
    public void setHoursRequired(double hoursRequired) { this.hoursRequired = hoursRequired; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

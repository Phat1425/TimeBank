package model;

import java.util.Date;

public class Booking {
    private int bookingId;
    private int serviceId;
    private int requesterId;
    private int providerId;
    private Date scheduleTime;
    private String status;
    
    // Object references for convenience
    private Service service;
    private User requester;
    private User provider;

    public Booking() {
    }

    public Booking(int bookingId, int serviceId, int requesterId, int providerId, Date scheduleTime, String status) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.requesterId = requesterId;
        this.providerId = providerId;
        this.scheduleTime = scheduleTime;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getRequesterId() { return requesterId; }
    public void setRequesterId(int requesterId) { this.requesterId = requesterId; }

    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }

    public Date getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(Date scheduleTime) { this.scheduleTime = scheduleTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public User getProvider() { return provider; }
    public void setProvider(User provider) { this.provider = provider; }
}

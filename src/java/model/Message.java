package model;

import java.util.Date;

public class Message {
    private int msgId;
    private int senderId;
    private int receiverId;
    private String content;
    private Date sentAt;
    private boolean isRead;

    public Message() {
    }

    public Message(int msgId, int senderId, int receiverId, String content, Date sentAt, boolean isRead) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public int getMsgId() { return msgId; }
    public void setMsgId(int msgId) { this.msgId = msgId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }

    public boolean isIsRead() { return isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }
}

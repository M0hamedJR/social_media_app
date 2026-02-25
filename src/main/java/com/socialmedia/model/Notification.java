package com.socialmedia.model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private boolean isRead;
    private Timestamp createdAt;

    // الجديد: اسم المرسل
    private String senderName;

    public Notification(int id, int userId, String message, boolean isRead, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public Timestamp getCreatedAt() { return createdAt; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    @Override
    public String toString() {
        if (senderName != null && !senderName.isEmpty()) {
            return senderName + ": " + message + (isRead ? " (Read)" : " (Unread)");
        }
        return "User " + userId + ": " + message + (isRead ? " (Read)" : " (Unread)");
    }
}
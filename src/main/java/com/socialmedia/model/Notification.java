package com.socialmedia.model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private boolean isRead;
    private Timestamp createdAt;

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

    @Override
    public String toString() {
        return message + (isRead ? " (Read)" : " (Unread)");
    }
}
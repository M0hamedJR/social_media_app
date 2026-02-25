package com.socialmedia.model;

import java.sql.Timestamp;

public class FriendRequest {
    private int id;
    private int senderId;
    private int receiverId;
    private String status;
    private Timestamp createdAt;

    private String senderName;

    public FriendRequest(int id, int senderId, int receiverId, String status, Timestamp createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getId() { return id; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getStatus() { return status; }
    public Timestamp getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        if (senderName != null && !senderName.isEmpty()) {
            return "From " + senderName + " [" + status + "]";
        }
        return "From User " + senderId + " [" + status + "]";
    }
}
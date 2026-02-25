package com.socialmedia.model;

import java.sql.Timestamp;

public class Like {
    private int id;
    private int userId;
    private int postId;
    private Timestamp createdAt;

    public Like(int id, int userId, int postId, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getPostId() { return postId; }
    public Timestamp getCreatedAt() { return createdAt; }
}
package com.socialmedia.model;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int userId;
    private int postId;
    private String content;
    private Timestamp createdAt;

    public Comment(int id, int userId, int postId, String content, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getPostId() { return postId; }
    public String getContent() { return content; }
    public Timestamp getCreatedAt() { return createdAt; }
}
package com.socialmedia.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String imagePath;
    private String privacy;
    private Timestamp createdAt;

    private String userName;
    private String profileImage;

    public Post(int id, int userId, String content, String imagePath, String privacy, Timestamp createdAt,
                String userName, String profileImage) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.imagePath = imagePath;
        this.privacy = privacy;
        this.createdAt = createdAt;
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public Post(int id, int userId, String content, String imagePath, String privacy, Timestamp createdAt) {
        this(id, userId, content, imagePath, privacy, createdAt, null, null);
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getContent() { return content; }
    public String getImagePath() { return imagePath; }
    public String getPrivacy() { return privacy; }
    public Timestamp getCreatedAt() { return createdAt; }

    public String getUserName() { return userName; }
    public String getProfileImage() { return profileImage; }

    @Override
    public String toString() {
        return (userName != null ? userName : "User " + userId) + ": " + content + " [" + privacy + "] at " + createdAt;
    }
}
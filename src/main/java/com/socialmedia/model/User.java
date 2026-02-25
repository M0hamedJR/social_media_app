package com.socialmedia.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String createdAt;
    private String profileImage;
    private String bio;

    public User(int id, String name, String email, String password, String createdAt, String profileImage, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.profileImage = profileImage;
        this.bio = bio;
    }

    public User(int id, String name, String email, String password, String createdAt) {
        this(id, name, email, password, createdAt, null, null);
    }

    public User(int id, String name, String email, String password, String createdAt, String profileImage) {
        this(id, name, email, password, createdAt, profileImage, null);
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return name; } //
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCreatedAt() { return createdAt; }
    public String getProfileImage() { return profileImage; }
    public String getBio() { return bio; }

    // Setters
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public void setBio(String bio) { this.bio = bio; }
}
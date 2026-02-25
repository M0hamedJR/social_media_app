package com.socialmedia.model;

public class Privacy {
    private int id;
    private int userId;
    private String setting; // PUBLIC, FRIENDS, PRIVATE

    public Privacy(int id, int userId, String setting) {
        this.id = id;
        this.userId = userId;
        this.setting = setting;
    }

    public Privacy(int userId, String setting) {
        this.userId = userId;
        this.setting = setting;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSetting() { return setting; }
    public void setSetting(String setting) { this.setting = setting; }
}

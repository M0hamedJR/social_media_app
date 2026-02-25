package com.socialmedia.model;

public class Profile {
    private int id;
    private String name;
    private String email;
    private String bio;
    private String profilePicture;

    public Profile(int id, String name, String email, String bio, String profilePicture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getProfilePicture() { return profilePicture; }
}
package com.socialmedia.dao;

import com.socialmedia.model.Post;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public List<Post> getPostsForUser(int userId) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS user_name, u.profile_image AS profile_image " +
                "FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "LEFT JOIN friends f ON (p.user_id = f.user1_id AND f.user2_id = ?) " +
                "                     OR (p.user_id = f.user2_id AND f.user1_id = ?) " +
                "WHERE p.privacy = 'PUBLIC' " +
                "   OR (p.privacy = 'FRIENDS' AND f.user1_id IS NOT NULL) " +
                "   OR (p.privacy = 'PRIVATE' AND p.user_id = ?) " +
                "ORDER BY p.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getString("image_path"),
                        rs.getString("privacy"),
                        rs.getTimestamp("created_at"),
                        rs.getString("user_name"),
                        rs.getString("profile_image")
                ));
            }
        }
        return posts;
    }

    public void addPost(int userId, String content, String imagePath, String privacy) throws SQLException {
        String sql = "INSERT INTO posts (user_id, content, image_path, privacy) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, content);
            stmt.setString(3, imagePath);
            stmt.setString(4, privacy);
            stmt.executeUpdate();
        }
    }

    public int getPostOwnerId(int postId) throws SQLException {
        String sql = "SELECT user_id FROM posts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        }
        return -1;
    }

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS user_name, u.profile_image AS profile_image " +
                "FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "ORDER BY p.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getString("image_path"),
                        rs.getString("privacy"),
                        rs.getTimestamp("created_at"),
                        rs.getString("user_name"),
                        rs.getString("profile_image")
                ));
            }
        }
        return posts;
    }
}
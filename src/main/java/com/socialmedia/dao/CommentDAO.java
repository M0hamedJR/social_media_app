package com.socialmedia.dao;

import com.socialmedia.model.Comment;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private NotificationDAO notificationDAO = new NotificationDAO();
    private PostDAO postDAO = new PostDAO();

    public void addComment(int userId, int postId, String content) throws SQLException {
        String sql = "INSERT INTO comments (user_id, post_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }

        int postOwnerId = postDAO.getPostOwnerId(postId);
        if (postOwnerId != userId) {
            notificationDAO.addCommentNotification(postOwnerId, userId);
        }
    }

    public List<Comment> getCommentsByPost(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("post_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return comments;
    }
}
package com.socialmedia.dao;

import com.socialmedia.model.Comment;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private NotificationDAO notificationDAO = new NotificationDAO();
    private PostDAO postDAO = new PostDAO();
    private UserDAO userDAO = new UserDAO();

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
            String commenterName = userDAO.getUserById(userId).getName();
            notificationDAO.addCommentNotification(postOwnerId, userId, commenterName);
        }
    }

    public List<Comment> getCommentsByPost(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, u.name AS username " +
                "FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.post_id = ? ORDER BY c.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("post_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                );
                comment.setUsername(rs.getString("username"));
                comments.add(comment);
            }
        }
        return comments;
    }
}
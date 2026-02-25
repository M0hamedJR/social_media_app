package com.socialmedia.dao;

import com.socialmedia.util.DatabaseConnection;
import java.sql.*;

public class LikeDAO {

    private NotificationDAO notificationDAO = new NotificationDAO();
    private PostDAO postDAO = new PostDAO();
    private UserDAO userDAO = new UserDAO(); //

    public void addLike(int userId, int postId) throws SQLException {
        String sql = "INSERT INTO likes (user_id, post_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        }

        int postOwnerId = postDAO.getPostOwnerId(postId);
        if (postOwnerId != userId) {
            String likerName = userDAO.getUserById(userId).getName();
            notificationDAO.addLikeNotification(postOwnerId, userId, likerName);
        }
    }

    public void removeLike(int userId, int postId) throws SQLException {
        String sql = "DELETE FROM likes WHERE user_id = ? AND post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        }
    }

    public int countLikes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean hasUserLiked(int userId, int postId) throws SQLException {
        String sql = "SELECT * FROM likes WHERE user_id = ? AND post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
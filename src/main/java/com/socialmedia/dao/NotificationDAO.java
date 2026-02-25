package com.socialmedia.dao;

import com.socialmedia.model.Notification;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public List<Notification> getNotificationsByUserId(int userId) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return notifications;
    }

    public void markAsRead(int notificationId) throws SQLException {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        }
    }

    public void addNotification(int userId, String message) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        }
    }

    public void addLikeNotification(int postOwnerId, int likerId) throws SQLException {
        String message = "User " + likerId + " liked your post.";
        addNotification(postOwnerId, message);
    }

    public void addCommentNotification(int postOwnerId, int commenterId) throws SQLException {
        String message = "User " + commenterId + " commented on your post.";
        addNotification(postOwnerId, message);
    }

    public void addFriendRequestNotification(int receiverId, int senderId) throws SQLException {
        String message = "User " + senderId + " sent you a friend request.";
        addNotification(receiverId, message);
    }

    public void addFriendAcceptedNotification(int senderId, int receiverId) throws SQLException {
        String message = "User " + receiverId + " accepted your friend request.";
        addNotification(senderId, message);
    }
}
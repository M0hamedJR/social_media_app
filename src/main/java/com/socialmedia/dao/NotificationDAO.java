package com.socialmedia.dao;

import com.socialmedia.model.Notification;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public List<Notification> getNotificationsByUserId(int userId) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT n.*, u.name AS sender_name " +
                "FROM notifications n " +
                "JOIN users u ON n.user_id = u.id " +
                "WHERE n.user_id = ? ORDER BY n.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                );
                notification.setSenderName(rs.getString("sender_name"));
                notifications.add(notification);
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

    public void addLikeNotification(int postOwnerId, int likerId, String likerName) throws SQLException {
        String message = likerName + " liked your post.";
        addNotification(postOwnerId, message);
    }

    public void addCommentNotification(int postOwnerId, int commenterId, String commenterName) throws SQLException {
        String message = commenterName + " commented on your post.";
        addNotification(postOwnerId, message);
    }

    public void addFriendRequestNotification(int receiverId, int senderId, String senderName) throws SQLException {
        String message = senderName + " sent you a friend request.";
        addNotification(receiverId, message);
    }

    public void addFriendAcceptedNotification(int senderId, int receiverId, String receiverName) throws SQLException {
        String message = receiverName + " accepted your friend request.";
        addNotification(senderId, message);
    }
}
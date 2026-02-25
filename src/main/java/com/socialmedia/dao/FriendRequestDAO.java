package com.socialmedia.dao;

import com.socialmedia.model.FriendRequest;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO {

    public List<FriendRequest> getRequestsByReceiverId(int receiverId) throws SQLException {
        List<FriendRequest> requests = new ArrayList<>();
        String sql = "SELECT fr.*, u.name AS sender_name " +
                "FROM friend_requests fr " +
                "JOIN users u ON fr.sender_id = u.id " +
                "WHERE fr.receiver_id = ? AND fr.status = 'PENDING'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receiverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FriendRequest request = new FriendRequest(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
                request.setSenderName(rs.getString("sender_name"));
                requests.add(request);
            }
        }
        return requests;
    }

    public void sendRequest(int senderId, int receiverId) throws SQLException {
        String sql = "INSERT INTO friend_requests (sender_id, receiver_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.executeUpdate();
        }
    }

    public void updateRequestStatus(int requestId, String status) throws SQLException {
        String sql = "UPDATE friend_requests SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, requestId);
            stmt.executeUpdate();
        }
    }

    public void addFriend(int user1Id, int user2Id) throws SQLException {
        String sql = "INSERT INTO friends (user1_id, user2_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user1Id);
            stmt.setInt(2, user2Id);
            stmt.executeUpdate();
        }
    }
}
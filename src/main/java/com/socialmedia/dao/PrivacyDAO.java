package com.socialmedia.dao;

import com.socialmedia.util.DatabaseConnection;

import java.sql.*;

public class PrivacyDAO {

    public String getPrivacySetting(int userId) throws SQLException {
        String sql = "SELECT setting FROM privacy_settings WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("setting");
            }
        }
        return null;
    }

    public void savePrivacySetting(int userId, String setting) throws SQLException {
        String sql = "INSERT INTO privacy_settings (user_id, setting) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE setting = VALUES(setting)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, setting);
            stmt.executeUpdate();
        }
    }
}
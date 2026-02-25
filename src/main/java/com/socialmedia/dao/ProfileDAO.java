package com.socialmedia.dao;

import com.socialmedia.model.Profile;
import com.socialmedia.util.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class ProfileDAO {

    public Optional<Profile> getProfileByUserId(int userId) throws SQLException {
        String sql = "SELECT u.id, u.name, u.email, p.bio, p.profile_picture " +
                "FROM users u LEFT JOIN profiles p ON u.id = p.user_id " +
                "WHERE u.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Profile profile = new Profile(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("bio"),
                        rs.getString("profile_picture")
                );
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }

    public void updateProfile(int userId, String name, String email, String bio, String profilePicture) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            String sqlUsers = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsers)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            }

            String sqlProfiles = "INSERT INTO profiles (user_id, bio, profile_picture) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE bio = VALUES(bio), profile_picture = VALUES(profile_picture)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlProfiles)) {
                stmt.setInt(1, userId);
                stmt.setString(2, bio);
                stmt.setString(3, profilePicture);
                stmt.executeUpdate();
            }

            conn.commit();
        }
    }
}
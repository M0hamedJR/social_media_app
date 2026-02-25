package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.socialmedia.dao.UserDAO;
import com.socialmedia.model.User;
import com.socialmedia.util.Session;

import java.io.File;
import java.sql.SQLException;

public class ProfileController {

    @FXML private ImageView profileImageView;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextArea bioField;
    @FXML private Label messageLabel;

    private UserDAO userDAO = new UserDAO();
    private int currentUserId = Session.getCurrentUserId();

    @FXML
    public void initialize() {
        try {
            User user = userDAO.getUserById(currentUserId);
            if (user != null) {
                nameField.setText(user.getName());
                emailField.setText(user.getEmail());
                bioField.setText(user.getBio());

                if (user.getProfileImage() != null) {
                    profileImageView.setImage(new Image(new File(user.getProfileImage()).toURI().toString()));
                }
            }
        } catch (SQLException e) {
            messageLabel.setText("Error loading profile: " + e.getMessage());
        }
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(profileImageView.getScene().getWindow());

        if (file != null) {
            String imagePath = file.getAbsolutePath();
            try {
                userDAO.updateProfileImage(currentUserId, imagePath);
                profileImageView.setImage(new Image(file.toURI().toString()));
                messageLabel.setText("Profile image updated!");
            } catch (SQLException e) {
                messageLabel.setText("Error updating image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSaveProfile() {
        String name = nameField.getText();
        String email = emailField.getText();
        String bio = bioField.getText();

        try {
            userDAO.updateProfile(currentUserId, name, email, bio);
            messageLabel.setText("Profile updated successfully!");
        } catch (SQLException e) {
            messageLabel.setText("Error updating profile: " + e.getMessage());
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) profileImageView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
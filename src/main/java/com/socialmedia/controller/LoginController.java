package com.socialmedia.controller;

import com.socialmedia.util.PasswordUtil;
import com.socialmedia.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import com.socialmedia.dao.UserDAO;
import com.socialmedia.model.User;

import java.util.Optional;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Optional<User> userOpt = userDAO.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (PasswordUtil.checkPassword(password, user.getPassword())) { // hashing
                    messageLabel.setText("Login successful!");

                    Session.setCurrentUserId(user.getId());

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu.fxml"));
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(new Scene(loader.load(), 600, 650));
                } else {
                    messageLabel.setText("Invalid password!");
                }
            } else {
                messageLabel.setText("User not found!");
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/register.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
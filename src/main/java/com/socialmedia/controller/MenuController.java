package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MenuController {

    @FXML private Button profileButton;
    @FXML private Button newsFeedButton;
    @FXML private Button notificationsButton;
    @FXML private Button friendRequestsButton;
    @FXML private Button searchButton;
    @FXML private Button privacyButton;
    @FXML private Button logoutButton;

    private void switchScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/" + fxmlFile));
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), width, height));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToProfile() {
        switchScene("profile.fxml", 600, 650);
    }

    @FXML
    private void goToNewsFeed() {
        switchScene("newsfeed.fxml", 600, 800);
    }

    @FXML
    private void goToNotifications() {
        switchScene("notification.fxml", 600, 650);
    }

    @FXML
    private void goToFriendRequests() {
        switchScene("friend_request.fxml", 600, 650);
    }

    @FXML
    private void goToSearch() {
        switchScene("search.fxml", 600, 650);
    }

    @FXML
    private void goToPrivacy() {
        switchScene("privacy.fxml", 600, 650);
    }

    @FXML
    private void goToLogin() {
        switchScene("login.fxml", 600, 650);
    }
}
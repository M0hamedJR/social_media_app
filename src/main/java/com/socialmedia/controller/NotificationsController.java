package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.socialmedia.dao.NotificationDAO;
import com.socialmedia.model.Notification;
import com.socialmedia.util.Session;

import java.sql.SQLException;
import java.util.List;

public class NotificationsController {

    @FXML private ListView<Notification> notificationsListView;
    @FXML private Label messageLabel;

    private NotificationDAO notificationDAO = new NotificationDAO();
    private int currentUserId = Session.getCurrentUserId();

    @FXML
    public void initialize() {
        loadNotifications();
    }

    @FXML
    private void loadNotifications() {
        try {
            List<Notification> notifications = notificationDAO.getNotificationsByUserId(currentUserId);
            notificationsListView.getItems().setAll(notifications);

            notificationsListView.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Notification notification, boolean empty) {
                    super.updateItem(notification, empty);
                    if (empty || notification == null) {
                        setText(null);
                    } else {
                        if (notification.isRead()) {
                            setText("✓ " + notification.getMessage());
                            setStyle("-fx-text-fill: green;"); //
                        } else {
                            setText("• " + notification.getMessage());
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); //
                        }
                    }
                }
            });

            messageLabel.setText("Notifications loaded!");
        } catch (SQLException e) {
            messageLabel.setText("Error loading notifications: " + e.getMessage());
        }
    }

    @FXML
    private void handleMarkAsRead() {
        Notification selected = notificationsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                notificationDAO.markAsRead(selected.getId());
                loadNotifications(); //
                messageLabel.setText("Notification marked as read!");
            } catch (SQLException e) {
                messageLabel.setText("Error marking notification: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Please select a notification first!");
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) notificationsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.socialmedia.dao.FriendRequestDAO;
import com.socialmedia.model.FriendRequest;
import com.socialmedia.util.Session;

import java.sql.SQLException;
import java.util.List;

public class FriendRequestController {

    @FXML private ListView<FriendRequest> requestsListView;
    @FXML private TextField receiverField;
    @FXML private Label messageLabel;

    private FriendRequestDAO friendRequestDAO = new FriendRequestDAO();
    private int currentUserId = Session.getCurrentUserId();

    @FXML
    public void initialize() {
        loadRequests();
    }

    private void loadRequests() {
        try {
            List<FriendRequest> requests = friendRequestDAO.getRequestsByReceiverId(currentUserId);
            requestsListView.getItems().setAll(requests);
        } catch (SQLException e) {
            messageLabel.setText("Error loading requests: " + e.getMessage());
        }
    }

    @FXML
    private void handleAccept() {
        FriendRequest request = requestsListView.getSelectionModel().getSelectedItem();
        if (request != null) {
            try {
                friendRequestDAO.updateRequestStatus(request.getId(), "ACCEPTED");
                // أضف الاتنين في جدول friends
                friendRequestDAO.addFriend(request.getSenderId(), request.getReceiverId());
                messageLabel.setText("Request accepted! Friend added.");
                loadRequests();
            } catch (SQLException e) {
                messageLabel.setText("Error accepting request: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleReject() {
        FriendRequest request = requestsListView.getSelectionModel().getSelectedItem();
        if (request != null) {
            try {
                friendRequestDAO.updateRequestStatus(request.getId(), "REJECTED");
                messageLabel.setText("Request rejected!");
                loadRequests();
            } catch (SQLException e) {
                messageLabel.setText("Error rejecting request: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSendRequest() {
        try {
            int receiverId = Integer.parseInt(receiverField.getText());
            friendRequestDAO.sendRequest(currentUserId, receiverId);
            messageLabel.setText("Request sent to user " + receiverId + "!");
            loadRequests();
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid user ID!");
        } catch (SQLException e) {
            messageLabel.setText("Error sending request: " + e.getMessage());
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) requestsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
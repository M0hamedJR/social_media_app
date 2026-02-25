package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.socialmedia.dao.SearchDAO;
import com.socialmedia.model.User;
import com.socialmedia.model.Post;

import java.sql.SQLException;
import java.util.List;

public class SearchController {

    @FXML private TextField searchField;
    @FXML private ListView<String> resultsListView;
    @FXML private Label messageLabel;

    private SearchDAO searchDAO = new SearchDAO();

    @FXML
    private void handleSearchUsers() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            messageLabel.setText("Please enter a keyword!");
            return;
        }
        try {
            List<User> users = searchDAO.searchUsers(keyword);
            resultsListView.getItems().clear();
            for (User user : users) {
                resultsListView.getItems().add("User: " + user.getName() + " | Email: " + user.getEmail());
            }
            if (users.isEmpty()) {
                messageLabel.setText("No users found.");
            } else {
                messageLabel.setText("Users found: " + users.size());
            }
        } catch (SQLException e) {
            messageLabel.setText("Error searching users: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchPosts() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            messageLabel.setText("Please enter a keyword!");
            return;
        }
        try {
            List<Post> posts = searchDAO.searchPosts(keyword);
            resultsListView.getItems().clear();
            for (Post post : posts) {
                resultsListView.getItems().add("Post by User " + post.getUserId() + ": " + post.getContent());
            }
            if (posts.isEmpty()) {
                messageLabel.setText("No posts found.");
            } else {
                messageLabel.setText("Posts found: " + posts.size());
            }
        } catch (SQLException e) {
            messageLabel.setText("Error searching posts: " + e.getMessage());
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) resultsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.socialmedia.dao.PostDAO;
import com.socialmedia.dao.LikeDAO;
import com.socialmedia.dao.CommentDAO;
import com.socialmedia.model.Post;
import com.socialmedia.model.Comment;
import com.socialmedia.util.Session;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class NewsFeedController {

    @FXML private ListView<Post> postsListView;
    @FXML private TextArea postContentField;
    @FXML private ComboBox<String> privacyComboBox;
    @FXML private Label messageLabel;

    @FXML private Label likesCountLabel;
    @FXML private TextArea commentField;
    @FXML private ListView<String> commentsListView;

    private PostDAO postDAO = new PostDAO();
    private LikeDAO likeDAO = new LikeDAO();
    private CommentDAO commentDAO = new CommentDAO();

    private int currentUserId = Session.getCurrentUserId();
    private int currentPostId = -1;

    @FXML
    public void initialize() {
        privacyComboBox.getItems().addAll("PUBLIC", "FRIENDS", "PRIVATE");
        privacyComboBox.setValue("PUBLIC");
        loadPosts();

        postsListView.setCellFactory(lv -> new ListCell<>() {
            private final ImageView profileImageView = new ImageView();
            private final Label nameLabel = new Label();
            private final Label postContentLabel = new Label();
            private final ImageView postImageView = new ImageView();
            private final VBox vBox = new VBox();
            private final HBox hBox = new HBox();

            {
                profileImageView.setFitWidth(40);
                profileImageView.setFitHeight(40);

                nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                postContentLabel.setWrapText(true);

                postImageView.setFitWidth(200);
                postImageView.setPreserveRatio(true);

                VBox textBox = new VBox(nameLabel, postContentLabel);
                textBox.setSpacing(5);

                hBox.getChildren().addAll(profileImageView, textBox);
                hBox.setSpacing(10);

                vBox.getChildren().addAll(hBox, postImageView);
                vBox.setSpacing(10);
                vBox.getStyleClass().add("post-card");
                vBox.setStyle(
                        "-fx-background-color: #ffffff;" +
                                "-fx-border-color: #dcdcdc;" +       //
                                "-fx-border-radius: 8;" +            //
                                "-fx-background-radius: 8;" +        //
                                "-fx-padding: 10;" +                 //
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);" // ظل خفيف
                );
            }

            @Override
            protected void updateItem(Post post, boolean empty) {
                super.updateItem(post, empty);
                if (empty || post == null) {
                    setGraphic(null);
                } else {
                    // اسم المستخدم
                    nameLabel.setText(post.getUserName());

                    // صورة البروفايل (لو موجودة)
                    if (post.getProfileImage() != null && !post.getProfileImage().isEmpty()) {
                        File profileFile = new File(post.getProfileImage());
                        if (profileFile.exists()) {
                            profileImageView.setImage(new Image(profileFile.toURI().toString()));
                        } else {
                            profileImageView.setImage(null);
                        }
                    } else {
                        profileImageView.setImage(null);
                    }

                    postContentLabel.setText(post.getContent() != null ? post.getContent() : "");

                    if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                        File postFile = new File(post.getImagePath());
                        if (postFile.exists()) {
                            postImageView.setImage(new Image(postFile.toURI().toString()));
                        } else {
                            postImageView.setImage(null);
                        }
                    } else {
                        postImageView.setImage(null);
                    }

                    setGraphic(vBox);
                }
            }
        });
    }

    private void loadPosts() {
        try {
            List<Post> posts = postDAO.getPostsForUser(currentUserId);
            postsListView.getItems().setAll(posts);
        } catch (SQLException e) {
            messageLabel.setText("Error loading posts: " + e.getMessage());
        }
    }

    @FXML
    private void handleCreatePost() {
        String content = postContentField.getText();
        String privacy = privacyComboBox.getValue();

        if (content == null || content.trim().isEmpty()) {
            messageLabel.setText("Post content cannot be empty!");
            return;
        }

        try {
            postDAO.addPost(currentUserId, content, null, privacy);
            messageLabel.setText("Post created successfully!");
            postContentField.clear();
            loadPosts();
        } catch (SQLException e) {
            messageLabel.setText("Error creating post: " + e.getMessage());
        }
    }

    @FXML
    private void handlePostSelection() {
        Post selectedPost = postsListView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            currentPostId = selectedPost.getId();
            updateLikesCount();
            loadComments();
        }
    }

    @FXML
    private void handleLike() {
        if (currentPostId == -1) return;
        try {
            if (likeDAO.hasUserLiked(currentUserId, currentPostId)) {
                likeDAO.removeLike(currentUserId, currentPostId);
            } else {
                likeDAO.addLike(currentUserId, currentPostId);
            }
            updateLikesCount();
        } catch (SQLException e) {
            messageLabel.setText("Error updating like: " + e.getMessage());
        }
    }

    private void updateLikesCount() {
        try {
            int count = likeDAO.countLikes(currentPostId);
            likesCountLabel.setText("Likes: " + count);
        } catch (SQLException e) {
            messageLabel.setText("Error counting likes: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddComment() {
        if (currentPostId == -1) return;
        String content = commentField.getText();
        if (content != null && !content.trim().isEmpty()) {
            try {
                commentDAO.addComment(currentUserId, currentPostId, content);
                loadComments();
                commentField.clear();
            } catch (SQLException e) {
                messageLabel.setText("Error adding comment: " + e.getMessage());
            }
        }
    }

    private void loadComments() {
        try {
            commentsListView.getItems().clear();
            List<Comment> comments = commentDAO.getCommentsByPost(currentPostId);
            for (Comment c : comments) {
                commentsListView.getItems().add("User " + c.getUserId() + ": " + c.getContent());
            }
        } catch (SQLException e) {
            messageLabel.setText("Error loading comments: " + e.getMessage());
        }
    }

    @FXML
    private void handleUploadImage() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(postsListView.getScene().getWindow());

        if (file != null) {
            String imagePath = file.getAbsolutePath();
            String privacy = privacyComboBox.getValue();

            try {
                postDAO.addPost(currentUserId, null, imagePath, privacy);
                messageLabel.setText("Image post created successfully!");
                loadPosts();
            } catch (SQLException e) {
                messageLabel.setText("Error creating image post: " + e.getMessage());
            }
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) postsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
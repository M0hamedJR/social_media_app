package com.socialmedia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.socialmedia.dao.PrivacyDAO;
import com.socialmedia.util.Session;

import java.sql.SQLException;

public class PrivacyController {

    @FXML private ComboBox<String> privacyComboBox;
    @FXML private Label messageLabel;

    private PrivacyDAO privacySettingsDAO = new PrivacyDAO();
    private int currentUserId = Session.getCurrentUserId();

    @FXML
    public void initialize() {
        privacyComboBox.getItems().addAll("PUBLIC", "FRIENDS", "PRIVATE");

        try {
            String currentSetting = privacySettingsDAO.getPrivacySetting(currentUserId);
            if (currentSetting != null) {
                privacyComboBox.setValue(currentSetting);
            } else {
                privacyComboBox.setValue("PUBLIC");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error loading privacy setting: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        String selectedSetting = privacyComboBox.getValue();
        if (selectedSetting == null) {
            messageLabel.setText("Please select a privacy setting!");
            return;
        }
        try {
            privacySettingsDAO.savePrivacySetting(currentUserId, selectedSetting);
            messageLabel.setText("Privacy setting saved: " + selectedSetting);
        } catch (SQLException e) {
            messageLabel.setText("Error saving privacy setting: " + e.getMessage());
        }
    }

    @FXML
    private void goToMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ui/menu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) privacyComboBox.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 600, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
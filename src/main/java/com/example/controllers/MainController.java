package com.example.controllers;

import com.example.Views.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainController {

    private UserController userController;

    public MainController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    public void handleProfileButtonAction() {
        try {
            Stage stage = new Stage();
            new ProfileView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно профиля.");
        }
    }

    @FXML
    public void handleSendPackageButtonAction() {
        try {
            Stage stage = new Stage();
            new SendPackageView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно отправки посылки.");
        }
    }

    @FXML
    public void handleViewPackagesButtonAction() {
        try {
            Stage stage = new Stage();
            new ViewPackagesView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно просмотра посылок.");
        }
    }

    @FXML
    public void handleLogoutButtonAction() {
        Stage stage = (Stage) userController.getCurrentUser().getStage();
        new LoginView(userController).start(stage);
    }

    @FXML
    public void handleBackButtonAction(Stage primaryStage) {
        new MainView(userController).start(primaryStage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.example.controllers;

import com.example.Views.MainView;
import com.example.Views.ProfileView;
import com.example.Views.SendPackageView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainController {

    private UserController userController;

    public MainController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    public void handleProfileButtonAction(Stage primaryStage) {
        try {
            new ProfileView(userController).start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно профиля.");
        }
    }

    @FXML
    public void handleSendPackageButtonAction(Stage primaryStage) {
        try {
            new SendPackageView(userController).start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно отправки посылки.");
        }
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

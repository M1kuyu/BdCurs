package com.example.Views;

import com.example.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainController {

    private UserController userController;

    public MainController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private void handleProfileButtonAction() {
        try {
            new ProfileView(userController).start(new Stage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно профиля.");
        }
    }

    @FXML
    private void handleSendPackageButtonAction() {
        try {
            new SendPackageView(userController).start(new Stage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно отправки посылки.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

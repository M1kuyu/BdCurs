package com.example.controllers;

import com.example.models.Package;
import com.example.utils.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private TextField packageIdInput;

    private UserController userController;
    private PackageController packageController;

    public AdminController(UserController userController) {
        this.userController = userController;
        this.packageController = new PackageController();
    }

    @FXML
    public void handleSearchButtonAction() {
        int packageId = Integer.parseInt(packageIdInput.getText());
        Package aPackage = packageController.getPackageById(packageId);

        if (aPackage != null) {
            String message = "ID: " + aPackage.getPackageId()
                    + ", Отправитель: " + aPackage.getSenderId()
                    + ", Получатель: " + aPackage.getReceiverId()
                    + ", Вес: " + aPackage.getWeight()
                    + ", Тип: " + aPackage.getType();
            showAlert(Alert.AlertType.INFORMATION, "Посылка", message);
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Посылка не найдена.");
        }
    }

    @FXML
    public void handleBackButtonAction() {
        Stage stage = (Stage) packageIdInput.getScene().getWindow();
        new MainController(userController).handleBackButtonAction(stage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

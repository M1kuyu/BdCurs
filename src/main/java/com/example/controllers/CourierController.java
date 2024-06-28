package com.example.controllers;

import com.example.models.Package;
import com.example.utils.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourierController {

    @FXML
    private ComboBox<String> centerComboBox;

    private UserController userController;
    private PackageController packageController;

    public CourierController(UserController userController) {
        this.userController = userController;
        this.packageController = new PackageController();
    }

    @FXML
    public void handleLoadButtonAction() {
        String center = centerComboBox.getValue();
        List<Package> packages = packageController.getPackagesByCenter(center);

        if (packages != null) {
            StringBuilder sb = new StringBuilder();
            for (Package aPackage : packages) {
                sb.append("ID: ").append(aPackage.getPackageId())
                        .append(", Отправитель: ").append(aPackage.getSenderId())
                        .append(", Получатель: ").append(aPackage.getReceiverId())
                        .append(", Вес: ").append(aPackage.getWeight())
                        .append(", Тип: ").append(aPackage.getType())
                        .append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Посылки", sb.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка загрузки посылок.");
        }
    }

    @FXML
    public void handleBackButtonAction() {
        Stage stage = (Stage) centerComboBox.getScene().getWindow();
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

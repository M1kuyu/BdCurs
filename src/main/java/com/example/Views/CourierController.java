package com.example.Views;


import com.example.controllers.PackageController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import com.example.models.Package;

import java.util.List;

public class CourierController {

    @FXML
    private ComboBox<String> centerComboBox;

    private PackageController packageController;

    public CourierController(PackageController packageController) {
        this.packageController = packageController;
    }

    @FXML
    private void handleLoadButtonAction() {
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

/*package com.example.controllers;

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
}*/

package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.models.Courier;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourierController extends BaseController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> saveCourier());
        updateButton.setOnAction(event -> updateCourier());
        deleteButton.setOnAction(event -> deleteCourier());
    }

    private void saveCourier() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO Couriers (name, phone, delivery_center_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            // Replace with actual delivery_center_id
            stmt.setInt(3, 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourier() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        // Assume courier_id is known
        int courierId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "UPDATE Couriers SET name = ?, phone = ? WHERE courier_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setInt(3, courierId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourier() {
        // Assume courier_id is known
        int courierId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "DELETE FROM Couriers WHERE courier_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, courierId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/*package com.example.controllers;

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
*/
package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.models.Admin;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminController extends BaseController {

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
        saveButton.setOnAction(event -> saveAdmin());
        updateButton.setOnAction(event -> updateAdmin());
        deleteButton.setOnAction(event -> deleteAdmin());
    }

    private void saveAdmin() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO Admins (name, phone, delivery_center_id) VALUES (?, ?, ?)";
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

    private void updateAdmin() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        // Assume admin_id is known
        int adminId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "UPDATE Admins SET name = ?, phone = ? WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setInt(3, adminId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAdmin() {
        // Assume admin_id is known
        int adminId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "DELETE FROM Admins WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, adminId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*package com.example.controllers;

import com.example.models.Admin;
import com.example.models.Client;
import com.example.models.Courier;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController {

    private UserController userController;

    public ProfileController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private TextField nameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField addressInput;

    @FXML
    private void initialize() {
        User currentUser = userController.getCurrentUser();
        if (currentUser.getTypeId() == 1) {
            Client currentClient = userController.getClientById(currentUser.getUserId());
            nameInput.setText(currentClient.getName());
            phoneInput.setText(currentClient.getPhone());
            addressInput.setText(currentClient.getAddress());
        } else if (currentUser.getTypeId() == 2) {
            Courier currentCourier = userController.getCurrentCourier();
            nameInput.setText(currentCourier.getName());
            phoneInput.setText(currentCourier.getPhone());
        } else if (currentUser.getTypeId() == 3) {
            Admin currentAdmin = userController.getCurrentAdmin();
            nameInput.setText(currentAdmin.getName());
            phoneInput.setText(currentAdmin.getPhone());
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();

        User currentUser = userController.getCurrentUser();

        boolean success = false;

        if (currentUser.getTypeId() == 1) {
            Client currentClient = userController.getClientById(currentUser.getUserId());
            currentClient.setName(name);
            currentClient.setPhone(phone);
            currentClient.setAddress(address);
            success = userController.updateUser(currentClient);
        } else if (currentUser.getTypeId() == 2) {
            Courier currentCourier = userController.getCurrentCourier();
            currentCourier.setName(name);
            currentCourier.setPhone(phone);
            success = userController.updateUser(currentCourier);
        } else if (currentUser.getTypeId() == 3) {
            Admin currentAdmin = userController.getCurrentAdmin();
            currentAdmin.setName(name);
            currentAdmin.setPhone(phone);
            success = userController.updateUser(currentAdmin);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Данные профиля успешно обновлены!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка обновления данных профиля.");
        }
    }

    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) nameInput.getScene().getWindow();
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
import com.example.models.Client;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController extends BaseController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Button updateButton;

    @FXML
    public void initialize() {
        // Load user data
        loadUserData();

        updateButton.setOnAction(event -> updateUserData());
    }

    private void loadUserData() {
        // Assume user is logged in and we have userId
        int userId = 1; // Replace with actual logged-in user ID

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM Clients WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                phoneField.setText(rs.getString("phone"));
                addressField.setText(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserData() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        // Assume user is logged in and we have userId
        int userId = 1; // Replace with actual logged-in user ID

        try (Connection conn = DBUtil.getConnection()) {
            String query = "UPDATE Clients SET name = ?, phone = ?, address = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

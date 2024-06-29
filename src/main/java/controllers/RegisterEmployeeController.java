package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserType;
import utils.DBUtil;
import utils.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterEmployeeController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> registerEmployee());
        backButton.setOnAction(event -> goBack());
    }

    private void registerEmployee() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String login = loginField.getText();
        String password = HashUtil.hashPassword(passwordField.getText());
        boolean isAdmin = isAdminCheckBox.isSelected();

        int userType = isAdmin ? UserType.ADMIN.ordinal() + 1 : UserType.COURIER.ordinal() + 1;

        try (Connection connection = DBUtil.getConnection()) {
            String query = "INSERT INTO Users (login, password, type_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, userType);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    if (isAdmin) {
                        query = "INSERT INTO Admins (name, phone, address, user_id) VALUES (?, ?, ?, ?)";
                    } else {
                        query = "INSERT INTO Couriers (name, phone, address, user_id) VALUES (?, ?, ?, ?)";
                    }
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, phone);
                    statement.setString(3, address);
                    statement.setInt(4, userId);

                    statement.executeUpdate();
                    showAlert("Success", "Employee registered successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Registration failed!");
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_main_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Admin Main");
            stage.show();
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

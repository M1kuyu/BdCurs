package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private ComboBox<String> deliveryCenterComboBox;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> registerEmployee());
        backButton.setOnAction(event -> goBack());
        loadDeliveryCenters();
    }

    private void loadDeliveryCenters() {
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT name FROM DeliveryCenters";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            ObservableList<String> centers = FXCollections.observableArrayList();
            while (resultSet.next()) {
                centers.add(resultSet.getString("name"));
            }
            deliveryCenterComboBox.setItems(centers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerEmployee() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String login = loginField.getText();
        String password = HashUtil.hashPassword(passwordField.getText());
        boolean isAdmin = isAdminCheckBox.isSelected();
        String selectedCenter = deliveryCenterComboBox.getSelectionModel().getSelectedItem();

        if (selectedCenter == null) {
            showAlert("Error", "Please select a delivery center");
            return;
        }

        int userType = isAdmin ? 3 : 2; // Assuming 3 is for Admin and 2 is for Courier

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

                    query = "SELECT center_id FROM DeliveryCenters WHERE name = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, selectedCenter);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int centerId = resultSet.getInt("center_id");

                        if (isAdmin) {
                            query = "INSERT INTO Admins (name, phone, delivery_center_id, user_id) VALUES (?, ?, ?, ?)";
                        } else {
                            query = "INSERT INTO Couriers (name, phone, delivery_center_id, user_id) VALUES (?, ?, ?, ?)";
                        }
                        statement = connection.prepareStatement(query);
                        statement.setString(1, name);
                        statement.setString(2, phone);
                        statement.setInt(3, centerId);
                        statement.setInt(4, userId);

                        statement.executeUpdate();
                        showAlert("Success", "Employee registered successfully!");
                    }
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

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    private int userId;

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> saveProfile());
        backButton.setOnAction(event -> goBack());

        loadProfile();
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadProfile();
    }

    private void loadProfile() {
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT name, phone, address FROM Clients WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nameField.setText(resultSet.getString("name"));
                phoneField.setText(resultSet.getString("phone"));
                addressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveProfile() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        try (Connection connection = DBUtil.getConnection()) {
            String query = "UPDATE Clients SET name = ?, phone = ?, address = ? WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setInt(4, userId);

            statement.executeUpdate();
            System.out.println("Profile updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Client");
            stage.show();
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

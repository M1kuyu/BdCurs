package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierController {

    @FXML
    private ListView<String> packageListView;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        loadPackages();
        logoutButton.setOnAction(event -> logout());
    }

    private void loadPackages() {
        try (Connection connection = DBUtil.getConnection()) {
            // Assuming courier_id is available somehow (e.g., passed during login)
            int courierId = 1;  // Replace with actual courier ID
            String query = "SELECT * FROM Packages WHERE courier_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courierId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String packageInfo = "ID: " + resultSet.getInt("package_id") +
                        " Weight: " + resultSet.getFloat("weight") +
                        " Type: " + resultSet.getString("type");
                packageListView.getItems().add(packageInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
            stage.show();
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

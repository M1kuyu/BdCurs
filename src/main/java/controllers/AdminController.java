package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private TextField packageIdField;

    @FXML
    private Button searchButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        searchButton.setOnAction(event -> searchPackage());
        logoutButton.setOnAction(event -> logout());
    }

    private void searchPackage() {
        int packageId = Integer.parseInt(packageIdField.getText());

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT * FROM Packages WHERE package_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, packageId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String packageInfo = "ID: " + resultSet.getInt("package_id") + "\n" +
                        "Weight: " + resultSet.getFloat("weight") + "\n" +
                        "Type: " + resultSet.getString("type") + "\n" +
                        "Sender ID: " + resultSet.getInt("sender_id") + "\n" +
                        "Receiver ID: " + resultSet.getInt("receiver_id") + "\n" +
                        "Delivery Center ID: " + resultSet.getInt("delivery_center_id");
                showPackageInfo(packageInfo);
            } else {
                showPackageInfo("Package not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showPackageInfo(String packageInfo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Package Information");
        alert.setHeaderText("Package Details");
        alert.setContentText(packageInfo);
        alert.showAndWait();
    }

    private void logout() {
        // Close current window and return to login window
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

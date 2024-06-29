package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackPackageController {

    @FXML
    private TextField trackingIdField;

    @FXML
    private Button trackButton;

    @FXML
    private ListView<String> statusListView;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        trackButton.setOnAction(event -> trackPackage());
        backButton.setOnAction(event -> goBack());
    }

    private void trackPackage() {
        int packageId = Integer.parseInt(trackingIdField.getText());

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT description, date, name FROM Packages_Statuses " +
                    "JOIN Statuses ON Packages_Statuses.status_id = Statuses.status_id " +
                    "JOIN DeliveryCenters ON Packages_Statuses.center_id = DeliveryCenters.center_id " +
                    "WHERE package_id = ? ORDER BY date ASC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, packageId);

            ResultSet resultSet = statement.executeQuery();
            statusListView.getItems().clear();
            while (resultSet.next()) {
                String status = resultSet.getString("description") + " at " + resultSet.getString("name") + " on " + resultSet.getDate("date");
                statusListView.getItems().add(status);
            }
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

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

public class TrackPackageController {

    @FXML
    private Button trackButton;

    @FXML
    private ListView<String> statusListView;

    @FXML
    private Button backButton;

    private int clientId;

    public void setClientId(int clientId) {
        this.clientId = clientId;
        System.out.println("Client ID set to: " + clientId); // Для отладки
    }

    @FXML
    public void initialize() {
        trackButton.setOnAction(event -> loadClientPackages());
        backButton.setOnAction(event -> goBack());
    }

    private void loadClientPackages() {
        if (clientId <= 0) {
            System.out.println("Invalid Client ID");
            return;
        }

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT P.package_id, S.description, PS.date, DC.name FROM Packages P " +
                    "JOIN Packages_Statuses PS ON P.package_id = PS.package_id " +
                    "JOIN Statuses S ON PS.status_id = S.status_id " +
                    "JOIN DeliveryCenters DC ON PS.center_id = DC.center_id " +
                    "WHERE P.sender_id = ? OR P.receiver_id = ? ORDER BY PS.date ASC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);
            statement.setInt(2, clientId);

            ResultSet resultSet = statement.executeQuery();
            statusListView.getItems().clear();
            while (resultSet.next()) {
                String packageInfo = "Package ID: " + resultSet.getInt("package_id") +
                        ", Status: " + resultSet.getString("description") +
                        ", Date: " + resultSet.getDate("date") +
                        ", Location: " + resultSet.getString("name");
                statusListView.getItems().add(packageInfo);
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

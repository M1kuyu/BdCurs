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
    private int courierId;

    @FXML
    private Button logoutButton;

    public void packagesOfCuries(){
        loadPackages();
    }
    @FXML
    public void initialize(int courierId) {


        logoutButton.setOnAction(event -> logout());
    }

    public void setCourierId(int courierId){this.courierId = courierId;
        System.out.println(courierId + " sasd");}

    private void loadPackages() {
        try (Connection connection = DBUtil.getConnection()) {

            String query = "SELECT package_id, weight, type FROM Packages WHERE courier_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            System.out.println(courierId);
            statement.setInt(1, courierId);

            ResultSet resultSet = statement.executeQuery();
            packageListView.getItems().clear(); // Clear the list before adding new items
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

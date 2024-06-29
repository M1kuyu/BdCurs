package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectCenterController {

    @FXML
    private TextField clientIdField;

    @FXML
    private TextField clientAddressField;

    @FXML
    private ComboBox<String> centerComboBox;

    @FXML
    private TextField centerAddressField;

    @FXML
    private Button loadClientButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        loadClientButton.setOnAction(event -> loadClientInfo());
        centerComboBox.setOnAction(event -> loadCenterAddress());
        saveButton.setOnAction(event -> saveCenter());
        backButton.setOnAction(event -> goBack());

        loadDeliveryCenters();
    }

    private void loadClientInfo() {
        int clientId = Integer.parseInt(clientIdField.getText());

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT address FROM Clients WHERE client_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                clientAddressField.setText(resultSet.getString("address"));
            } else {
                showAlert("Error", "Client not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            centerComboBox.setItems(centers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCenterAddress() {
        String selectedCenter = centerComboBox.getSelectionModel().getSelectedItem();

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT address FROM DeliveryCenters WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedCenter);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                centerAddressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCenter() {
        int clientId = Integer.parseInt(clientIdField.getText());
        String selectedCenter = centerComboBox.getSelectionModel().getSelectedItem();

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT center_id FROM DeliveryCenters WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedCenter);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int centerId = resultSet.getInt("center_id");

                query = "UPDATE Clients SET nearest_delivery_center_id = ? WHERE client_id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, centerId);
                statement.setInt(2, clientId);

                statement.executeUpdate();
                showAlert("Success", "Center updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update center");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}

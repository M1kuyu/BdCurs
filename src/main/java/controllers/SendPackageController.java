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

public class SendPackageController {

    @FXML
    private ComboBox<String> deliveryCenterComboBox;

    @FXML
    private ComboBox<String> packageTypeComboBox;

    @FXML
    private TextField receiverIdField;

    @FXML
    private TextField weightField;

    @FXML
    private Button sendPackageButton;

    @FXML
    private Button backButton;

    private int senderId;

    public void initializeSenderId(int senderId) {
        this.senderId = senderId;
        loadDeliveryCenters();
        loadPackageTypes();
    }

    @FXML
    public void initialize() {
        sendPackageButton.setOnAction(event -> sendPackage());
        backButton.setOnAction(event -> goBack());
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

    private void loadPackageTypes() {
        ObservableList<String> packageTypes = FXCollections.observableArrayList("Regular", "Express");
        packageTypeComboBox.setItems(packageTypes);
    }

    private void sendPackage() {
        String selectedCenter = deliveryCenterComboBox.getSelectionModel().getSelectedItem();
        String packageType = packageTypeComboBox.getSelectionModel().getSelectedItem();
        int receiverId = Integer.parseInt(receiverIdField.getText());
        float weight = Float.parseFloat(weightField.getText());

        try (Connection connection = DBUtil.getConnection()) {
            // Проверяем, существует ли sender_id в таблице Clients
            String query = "SELECT client_id FROM Clients WHERE client_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, senderId);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Invalid sender ID");
                System.out.println(senderId);
                return;
            }

            // Получаем ID центра доставки по имени
            query = "SELECT center_id FROM DeliveryCenters WHERE name = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, selectedCenter);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int deliveryCenterId = resultSet.getInt("center_id");

                query = "INSERT INTO Packages (weight, type, sender_id, receiver_id, delivery_center_id, sender_center_id) VALUES (?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setFloat(1, weight);
                statement.setString(2, packageType);
                statement.setInt(3, senderId);
                statement.setInt(4, receiverId);
                statement.setInt(5, deliveryCenterId);
                statement.setInt(6, deliveryCenterId);

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int packageId = generatedKeys.getInt(1);
                        System.out.println("Package sent successfully");
                        showPackageId(packageId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showPackageId(int packageId) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Package Sent");
        alert.setHeaderText("Package sent successfully!");
        alert.setContentText("Your Package ID is: " + packageId);
        alert.showAndWait();
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

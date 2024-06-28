package com.example.Views;

import com.example.controllers.MainController;
import com.example.controllers.PackageController;
import com.example.controllers.UserController;
import com.example.models.Client;
import com.example.models.Package;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class SendPackageView extends Application {

    private SendPackageController sendPackageController;
    private UserController userController;

    public SendPackageView(UserController userController) {
        this.sendPackageController = new SendPackageController();
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Отправить посылку");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Получатели
        Label receiverLabel = new Label("Получатель:");
        GridPane.setConstraints(receiverLabel, 0, 0);
        ComboBox<Client> receiverComboBox = new ComboBox<>();
        List<Client> clients = userController.getAllClients();
        ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
        receiverComboBox.setItems(clientList);
        GridPane.setConstraints(receiverComboBox, 1, 0);

        // Центр отправки
        Label centerLabel = new Label("Центр отправки:");
        GridPane.setConstraints(centerLabel, 0, 1);
        ComboBox<String> centerComboBox = new ComboBox<>();
        List<String> centers = userController.getAllDeliveryCenters();
        ObservableList<String> centerList = FXCollections.observableArrayList(centers);
        centerComboBox.setItems(centerList);
        GridPane.setConstraints(centerComboBox, 1, 1);

        // Тип доставки
        Label typeLabel = new Label("Тип доставки:");
        GridPane.setConstraints(typeLabel, 0, 2);
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Обычная", "Срочная");
        GridPane.setConstraints(typeComboBox, 1, 2);

        // Вес посылки
        Label weightLabel = new Label("Вес посылки:");
        GridPane.setConstraints(weightLabel, 0, 3);
        TextField weightInput = new TextField();
        GridPane.setConstraints(weightInput, 1, 3);

        // Кнопка отправки
        Button sendButton = new Button("Отправить посылку");
        GridPane.setConstraints(sendButton, 1, 4);
        sendButton.setOnAction(e -> {
            Client receiver = receiverComboBox.getValue();
            String type = typeComboBox.getValue();
            double weight = Double.parseDouble(weightInput.getText());
            String center = centerComboBox.getValue();
            int senderCenterId = userController.getCurrentUser().getNearestDeliveryCenterId();
            int receiverCenterId = receiver.getNearestDeliveryCenterId();

            // Создание объекта посылки
            Package aPackage = new Package(0, weight, type, userController.getCurrentUser().getUserId(), receiver.getClientId(), senderCenterId, receiverCenterId, 0);

            // Отправка посылки и получение объекта с ID
            Package sentPackage = sendPackageController.sendPackage(aPackage);

            if (sentPackage != null) {
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Посылка успешно отправлена! Номер посылки: " + sentPackage.getPackageId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка отправки посылки.");
            }
        });

        // Кнопка назад
        Button backButton = new Button("Назад");
        GridPane.setConstraints(backButton, 1, 5);
        backButton.setOnAction(e -> new MainController(userController).handleBackButtonAction(primaryStage));

        grid.getChildren().addAll(receiverLabel, receiverComboBox, centerLabel, centerComboBox, typeLabel, typeComboBox, weightLabel, weightInput, sendButton, backButton);

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

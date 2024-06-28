package com.example.Views;

import com.example.controllers.SendPackageController;
import com.example.controllers.UserController;
import com.example.models.Package;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

        // Центр отправки
        Label centerLabel = new Label("Центр отправки:");
        GridPane.setConstraints(centerLabel, 0, 0);
        ComboBox<String> centerComboBox = new ComboBox<>();
        GridPane.setConstraints(centerComboBox, 1, 0);

        // Тип доставки
        Label typeLabel = new Label("Тип доставки:");
        GridPane.setConstraints(typeLabel, 0, 1);
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Обычная", "Срочная");
        GridPane.setConstraints(typeComboBox, 1, 1);

        // Имя получателя
        Label receiverLabel = new Label("Имя получателя:");
        GridPane.setConstraints(receiverLabel, 0, 2);
        TextField receiverInput = new TextField();
        GridPane.setConstraints(receiverInput, 1, 2);

        // Вес посылки
        Label weightLabel = new Label("Вес посылки:");
        GridPane.setConstraints(weightLabel, 0, 3);
        TextField weightInput = new TextField();
        GridPane.setConstraints(weightInput, 1, 3);

        // Адрес получателя
        Label addressLabel = new Label("Адрес получателя:");
        GridPane.setConstraints(addressLabel, 0, 4);
        TextField addressInput = new TextField();
        GridPane.setConstraints(addressInput, 1, 4);

        // Кнопка отправки
        Button sendButton = new Button("Отправить посылку");
        GridPane.setConstraints(sendButton, 1, 5);
        sendButton.setOnAction(e -> {
            String center = centerComboBox.getValue();
            String type = typeComboBox.getValue();
            String receiver = receiverInput.getText();
            double weight = Double.parseDouble(weightInput.getText());
            String address = addressInput.getText();

            // Создание объекта посылки
            Package aPackage = new Package(0, weight, type, userController.getCurrentUser().getUserId(), receiver, center, address, 0);

            // Отправка посылки и получение объекта с ID
            Package sentPackage = sendPackageController.sendPackage(aPackage);

            if (sentPackage != null) {
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Посылка успешно отправлена! Номер посылки: " + sentPackage.getPackageId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка отправки посылки.");
            }
        });

        grid.getChildren().addAll(centerLabel, centerComboBox, typeLabel, typeComboBox, receiverLabel, receiverInput, weightLabel, weightInput, addressLabel, addressInput, sendButton);

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

package com.example.Views;

import com.example.controllers.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.models.User;

public class ProfileView extends Application {

    private UserController userController;

    public ProfileView(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Профиль");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Имя
        Label nameLabel = new Label("Имя:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        // Телефон
        Label phoneLabel = new Label("Телефон:");
        GridPane.setConstraints(phoneLabel, 0, 1);
        TextField phoneInput = new TextField();
        GridPane.setConstraints(phoneInput, 1, 1);

        // Адрес
        Label addressLabel = new Label("Адрес:");
        GridPane.setConstraints(addressLabel, 0, 2);
        TextField addressInput = new TextField();
        GridPane.setConstraints(addressInput, 1, 2);

        // Кнопка сохранения изменений
        Button saveButton = new Button("Сохранить изменения");
        GridPane.setConstraints(saveButton, 1, 3);
        saveButton.setOnAction(e -> {
            String name = nameInput.getText();
            String phone = phoneInput.getText();
            String address = addressInput.getText();

            User currentUser = userController.getCurrentUser();
            currentUser.setName(name);
            currentUser.setPhone(phone);
            currentUser.setAddress(address);

            boolean success = userController.updateUser(currentUser);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Данные профиля успешно обновлены!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка обновления данных профиля.");
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, phoneLabel, phoneInput, addressLabel, addressInput, saveButton);

        Scene scene = new Scene(grid, 300, 200);
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

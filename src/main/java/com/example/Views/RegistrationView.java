package com.example.views;

import com.example.controllers.RegistrationController;
import com.example.controllers.UserController;
import com.example.models.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrationView extends Application {

    private UserController userController;
    private RegistrationController registrationController;

    public RegistrationView(UserController userController) {
        this.userController = userController;
        this.registrationController = new RegistrationController();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Регистрация");

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

        // Пароль
        Label passwordLabel = new Label("Пароль:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 3);

        // Подтверждение пароля
        Label confirmPasswordLabel = new Label("Подтвердите пароль:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 4);
        PasswordField confirmPasswordInput = new PasswordField();
        GridPane.setConstraints(confirmPasswordInput, 1, 4);

        // Кнопка регистрации
        Button registerButton = new Button("Регистрация");
        GridPane.setConstraints(registerButton, 1, 5);
        registerButton.setOnAction(e -> {
            String name = nameInput.getText();
            String phone = phoneInput.getText();
            String address = addressInput.getText();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();

            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Пароли не совпадают!");
                return;
            }

            User user = new User(0, name, password, 1); // Пример: 1 - это ID типа пользователя (например, клиент)
            boolean success = registrationController.registerUser(user);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Пользователь успешно зарегистрирован!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка регистрации пользователя.");
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, phoneLabel, phoneInput, addressLabel, addressInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, registerButton);

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

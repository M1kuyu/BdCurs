package com.example.Views;

import com.example.controllers.UserController;
import com.example.models.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends Application {

    private UserController userController;

    public LoginView(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Вход");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Логин
        Label loginLabel = new Label("Логин:");
        GridPane.setConstraints(loginLabel, 0, 0);
        TextField loginInput = new TextField();
        GridPane.setConstraints(loginInput, 1, 0);

        // Пароль
        Label passwordLabel = new Label("Пароль:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        // Кнопка входа
        Button loginButton = new Button("Вход");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String login = loginInput.getText();
            String password = passwordInput.getText();

            User user = userController.loginUser(login, password);

            if (user != null) {
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Успешный вход в систему!");
                // Переход на главное окно в зависимости от роли пользователя
                if (user.getTypeId() == 1) {
                    new MainView(userController).start(primaryStage);
                } else if (user.getTypeId() == 2) {
                    new CourierView(userController).start(primaryStage);
                } else if (user.getTypeId() == 3) {
                    new AdminView(userController).start(primaryStage);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Неправильный логин или пароль.");
            }
        });

        grid.getChildren().addAll(loginLabel, loginInput, passwordLabel, passwordInput, loginButton);

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

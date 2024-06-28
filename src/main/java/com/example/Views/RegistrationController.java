package com.example.Views;

import com.example.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.models.User;

public class RegistrationController {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField addressInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField confirmPasswordInput;

    private UserController userController;

    public RegistrationController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private void handleRegisterButtonAction() {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Пароли не совпадают!");
            return;
        }

        User user = new User(0, password, name, 1); // Пример: 1 - это ID типа пользователя (например, клиент)
        boolean success = userController.registerUser(user);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Пользователь успешно зарегистрирован!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка регистрации пользователя.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

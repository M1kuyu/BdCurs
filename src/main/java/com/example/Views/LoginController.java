package com.example.Views;


import com.example.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.models.User;

public class LoginController {

    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passwordInput;

    private UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private void handleLoginButtonAction() {
        String login = loginInput.getText();
        String password = passwordInput.getText();

        User user = userController.loginUser(login, password);

        if (user != null) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Успешный вход в систему!");
            // Переход на главное окно в зависимости от роли пользователя
            // Замените запуск нового окна на правильный контроллер
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Неправильный логин или пароль.");
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

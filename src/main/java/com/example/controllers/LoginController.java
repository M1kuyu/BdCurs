package com.example.controllers;

import com.example.Views.LoginView;
import com.example.Views.MainView;
import com.example.Views.RegistrationView;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserController userController;

    public LoginController() {
        this.userController = new UserController();
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userController.loginUser(username, password);
        if (user != null) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            new MainView(userController).start(stage);
        } else {
            // Ошибка авторизации
        }
    }

    @FXML
    public void handleRegister() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        new RegistrationView(userController).start(stage);
    }
}

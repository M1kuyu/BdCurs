// src/main/java/com/example/controllers/LoginController.java
package com.example.controllers;

import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserController userController = new UserController();
        User user = userController.login(username, password);
        if (user != null) {
            // Успешная авторизация
        } else {
            // Ошибка авторизации
        }
    }
}

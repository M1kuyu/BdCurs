/*package com.example.controllers;

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
}*/
package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController extends BaseController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM Users WHERE login = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Login successful, redirect to appropriate view based on user type
            } else {
                // Show error message
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


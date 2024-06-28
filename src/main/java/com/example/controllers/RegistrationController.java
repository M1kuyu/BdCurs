/*package com.example.controllers;

import com.example.Views.LoginView;
import com.example.models.User;
import com.example.utils.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    public boolean registerUser(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean registered = false;

        try {
            connection = DBUtil.getConnection();
            String query = "INSERT INTO Users (login, password, type_id) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getTypeId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                registered = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return registered;
    }

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

        User user = new User(0, name, password, 1); // Пример: 1 - это ID типа пользователя (например, клиент)
        boolean success = registerUser(user);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Пользователь успешно зарегистрирован!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка регистрации пользователя.");
        }
    }

    @FXML
    private void handleBackToLoginButtonAction() {
        Stage stage = (Stage) nameInput.getScene().getWindow();
        new LoginView(userController).start(stage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}*/

package com.example.controllers;

import com.example.controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController extends BaseController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> register());
    }

    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (!password.equals(confirmPassword)) {
            // Show error: Passwords do not match
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String insertUserQuery = "INSERT INTO Users (login, password) VALUES (?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, password); // Hash password before storing
            userStmt.executeUpdate();

            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                String insertClientQuery = "INSERT INTO Clients (name, phone, address, user_id) VALUES (?, ?, ?, ?)";
                PreparedStatement clientStmt = conn.prepareStatement(insertClientQuery);
                clientStmt.setString(1, name);
                clientStmt.setString(2, phone);
                clientStmt.setString(3, address);
                clientStmt.setInt(4, userId);
                clientStmt.executeUpdate();

                // Registration successful, redirect to login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


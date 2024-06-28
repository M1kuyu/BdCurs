package com.example.controllers;

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
}

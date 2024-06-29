package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserType;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.UserType.ADMIN;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
        registerButton.setOnAction(event -> register());
    }

    private void login() {
        String login = loginField.getText();
        String password = passwordField.getText();
        String hashedPassword = utils.HashUtil.hashPassword(password);

        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT user_id, type_id FROM Users WHERE login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int userTypeId = resultSet.getInt("type_id");
                UserType userType = UserType.values()[userTypeId - 1];

                switch (userType) {
                    case CLIENT:
                        openClientView(userId);
                        break;
                    case COURIER:
                        openCourierView(userId);
                        break;
                    case ADMIN:
                        openAdminMainView(userId);
                        break;
                }
            } else {
                System.out.println("Invalid login or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register() {
        // Логика регистрации
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Registration");
            stage.show();
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openClientView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Client");
            ClientController controller = loader.getController();
            controller.setUserId(userId);
            stage.show();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCourierView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/courier_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Courier");

            CourierController courierController = loader.getController();
            courierController.setCourierId(userId);
            courierController.packagesOfCuries();

            stage.show();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAdminMainView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_main_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Admin Main");
            stage.show();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

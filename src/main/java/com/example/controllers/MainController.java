/*ackage com.example.controllers;

import com.example.Views.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import com.example.models.Client;
import com.example.models.Courier;

import com.example.models.Admin;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MainController {

    private UserController userController;

    public MainController(UserController userController) {
        this.userController = userController;
    }

    public Client getClientByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Client client = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Clients WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("nearest_delivery_center_id"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return client;
    }

    public Courier getCourierByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Courier courier = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Couriers WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                courier = new Courier(
                        rs.getInt("courier_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("delivery_center_id"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courier;
    }

    public Admin getAdminByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Admins WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("delivery_center_id"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return admin;
    }


    @FXML
    public void handleProfileButtonAction() {
        try {
            Stage stage = new Stage();
            new ProfileView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно профиля.");
        }
    }

    @FXML
    public void handleSendPackageButtonAction() {
        try {
            Stage stage = new Stage();
            new SendPackageView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно отправки посылки.");
        }
    }

    @FXML
    public void handleViewPackagesButtonAction() {
        try {
            Stage stage = new Stage();
            new ViewPackagesView(userController).start(stage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть окно просмотра посылок.");
        }
    }

    @FXML
    public void handleLogoutButtonAction() {
        Stage stage = (Stage) userController.getCurrentUser().getStage();
        new LoginView(userController).start(stage);
    }

    @FXML
    public void handleBackButtonAction(Stage primaryStage) {
        new MainView(userController).start(primaryStage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/
package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.views.LoginView;
import com.example.views.RegistrationView;
import com.example.views.SendPackageView;
import com.example.views.ProfileView;

public class MainController extends BaseController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button sendPackageButton;
    @FXML
    private Button profileButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> showLoginView());
        registerButton.setOnAction(event -> showRegistrationView());
        sendPackageButton.setOnAction(event -> showSendPackageView());
        profileButton.setOnAction(event -> showProfileView());
    }

    private void showLoginView() {
        new LoginView(stage).show();
    }

    private void showRegistrationView() {
        new RegistrationView(stage).show();
    }

    private void showSendPackageView() {
        new SendPackageView(stage).show();
    }

    private void showProfileView() {
        new ProfileView(stage).show();
    }
}

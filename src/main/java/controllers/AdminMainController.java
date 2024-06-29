package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMainController {

    @FXML
    private Button managePackagesButton;

    @FXML
    private Button registerEmployeeButton;

    @FXML
    private Button selectCenterButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        managePackagesButton.setOnAction(event -> openManagePackages());
        registerEmployeeButton.setOnAction(event -> openRegisterEmployee());
        selectCenterButton.setOnAction(event -> openSelectCenter());
        logoutButton.setOnAction(event -> logout());
    }

    private void openManagePackages() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Manage Packages");
            stage.show();
            Stage currentStage = (Stage) managePackagesButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openRegisterEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register_employee_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Register Employee");
            stage.show();
            Stage currentStage = (Stage) registerEmployeeButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSelectCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/select_center_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Select Delivery Center");
            stage.show();
            Stage currentStage = (Stage) selectCenterButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
            stage.show();
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

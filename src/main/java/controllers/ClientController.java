package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private Button profileButton;

    @FXML
    private Button sendPackageButton;

    @FXML
    private Button trackPackageButton;

    @FXML
    private Button logoutButton;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    public void initialize() {
        profileButton.setOnAction(event -> openProfile());
        sendPackageButton.setOnAction(event -> openSendPackage());
        trackPackageButton.setOnAction(event -> openTrackPackage());
        logoutButton.setOnAction(event -> logout());
    }

    private void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Profile");

            ProfileController controller = loader.getController();
            controller.setUserId(userId);

            stage.show();
            Stage currentStage = (Stage) profileButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSendPackage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/send_package_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Send Package");

            SendPackageController controller = loader.getController();
            controller.initializeSenderId(userId);

            stage.show();
            Stage currentStage = (Stage) sendPackageButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTrackPackage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/track_package_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Track Package");

            TrackPackageController controller = loader.getController();
            controller.setClientId(userId);

            stage.show();
            Stage currentStage = (Stage) trackPackageButton.getScene().getWindow();
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

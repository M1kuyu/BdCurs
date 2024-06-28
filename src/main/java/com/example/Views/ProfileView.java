package com.example.Views;

import com.example.controllers.ProfileController;
import com.example.controllers.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileView extends Application {

    private UserController userController;

    public ProfileView(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile_view.fxml"));
            loader.setController(new ProfileController(userController));
            GridPane grid = loader.load();

            Scene scene = new Scene(grid, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Профиль");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

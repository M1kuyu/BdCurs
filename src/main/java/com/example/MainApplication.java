package com.example;

import com.example.controllers.UserController;
import com.example.Views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private UserController userController;

    @Override
    public void start(Stage primaryStage) {
        userController = new UserController();
        showLoginView(primaryStage);
    }

    private void showLoginView(Stage primaryStage) {
        LoginView loginView = new LoginView(userController);
        loginView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

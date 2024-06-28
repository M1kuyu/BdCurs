package com.example.Views;

import com.example.controllers.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

    private UserController userController;

    public MainView(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Главное окно");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        // Кнопка профиля
        Button profileButton = new Button("Профиль");
        profileButton.setOnAction(e -> new ProfileView(userController).start(primaryStage));

        // Кнопка отправки посылки
        Button sendPackageButton = new Button("Отправить посылку");
        sendPackageButton.setOnAction(e -> new SendPackageView(userController).start(primaryStage));

        vbox.getChildren().addAll(profileButton, sendPackageButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

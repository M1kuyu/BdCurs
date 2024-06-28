package com.example.Views;

import com.example.controllers.ProfileController;
import com.example.controllers.UserController;
import com.example.models.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProfileView extends Application {

    private UserController userController;

    public ProfileView(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Профиль");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        ProfileController profileController = new ProfileController(userController);

        // Имя
        Label nameLabel = new Label("Имя:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField(userController.getCurrentUser().getName());
        GridPane.setConstraints(nameInput, 1, 0);

        // Телефон
        Label phoneLabel = new Label("Телефон:");
        GridPane.setConstraints(phoneLabel, 0, 1);
        TextField phoneInput = new TextField(userController.getCurrentUser().getPhone());
        GridPane.setConstraints(phoneInput, 1, 1);

        // Адрес
        Label addressLabel = new Label("Адрес:");
        GridPane.setConstraints(addressLabel, 0, 2);
        TextField addressInput = new TextField(userController.getCurrentUser().getAddress());
        GridPane.setConstraints(addressInput, 1, 2);

        // Кнопка сохранения изменений
        Button saveButton = new Button("Сохранить изменения");
        GridPane.setConstraints(saveButton, 1, 3);
        saveButton.setOnAction(e -> profileController.handleSaveButtonAction(nameInput.getText(), phoneInput.getText(), addressInput.getText()));

        grid.getChildren().addAll(nameLabel, nameInput, phoneLabel, phoneInput, addressLabel, addressInput, saveButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.example.Views;

import com.example.controllers.MainController;
import com.example.controllers.PackageController;
import com.example.controllers.UserController;
import com.example.models.Package;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ViewPackagesView extends Application {

    private PackageController packageController;
    private UserController userController;

    public ViewPackagesView(UserController userController) {
        this.packageController = new PackageController();
        this.userController = userController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Мои посылки");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Список посылок
        ListView<String> listView = new ListView<>();
        List<Package> packages = packageController.getPackagesByUserId(userController.getCurrentUser().getUserId());
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Package aPackage : packages) {
            items.add("ID: " + aPackage.getPackageId() + ", Статус: " + aPackage.getStatus());
        }
        listView.setItems(items);
        GridPane.setConstraints(listView, 0, 0);

        // Кнопка назад
        Button backButton = new Button("Назад");
        GridPane.setConstraints(backButton, 0, 1);
        backButton.setOnAction(e -> new MainController(userController).handleBackButtonAction(primaryStage));

        grid.getChildren().addAll(listView, backButton);

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

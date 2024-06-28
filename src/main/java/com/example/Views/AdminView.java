package com.example.Views;


import com.example.controllers.PackageController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.models.Package;

public class AdminView extends Application {

    private PackageController packageController;

    public AdminView(PackageController packageController) {
        this.packageController = packageController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Администратор");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Номер посылки
        Label packageIdLabel = new Label("Номер посылки:");
        GridPane.setConstraints(packageIdLabel, 0, 0);
        TextField packageIdInput = new TextField();
        GridPane.setConstraints(packageIdInput, 1, 0);

        // Кнопка поиска
        Button searchButton = new Button("Поиск");
        GridPane.setConstraints(searchButton, 1, 1);
        searchButton.setOnAction(e -> {
            int packageId = Integer.parseInt(packageIdInput.getText());
            Package aPackage = packageController.getPackageById(packageId);

            if (aPackage != null) {
                String message = "ID: " + aPackage.getPackageId()
                        + ", Отправитель: " + aPackage.getSender()
                        + ", Получатель: " + aPackage.getReceiver()
                        + ", Вес: " + aPackage.getWeight()
                        + ", Тип: " + aPackage.getType();
                showAlert(Alert.AlertType.INFORMATION, "Посылка", message);
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Посылка не найдена.");
            }
        });

        grid.getChildren().addAll(packageIdLabel, packageIdInput, searchButton);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

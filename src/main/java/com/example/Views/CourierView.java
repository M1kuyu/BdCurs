package com.example.Views;


import com.example.controllers.PackageController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.models.Package;

import java.util.List;

public class CourierView extends Application {

    private PackageController packageController;

    public CourierView(PackageController packageController) {
        this.packageController = packageController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Курьер");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Центр доставки
        Label centerLabel = new Label("Центр доставки:");
        GridPane.setConstraints(centerLabel, 0, 0);
        ComboBox<String> centerComboBox = new ComboBox<>();
        GridPane.setConstraints(centerComboBox, 1, 0);

        // Кнопка загрузки посылок
        Button loadButton = new Button("Загрузить посылки");
        GridPane.setConstraints(loadButton, 1, 1);
        loadButton.setOnAction(e -> {
            String center = centerComboBox.getValue();
            List<Package> packages = packageController.getPackagesByCenter(center);

            if (packages != null) {
                StringBuilder sb = new StringBuilder();
                for (Package aPackage : packages) {
                    sb.append("ID: ").append(aPackage.getPackageId())
                            .append(", Отправитель: ").append(aPackage.getSender())
                            .append(", Получатель: ").append(aPackage.getReceiver())
                            .append(", Вес: ").append(aPackage.getWeight())
                            .append(", Тип: ").append(aPackage.getType())
                            .append("\n");
                }
                showAlert(Alert.AlertType.INFORMATION, "Посылки", sb.toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка загрузки посылок.");
            }
        });

        grid.getChildren().addAll(centerLabel, centerComboBox, loadButton);

        Scene scene = new Scene(grid, 300, 200);
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

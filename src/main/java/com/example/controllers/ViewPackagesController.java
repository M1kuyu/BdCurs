package com.example.controllers;

import com.example.models.Package;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class ViewPackagesController {

    @FXML
    private ListView<String> listView;

    private UserController userController;
    private PackageController packageController;

    public ViewPackagesController(UserController userController) {
        this.userController = userController;
        this.packageController = new PackageController();
    }

    @FXML
    public void initialize() {
        List<Package> packages = packageController.getPackagesByUserId(userController.getCurrentUser().getUserId());
        for (Package aPackage : packages) {
            listView.getItems().add("ID: " + aPackage.getPackageId() + ", Статус: " + aPackage.getStatus());
        }
    }

    @FXML
    public void handleBackButtonAction() {
        Stage stage = (Stage) listView.getScene().getWindow();
        new MainController(userController).handleBackButtonAction(stage);
    }
}

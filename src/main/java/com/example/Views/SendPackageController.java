package com.example.Views;


import com.example.controllers.PackageController;
import com.example.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.example.models.Package;

public class SendPackageController {

    @FXML
    private ComboBox<String> centerComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField receiverInput;
    @FXML
    private TextField weightInput;
    @FXML
    private TextField addressInput;

    private PackageController packageController;
    private UserController userController;

    public SendPackageController(PackageController packageController, UserController userController) {
        this.packageController = packageController;
        this.userController = userController;
    }

    @FXML
    private void handleSendButtonAction() {
        String center = centerComboBox.getValue();
        String type = typeComboBox.getValue();
        String receiver = receiverInput.getText();
        double weight = Double.parseDouble(weightInput.getText());
        String address = addressInput.getText();

        Package aPackage = new Package(0, weight, type, userController.getCurrentUser().getUserId(), receiver, center, address);
        boolean success = packageController.sendPackage(aPackage);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Посылка успешно отправлена!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка отправки посылки.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

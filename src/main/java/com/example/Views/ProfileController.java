package com.example.Views;


import com.example.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.example.models.User;

public class ProfileController {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField addressInput;

    private UserController userController;

    public ProfileController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private void handleSaveButtonAction() {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();

        User currentUser = userController.getCurrentUser();
        currentUser.setName(name);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);

        boolean success = userController.updateUser(currentUser);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Данные профиля успешно обновлены!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка обновления данных профиля.");
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

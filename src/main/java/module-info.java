module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens main to javafx.graphics;
    opens controllers to javafx.fxml;

}

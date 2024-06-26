module com.example.curs_bd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.curs_bd to javafx.fxml;
    exports com.example.curs_bd;
}
module com.example.curs_bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires eu.hansolo.fx.heatmap;

    opens com.example.curs_bd to javafx.fxml;
    exports com.example.curs_bd;
}

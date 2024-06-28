module com.example.curs_bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires eu.hansolo.fx.heatmap;
    requires java.sql;

    opens com.example.curs_bd to javafx.fxml;

}

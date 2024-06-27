package com.example.curs_bd;

import eu.hansolo.fx.heatmap.HeatMap;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    public void initialize() {
        HeatMap heatMap = new HeatMap();
        // Настройка heatMap
        rootPane.getChildren().add(heatMap);
    }
}

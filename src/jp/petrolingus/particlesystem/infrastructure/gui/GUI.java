package jp.petrolingus.particlesystem.infrastructure.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jp.petrolingus.particlesystem.Main;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class GUI extends BorderPane {
    
    private static final Logger log = LoggerFactory.getLogger(GUI.class);

    @FXML
    private BorderPane contentContainer;

    @FXML
    private TextField particlesCount;

    @FXML
    private TextField radius;

    @FXML
    private TextField startVelocity;

    @FXML
    private VBox chartContainer;

    public GUI(Node content) throws IOException {
        log.debug("Create GUI");

        URL location = this.getClass().getResource("gui.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        VBox.setVgrow(chart, Priority.ALWAYS);
        chartContainer.getChildren().add(chart);

        contentContainer.setCenter(content);
    }

    @FXML
    private void onStart() {
        log.info("onStart");
    }

    @FXML
    private void onStop() {
        log.info("onStop");
    }
}

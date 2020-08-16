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

import java.io.IOException;
import java.net.URL;

public class GUI extends BorderPane {

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
        System.out.println("Create GUI");

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
        System.out.println("onStart");
    }

    @FXML
    private void onStop() {
        System.out.println("onStop");
    }
}

package jp.petrolingus.particlesystem;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.petrolingus.particlesystem.infrastructure.gui.GUI;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.io.IOException;

// TODO: 17.08.2020 Limit number of events by limit area there particles collision
public class Main extends Application {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	private Parent root;
	
	@Override
	public void init() throws IOException {
		log.info("Initialize Main");
		root = new GUI();
	}
	
	@Override
	public void start(Stage primaryStage) {
		log.info("Start Main");
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		log.info("Hello ParticleSystem!");
		launch(args);
	}
}

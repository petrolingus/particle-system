package jp.petrolingus.particlesystem.infrastructure.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.domain.algorithmes.Algorithm;
import jp.petrolingus.particlesystem.domain.algorithmes.DefaultAlgorithm;
import jp.petrolingus.particlesystem.infrastructure.generators.Generator;
import jp.petrolingus.particlesystem.infrastructure.generators.RandomGenerator;
import jp.petrolingus.particlesystem.infrastructure.render.DefaultRenderer;
import jp.petrolingus.particlesystem.infrastructure.render.Renderer;
import jp.petrolingus.particlesystem.infrastructure.simulation.DefaultSimulation;
import jp.petrolingus.particlesystem.infrastructure.simulation.Simulation;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;
import jp.petrolingus.particlesystem.util.uicomponent.UiComponentLoader;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class GUI extends BorderPane {
	
	private static final Logger log = LoggerFactory.getLogger(GUI.class);
	
	@FXML
	private BorderPane simulationContainer;
	
	@FXML
	private BorderPane chartContainer;
	
	@FXML
	private BorderPane settingsContainer;
	
	@FXML
	private Button start;
	
	@FXML
	private Button stop;
	
	@FXML
	private Button resume;
	
	@FXML
	private Button clear;
	
	
	private final AtomicReference<Optional<Simulation>> simulation = new AtomicReference<>(Optional.empty());
	private final ParticleSimulationSettings simulationSettings = new ParticleSimulationSettings();
	
	
	public GUI(Node content) {
		log.debug("Create GUI");
		
		UiComponentLoader.load(this, "gui.fxml");
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		
		BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
		VBox.setVgrow(chart, Priority.ALWAYS);
		
		chartContainer.setCenter(chart);
//		this.simulationContainer.setCenter(content);
		settingsContainer.setCenter(simulationSettings);
		
		start.setDisable(false);
		stop.setDisable(true);
		resume.setDisable(true);
		clear.setDisable(false);
		
		start.requestFocus();
	}
	
	@FXML
	private void onStart() {
		log.trace("onStart");
		simulationSettings.setDisable(true);
		start.setDisable(true);
		stop.setDisable(false);
		resume.setDisable(true);
		clear.setDisable(true);
		new Thread(this::start).start();
	}
	
	private void start() {
		ParticleSimulationSettings s = this.simulationSettings;
		
		Generator generator = new RandomGenerator(s.width(), s.height(), s.shift());
		List<Particle> particles = generator.generate(s.n(), s.radius(), s.startVelocity(), s.attempts());
		Algorithm algorithm = new DefaultAlgorithm(s.width(), s.height(), s.dt(), s.radius(), particles);
		
		Platform.runLater(() -> {
			
			Canvas canvas = new Canvas(s.width(), s.height());
			simulationContainer.setCenter(canvas);
			
			GraphicsContext g = canvas.getGraphicsContext2D();
			Renderer renderer = new DefaultRenderer(s.width(), s.height(), g, particles);
			
			Simulation simulation = new DefaultSimulation(algorithm, renderer);
			simulation.start();
			this.simulation.set(Optional.of(simulation));
			
		});
	}
	
	@FXML
	private void onResume() {
		log.trace("onResume");
		simulationSettings.setDisable(true);
		start.setDisable(true);
		stop.setDisable(false);
		resume.setDisable(false);
		clear.setDisable(true);
		simulation.get().ifPresent(Simulation::start);
	}
	
	@FXML
	private void onStop() {
		log.trace("onStop");
		simulationSettings.setDisable(false);
		start.setDisable(false);
		stop.setDisable(true);
		resume.setDisable(false);
		clear.setDisable(false);
		simulation.get().ifPresent(Simulation::stop);
	}
	
	@FXML
	private void onClear() {
		log.trace("onClear");
		resume.setDisable(true);
		simulation.get().ifPresent(Simulation::clear);
	}
}

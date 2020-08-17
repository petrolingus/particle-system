package jp.petrolingus.particlesystem.infrastructure.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;
import jp.petrolingus.particlesystem.util.uicomponent.UiComponentLoader;

public class ParticleSimulationSettings extends BorderPane {
	
	private static final Logger log = LoggerFactory.getLogger(ParticleSimulationSettings.class);
	
	@FXML
	private TextField width;
	
	@FXML
	private TextField height;
	
	@FXML
	private TextField radius;
	
	@FXML
	private TextField attempts;
	
	@FXML
	private TextField startVelocity;
	
	@FXML
	private TextField n;
	
	@FXML
	private TextField dt;
	
	
	//	private final TextField shift;
	
	public ParticleSimulationSettings() {
		
		log.info("Create ParticleSimulationSettings");
		
		UiComponentLoader.load(this, "particle-simulation-settings.fxml");
		
		width.setText("640");
		height.setText("480");
		radius.setText("10");
		attempts.setText("100");
		startVelocity.setText("100000");
		n.setText("1000");
		dt.setText("0.00001");
		
	}
	
	public int width() {
		return Integer.parseInt(width.getText());
	}
	
	public int height() {
		return Integer.parseInt(height.getText());
	}
	
	public int shift() {
		return radius() * 2;
	}
	
	public int radius() {
		return Integer.parseInt(radius.getText());
	}
	
	public int attempts() {
		return Integer.parseInt(attempts.getText());
	}
	
	public double startVelocity() {
		return Double.parseDouble(startVelocity.getText());
	}
	
	public int n() {
		return Integer.parseInt(n.getText());
	}
	
	public double dt() {
		return Double.parseDouble(dt.getText());
	}
	
}

package jp.petrolingus.particlesystem.util.uicomponent;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class UiComponentLoader {
	
	public static void load(Object component, String path) {
		ClassLoader classLoader = UiComponentLoader.class.getClassLoader();
		URL location = classLoader.getResource("assets/" + path);
		FXMLLoader loader = new FXMLLoader();
		loader.setClassLoader(classLoader);
		loader.setLocation(location);
		loader.setController(component);
		loader.setRoot(component);
		
		try {
			loader.load();
		} catch (IOException e) {
			throw new ComponentLoadingException("Could not load component: " + path, e);
		}
	}
	
}

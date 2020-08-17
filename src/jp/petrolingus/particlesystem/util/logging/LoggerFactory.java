package jp.petrolingus.particlesystem.util.logging;

import java.time.format.DateTimeFormatter;

public class LoggerFactory {
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getSimpleName());
	}
	
	public static Logger getLogger(String name) {
		return new SimpleConsoleLogger(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"));
	}
}

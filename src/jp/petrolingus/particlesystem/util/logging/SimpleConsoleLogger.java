package jp.petrolingus.particlesystem.util.logging;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class SimpleConsoleLogger implements Logger {
	
	private final DateTimeFormatter formatter;
	
	public SimpleConsoleLogger(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}
	
	@Override
	public void trace(String msg) {
		log(Level.TRACE, msg);
	}
	
	@Override
	public void debug(String msg) {
		log(Level.DEBUG, msg);
	}
	
	@Override
	public void info(String msg) {
		log(Level.INFO, msg);
	}
	
	@Override
	public void warn(String msg) {
		log(Level.WARN, msg);
	}
	
	@Override
	public void error(String msg) {
		log(Level.ERROR, msg);
	}
	
	private void log(Level level, String msg) {
		System.out.println(formatter.format(LocalTime.now()) + " " + level.string + " : " + msg);
	}
	
	private enum Level {
		TRACE("TRACE"),
		DEBUG("DEBUG"),
		INFO(" INFO"),
		WARN(" WARN"),
		ERROR("ERROR");
		
		private final String string;
		
		Level(String string) {
			this.string = string;
		}
	}
}

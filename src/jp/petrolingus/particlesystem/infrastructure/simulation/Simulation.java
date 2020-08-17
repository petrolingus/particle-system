package jp.petrolingus.particlesystem.infrastructure.simulation;

public interface Simulation {
	
	void start();
	
	void stop();
	
	void clear();
	
	boolean isRunning();
	
}

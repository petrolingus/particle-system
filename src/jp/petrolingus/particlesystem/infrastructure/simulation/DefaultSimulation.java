package jp.petrolingus.particlesystem.infrastructure.simulation;

import javafx.animation.AnimationTimer;
import jp.petrolingus.particlesystem.domain.algorithmes.Algorithm;
import jp.petrolingus.particlesystem.infrastructure.render.Renderer;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DefaultSimulation implements Simulation {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultSimulation.class);

	private final Algorithm algorithm;
	private final Renderer renderer;
	
	private final AnimationTimer timer;
	
	private boolean running;
	
	public DefaultSimulation(Algorithm algorithm, Renderer renderer) {
		log.debug(String.format("Create ParticleSystemSimulation: algorithm=%s, renderer=%s", algorithm, renderer));
		
		this.algorithm = algorithm;
		this.renderer = renderer;
		
		this.timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
			}
		};
		
		this.running = false;
	}
	
	private void onUpdate() {
		algorithm.update();
		renderer.render();
		sleep();
	}
	
	private void sleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(16);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void start() {
		if (running) {
			log.warn("ParticleSystemSimulation is already started");
			return;
		}
		
		log.info("Start ParticleSystemSimulation");
		
		running = true;
		timer.start();
	}
	
	@Override
	public synchronized void stop() {
		if (!running) {
			log.warn("ParticleSystemSimulation is already stopped");
			return;
		}
		
		log.info("Stop ParticleSystemSimulation");
		
		running = false;
		timer.stop();
	}
	
	@Override
	public synchronized void clear() {
		if (running) {
			log.warn("Could not clear because simulation is currently running");
			return;
		}
		
		log.info("Clear ParticleSystemSimulation");
		renderer.clear();
	}
	
	@Override
	public boolean isRunning() {
		return running;
	}
	
}

package jp.petrolingus.particlesystem.domain.algorithmes;

import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Double.MAX_VALUE;
import static java.util.stream.Collectors.toList;

public class DefaultAlgorithm implements Algorithm {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultAlgorithm.class);
	
	//====================================
	// Параметры симуляции
	private final int width;
	private final int height;
	private final double dt;
	private final int n;
	private final double radius;
	private final List<Particle> particles;
	//=====================================
	
	
	//====================================
	// Состояние симуляции
	private double tm = 0;
	private Event event = new Event(-1, 0, 0, MAX_VALUE);
	private final List<Event> events;
	//====================================
	
	
	public DefaultAlgorithm(int width, int height, double dt, double radius, List<Particle> particles) {
		log.debug(String.format(
				"Create DefaultGenerator: dt=%f, radius=%f, particles=%s",
				dt, radius, particles.size()
		));
		
		this.width = width;
		this.height = height;
		this.dt = dt;
		this.n = particles.size();
		this.radius = radius;
		this.particles = particles;
		this.events = Stream.generate(Event::new).limit(2 * n).collect(toList());
	}
	
	// TODO: Two similar methods need to group
	private void getHorizontalWallEvents(List<Event> events) {
		for (int i = 0; i < n; i++) {
			Particle p = particles.get(i);
			Event event = events.get(i);
			
			event.id = 0;
			event.id1 = i;
			event.id2 = i;
			event.time = (p.vx < 0) ? (radius - p.x) / p.vx : (width - radius - p.x) / p.vx;
		}
	}
	
	private void getVerticalWallEvents(List<Event> events) {
		for (int i = 0; i < n; i++) {
			Particle p = particles.get(i);
			Event event = events.get(n + i);
			
			event.id = 1;
			event.id1 = i;
			event.id2 = i;
			event.time = (p.vy < 0) ? (radius - p.y) / p.vy : (height - radius - p.y) / p.vy;
		}
	}
	
	@Override
	public void update() {
		
		if (tm <= 0) {
			
			// Event handler
			if (event.id == 0) {
				particles.get(event.id1).vx *= -1;
			} else if (event.id == 1) {
				particles.get(event.id1).vy *= -1;
			}
			
			// Search for the nearest event
			getHorizontalWallEvents(events);
			getVerticalWallEvents(events);
			event = Collections.min(events);
			tm = event.time;
		}
		
		double tau = Math.min(tm, dt);
		tm -= tau;
		
		for (Particle p : particles) {
			p.update(tau);
		}
		
	}
	
}

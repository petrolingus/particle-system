package jp.petrolingus.particlesystem.domain.algorithmes;

import jp.petrolingus.particlesystem.domain.Particle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Double.MAX_VALUE;
import static java.util.stream.Collectors.toList;

public class DefaultAlgorithm implements Algorithm {

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
		System.out.printf(
				"Create DefaultGenerator: dt=%f, radius=%f, particles=%s%n",
				dt, radius, particles.size()
		);

		this.width = width;
		this.height = height;
		this.dt = dt;
		this.n = particles.size();
		this.radius = radius;
		this.particles = particles;
		int eventCount = 2 * n + (n * (n - 1) / 2);
		this.events = Stream.generate(Event::new).limit(eventCount).collect(toList());
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
			event.time = (p.vx == 0) ? MAX_VALUE : event.time;
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
			event.time = (p.vy == 0) ? MAX_VALUE : event.time;
		}
	}

	private void getCollisionEvents(List<Event> events) {

		for (int i = 0; i < n; i++) {
			Particle p1 = particles.get(i);

			for (int j = i + 1; j < n; j++) {
				Particle p2 = particles.get(j);

				int id = i * n + j - (i + 1) * (i + 2) / 2;
				Event event = events.get(2 * n + id);
				event.id = 2;
				event.id1 = i;
				event.id2 = j;
				event.time = MAX_VALUE;

				double dx = p1.x - p2.x;
				double dy = p1.y - p2.y;
				double dvx = p1.vx - p2.vx;
				double dvy = p1.vy - p2.vy;

				double a = dvx * dvx + dvy * dvy;
				double b = 2 * (dx * dvx + dy * dvy);
				double c = dx * dx + dy * dy - 4 * radius * radius;
				double D = b * b - 4 * a * c;

				if (D > 0) {
					double t = (-b - Math.sqrt(D)) / (2 * a);
					if (t > 0) {
						event.time = t;
					}
				}
			}
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
			} else if (event.id == 2) {

				double cosa = (particles.get(event.id1).x - particles.get(event.id2).x) / (2.0 * radius);
				double sina = (particles.get(event.id1).y - particles.get(event.id2).y) / (2.0 * radius);

				double vx0 = particles.get(event.id1).vx;
				double vy0 = particles.get(event.id1).vy;
				double vx1 = particles.get(event.id2).vx;
				double vy1 = particles.get(event.id2).vy;

				double nvx0 = vx1 * cosa * cosa + vx0 * sina * sina + (vy1 - vy0) * cosa * sina;
				double nvy0 = vy0 * cosa * cosa + vy1 * sina * sina + (vx1 - vx0) * cosa * sina;
				double nvx1 = vx0 * cosa * cosa + vx1 * sina * sina + (vy0 - vy1) * cosa * sina;
				double nvy1 = vy1 * cosa * cosa + vy0 * sina * sina + (vx0 - vx1) * cosa * sina;

				particles.get(event.id1).vx = nvx0;
				particles.get(event.id1).vy = nvy0;
				particles.get(event.id2).vx = nvx1;
				particles.get(event.id2).vy = nvy1;

				particles.get(event.id1).vx = nvx0;
				particles.get(event.id1).vy = nvy0;
				particles.get(event.id2).vx = nvx1;
				particles.get(event.id2).vy = nvy1;
			}

			// Search for the nearest event
			getHorizontalWallEvents(events);
			getVerticalWallEvents(events);
			getCollisionEvents(events);
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

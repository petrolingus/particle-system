package jp.petrolingus.particlesystem.domain.algorithmes;

import jp.petrolingus.particlesystem.domain.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private Event event = null;
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
    }

    // TODO: Two similar methods need to group
    private List<Event> getHorizontalWallEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Particle p = particles.get(i);
            double time = (p.vx < 0) ? (radius - p.x) / p.vx : (width - radius - p.x) / p.vx;
            events.add(new Event(0, i, i, time));
        }
        return events;
    }

    private List<Event> getVerticalWallEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Particle p = particles.get(i);
            double time = (p.vy < 0) ? (radius - p.y) / p.vy : (height - radius - p.y) / p.vy;
            events.add(new Event(1, i, i, time));
        }
        return events;
    }

    @Override
    public void update() {

        if (tm <= 0) {

            // Event handler
            if (event != null && event.id == 0) {
                particles.get(event.id1).vx *= -1;
            } else if (event != null && event.id == 1) {
                particles.get(event.id1).vy *= -1;
            }

            // Search for the nearest event
            List<Event> events = new ArrayList<>();
            events.addAll(getHorizontalWallEvents());
            events.addAll(getVerticalWallEvents());
            Collections.sort(events);
            event = events.get(0);
            tm = event.time;
        }

        double tau = Math.min(tm, dt);
        tm -= tau;

        for (Particle p : particles) {
            p.update(tau);
        }

    }

}

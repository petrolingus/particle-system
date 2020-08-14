package jp.petrolingus.particlesystem.domain.algorithmes;

import jp.petrolingus.particlesystem.domain.Particle;

import java.util.List;

public class DefaultAlgorithm implements Algorithm {

    //====================================
    // Параметры симуляции
    private final double dt;

    private final int n;
    private final double radius;

    private final List<Particle> particles;
    //=====================================

    //====================================
    // Состояние симуляции
    private double tm = 0;
    private double tau = 0;
    //====================================

    private int id1 = 0;
    private int id2 = 0;
    private int event = 0;

    public DefaultAlgorithm(double dt, int n, double radius, List<Particle> particles) {
        System.out.printf(
                "Create DefaultGenerator: dt=%f, n=%d, radius=%f, particles=%s%n",
                dt, n, radius, particles.size()
        );

        this.dt = dt;
        this.n = n;
        this.radius = radius;
        this.particles = particles;
    }

    @Override
    public void update() {

    }

}

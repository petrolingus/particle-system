package jp.petrolingus.particlesystem.domain;

import java.util.Objects;

public class Particle {

    public double x;
    public double y;
    public double vx;
    public double vy;
    public double r;

    public Particle(double x, double y, double vx, double vy, double r) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
    }

    public void update(double t) {
        this.x += this.vx * t;
        this.y += this.vy * t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Double.compare(particle.x, x) == 0 &&
                Double.compare(particle.y, y) == 0 &&
                Double.compare(particle.vx, vx) == 0 &&
                Double.compare(particle.vy, vy) == 0 &&
                Double.compare(particle.r, r) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, vx, vy, r);
    }

    @Override
    public String toString() {
        return "Particle{" +
                "x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                ", r=" + r +
                '}';
    }
}

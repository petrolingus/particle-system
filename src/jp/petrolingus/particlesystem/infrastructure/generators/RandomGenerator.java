package jp.petrolingus.particlesystem.infrastructure.generators;

import jp.petrolingus.particlesystem.domain.Particle;

import java.util.ArrayList;
import java.util.List;

public class RandomGenerator implements Generator {

    private final int width;
    private final int height;
    private final int shift;

    public RandomGenerator(int width, int height, int shift) {
        System.out.printf("Create RandomGenerator: width=%d, height=%d, shift=%d%n", width, height, shift);
        this.width = width;
        this.height = height;
        this.shift = shift;
    }

    @Override
    public List<Particle> generate(int n, double radius, double startVelocity) {

        List<Particle> particles = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1000; j++) {
                double x = shift + Math.random() * (width - shift - shift);
                double y = shift + Math.random() * (height - shift - shift);
                boolean flag = false;
                for (Particle particle : particles) {
                    double dx = x - particle.x;
                    double dy = y - particle.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < 2.0 * radius) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    double phi = 2.0 * Math.PI * Math.random();
                    double vx = startVelocity * Math.cos(phi);
                    double vy = startVelocity * Math.sin(phi);
                    particles.add(new Particle(x, y, vx, vy, radius));
                    break;
                }
            }
        }

        return particles;
    }

}

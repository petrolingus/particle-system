package jp.petrolingus.particlesystem.infrastructure.generators;

import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(RandomGenerator.class);

    private final int width;
    private final int height;

    private final int radius;
    private final double startVelocity;

    public SimpleGenerator(int width, int height, int radius, double startVelocity) {
        log.debug(String.format("Create RandomGenerator: width=%d, height=%d", width, height));
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.startVelocity = startVelocity;
    }

    @Override
    public List<Particle> generate() {
        log.info(String.format(
                "Generate simple particles: n=%d, radius=%d, startVelocity=%f",
                2, radius, startVelocity
        ));

        List<Particle> particles = new ArrayList<>();

        particles.add(new Particle(width / 2.0 - width * 0.2, height / 2.0, startVelocity, 0, radius));
        particles.add(new Particle(width / 2.0 + width * 0.2, height / 2.0, -startVelocity, 0, radius));

        return particles;
    }
}

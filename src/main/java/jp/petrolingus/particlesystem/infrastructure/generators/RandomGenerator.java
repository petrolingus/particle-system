package jp.petrolingus.particlesystem.infrastructure.generators;

import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RandomGenerator implements Generator {
	
	private static final Logger log = LoggerFactory.getLogger(RandomGenerator.class);
	
	private final int width;
	private final int height;
	private final int shift;

	private final int n;
	private final int radius;
	private final double startVelocity;
	private final int attempts;

	public RandomGenerator(int width, int height, int shift, int n, int radius, double startVelocity, int attempts) {
		log.debug(String.format("Create RandomGenerator: width=%d, height=%d, shift=%d", width, height, shift));
		this.n = n;
		this.radius = radius;
		this.startVelocity = startVelocity;
		this.attempts = attempts;
		this.width = width;
		this.height = height;
		this.shift = shift;
	}
	
	@Override
	public List<Particle> generate() {
		log.info(String.format(
				"Generate random particles: n=%d, radius=%d, startVelocity=%f",
				n, radius, startVelocity
		));
		
		List<Particle> particles = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < attempts; j++) {
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

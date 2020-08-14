package jp.petrolingus.particlesystem.infrastructure.generators;

import jp.petrolingus.particlesystem.domain.Particle;

import java.util.List;

public interface Generator {

    List<Particle> generate(int n, double radius, double startVelocity);

}

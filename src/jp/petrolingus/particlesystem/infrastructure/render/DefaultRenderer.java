package jp.petrolingus.particlesystem.infrastructure.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import jp.petrolingus.particlesystem.domain.Particle;

import java.util.List;

public class DefaultRenderer implements Renderer {

    private final int width;
    private final int height;
    private final GraphicsContext g;

    private final List<Particle> particles;

    public DefaultRenderer(int width, int height, GraphicsContext g, List<Particle> particles) {
        System.out.printf(
                "Create DefaultRenderer: width=%d, height=%d, graphics=%s, particles=%s%n",
                width, height, g, particles.size()
        );

        this.width = width;
        this.height = height;
        this.g = g;
        this.particles = particles;
    }

    @Override
    public void render() {
//        g.setGlobalAlpha(1.0);
//        g.setGlobalBlendMode(BlendMode.SRC_OVER);
        g.setFill(Color.BLACK);
        g.clearRect(0, 0, width, height);

        for (Particle p : particles) {
//            g.setGlobalAlpha(1.0);
//            g.setGlobalBlendMode(BlendMode.ADD);
            g.setFill(Color.BLACK);
            g.fillOval(p.x - p.r, p.y - p.r, p.r, p.r);
        }

    }
    
    @Override
    public void clear() {
        g.clearRect(0, 0, width, height);
    }
    
}

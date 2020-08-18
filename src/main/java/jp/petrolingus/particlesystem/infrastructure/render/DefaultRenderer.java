package jp.petrolingus.particlesystem.infrastructure.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.MotionBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import jp.petrolingus.particlesystem.Main;
import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.List;

public class DefaultRenderer implements Renderer {
    
    private static final Logger log = LoggerFactory.getLogger(DefaultRenderer.class);
    
    private final int width;
    private final int height;
    private final GraphicsContext g;

    private final List<Particle> particles;

    public DefaultRenderer(int width, int height, GraphicsContext g, List<Particle> particles) {
        log.debug(String.format(
                "Create DefaultRenderer: width=%d, height=%d, graphics=%s, particles=%s",
                width, height, g, particles.size()
        ));

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

        g.setLineWidth(1);
        g.strokeRect(0, 0, width, height);

        for (Particle p : particles) {
//            g.setGlobalAlpha(1.0);
//            g.setGlobalBlendMode(BlendMode.ADD);
            float r = ((float) Math.sqrt(p.vx * p.vx + p.vy * p.vy) / 400_000);
            r = r > 1.0 ? 1 : r;
            g.setFill(new Color(r, 0, 0, 1));
            g.fillOval(p.x - p.r, p.y - p.r, 2 * p.r, 2 * p.r);
        }

    }
    
    @Override
    public void clear() {
        g.clearRect(0, 0, width, height);
    }
    
}

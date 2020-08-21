package jp.petrolingus.particlesystem.infrastructure.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;
import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.util.logging.Logger;
import jp.petrolingus.particlesystem.util.logging.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class DefaultRenderer implements Renderer {

    private static final Logger log = LoggerFactory.getLogger(DefaultRenderer.class);

    private final int width;
    private final int height;
    private final GraphicsContext g;

    private final List<Particle> particles;
    private final XYChart.Series<String, Number> series;

    private final double v0;
    private final double velGap;

    private final int[] velocities = new int[40];

    public DefaultRenderer(int width, int height, GraphicsContext g, List<Particle> particles, XYChart.Series<String, Number> series, double v0) {

        log.debug(String.format(
                "Create DefaultRenderer: width=%d, height=%d, graphics=%s, particles=%s",
                width, height, g, particles.size()
        ));

        this.width = width;
        this.height = height;
        this.g = g;
        this.particles = particles;
        this.series = series;
        this.v0 = v0;
        this.velGap = v0 / 10.0;
    }

    private void generateGraphData() {
        for (Particle p : particles) {
            double velocityMagnitude = Math.sqrt(p.vx * p.vx + p.vy * p.vy);
            // TODO: 20.08.2020 Fix calculate id
            int id = (int) Math.floor(velocityMagnitude / velGap);
            velocities[id]++;
        }
    }

    @Override
    public void render() {
        g.setFill(Color.BLACK);
        g.clearRect(0, 0, width, height);

        g.setLineWidth(1);
        g.strokeRect(0, 0, width, height);

        for (Particle p : particles) {
            float r = ((float) Math.sqrt(p.vx * p.vx + p.vy * p.vy) / 400_000);
            r = r > 1.0 ? 1 : r;
            g.setFill(new Color(r, 0, 0, 1));
            g.fillOval(p.x - p.r, p.y - p.r, 2 * p.r, 2 * p.r);
        }

        Arrays.fill(velocities, 0);
        generateGraphData();

        series.getData().clear();
        for (int i = 0; i < 40; i++) {
            String name = "" + i;
            series.getData().add(new Data<>(name, velocities[i]));
        }

    }

    @Override
    public void clear() {
        g.clearRect(0, 0, width, height);
    }

}

package jp.petrolingus.particlesystem;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import jp.petrolingus.particlesystem.domain.Particle;
import jp.petrolingus.particlesystem.domain.algorithmes.Algorithm;
import jp.petrolingus.particlesystem.domain.algorithmes.DefaultAlgorithm;
import jp.petrolingus.particlesystem.infrastructure.generators.Generator;
import jp.petrolingus.particlesystem.infrastructure.generators.RandomGenerator;
import jp.petrolingus.particlesystem.infrastructure.gui.GUI;
import jp.petrolingus.particlesystem.infrastructure.render.DefaultRenderer;
import jp.petrolingus.particlesystem.infrastructure.render.Renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// TODO: 17.08.2020 Limit number of events by limit area there particles collision
public class Main extends Application {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private static final int N = 10_000;
    private static final int RADIUS = 1;
    private static final int SHIFT = RADIUS * 2;
    private static final int START_VELOCITY = 100000;

    private static final double DT = 0.00001;
    private static final int ATTEMPTS = 100;

    private List<Particle> particles;
    private Generator generator;
    private Algorithm algorithm;
    private Renderer renderer;
    private Parent root;

    private void onUpdate() {
        algorithm.update();
        renderer.render();
        sleep();
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws IOException {
        System.out.println("Initialize Main");
        particles = new ArrayList<>();

        generator = new RandomGenerator(WIDTH, HEIGHT, SHIFT);
        particles.addAll(generator.generate(N, RADIUS, START_VELOCITY, ATTEMPTS));

        algorithm = new DefaultAlgorithm(WIDTH, HEIGHT, DT, RADIUS, particles);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext g = canvas.getGraphicsContext2D();
        renderer = new DefaultRenderer(WIDTH, HEIGHT, g, particles);

//        root = new Pane(canvas);
//        root.setPrefSize(WIDTH, HEIGHT);

        root = new GUI(canvas);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Start Main");

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

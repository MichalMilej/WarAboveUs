package main;

import background.GameBackground;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import obstacles.Bombs;
import obstacles.EnemyPlanes;
import obstacles.Missiles;

import java.awt.*;
import java.util.Random;


public class Game extends Application {
    private static double wWidth;
    private static double wHeight;
    private Player player;
    private Random random;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("War Above Us");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        wWidth = dimension.width / 1.2d; // window width
        wHeight = dimension.height / 1.2d; // window height

        Pane pane = new Pane();
        Scene scene = new Scene(pane, wWidth, wHeight);

        GameBackground gameBackground = new GameBackground(0, 0);
        gameBackground.addToPane(pane);

        player = new Player(0, 5);
        player.addPlayerToPane(pane);

        Bombs bombs = new Bombs();
        EnemyPlanes enemyPlanes = new EnemyPlanes();
        Missiles missiles = new Missiles();

        random = new Random();

        for (int i = 0; i < 5; i++) {
            bombs.addObstacle(random.nextInt((int)wWidth), -100,
                    0, new MovingVector(false, false, false, true));
            pane.getChildren().add(bombs.getObjectsOfObstacles().getLast().getImageView());
        }
        for (int i = 0; i < 3; i++) {
            enemyPlanes.addObstacle(wWidth * 0.8,
                    random.nextInt((int) (wHeight - 100)),
                    0,
                    new MovingVector(true, false, false, false)
            );
            pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
        }

        //Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkUserInput(scene);
                gameBackground.moveGameBackground(2, 0);
                player.move();
                if (player.isReleaseMissilePressed() && player.getAmmunition() > 0) {
                    player.releaseMissile(missiles, pane);
                    player.setReleaseMissilePressed(false);
                }
                missiles.moveObstacles(wHeight / 400, wWidth / 250, pane);
                missiles.checkCollisions(bombs, enemyPlanes, pane);
                bombs.moveObstacles(wHeight / 400, 0, pane);
                bombs.checkCollisions(player, enemyPlanes, pane);
                enemyPlanes.moveObstacles(wHeight / 400, wWidth / 300, pane);
                enemyPlanes.checkCollisions(player, pane);
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        setOnCloseRequest(primaryStage);
        primaryStage.show();
    }

    private void checkUserInput(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                player.getMovingVector().up = true;
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                player.getMovingVector().down = true;
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
                player.getMovingVector().left = true;
            if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)
                player.getMovingVector().right = true;
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                player.getMovingVector().up = false;
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                player.getMovingVector().down = false;
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
                player.getMovingVector().left = false;
            if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)
                player.getMovingVector().right = false;
            if (event.getCode() == KeyCode.SPACE)
                player.setReleaseMissilePressed(true);
    });
    }

    private void setOnCloseRequest(Stage stage){
        stage.setOnCloseRequest((t) -> {
                Platform.exit();
                System.exit(0);
        });
    }

    public static double getwWidth(){
        return wWidth;
    }
    public static double getwHeight(){
        return wHeight;
    }
}
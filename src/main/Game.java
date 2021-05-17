package main;

import background.GameBackground;
import effect.Explosions;
import generating.BombsComposition;
import generating.GameGenerationSystem;
import generating.PlanesComposition;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import obstacles.Bombs;
import obstacles.EnemyPlanes;
import obstacles.Missiles;
import scores.DistanceCounter;

import java.util.Random;


public class Game {
    private static double wWidth;
    private static double wHeight;
    private Player player;
    private Random random;
    private boolean pause = false;

    public void startGame(Stage primaryStage){
        primaryStage.setTitle("War Above Us");
        wWidth = 1480f; // window width
        wHeight = 900f; // window height

        Pane pane = new Pane();
        Scene scene = new Scene(pane, wWidth, wHeight);

        GameBackground gameBackground = new GameBackground(0, 1);
        gameBackground.addToPane(pane);

        Bombs bombs = new Bombs();
        EnemyPlanes enemyPlanes = new EnemyPlanes();
        Missiles missiles = new Missiles();

        Explosions explosions = new Explosions();

        player = new Player(0, missiles, 5, 3000, pane);
        player.addPlayerToPane(pane);

        DistanceCounter distanceCounter = new DistanceCounter(pane, missiles);

        GameGenerationSystem gameGenerationSystem = new GameGenerationSystem(player, bombs, enemyPlanes, distanceCounter.getDistanceTravelled());

        //Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkUserInput(scene, this);
                gameGenerationSystem.work(pane);
                if (!pause) {
                    gameBackground.moveGameBackground(getwWidth() / 300);
                    if (!gameBackground.isNextBackgroundPrepared())
                        gameBackground.prepareNextBackground(gameGenerationSystem.getNextBackgroundId(gameBackground.getCurrentBackgroundId()));
                    player.move();
                    if (player.isReleaseMissilePressed() && player.getAmmunition() > 0) {
                        player.releaseMissile(missiles, pane, wWidth / 200, wHeight / 100);
                        player.setReleaseMissilePressed(false);
                    }
                    player.reload(missiles, pane);
                    enemyPlanes.attackPlayer(player, missiles, pane, wWidth / 200, wHeight / 100);
                    missiles.checkCollisions(bombs, enemyPlanes, player, pane, explosions);
                    missiles.moveObstacles( pane);
                    enemyPlanes.moveObstacles(pane);
                    bombs.moveObstacles(pane);
                    bombs.checkCollisions(player, enemyPlanes, pane);
                    enemyPlanes.checkCollisions(player, pane);
                    explosions.manageExplosions(pane);
                    explosions.moveExplosions();
                    distanceCounter.increaseDistanceTravelled(0.001);
                }
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private void checkUserInput(Scene scene, AnimationTimer timer){
        if (!pause) {
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
        }
        scene.setOnKeyReleased(event -> {
            if (!pause) {
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
            }
            if (event.getCode() == KeyCode.ESCAPE){
                pause = !pause;
            }
        });
    }

    public static double getwWidth(){
        return wWidth;
    }
    public static double getwHeight(){
        return wHeight;
    }
}
package main;

import background.GameBackground;
import dialogs.GameOver;
import effect.Explosions;
import generating.GameGenerationSystem;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import obstacles.Bombs;
import obstacles.EnemyPlanes;
import obstacles.Missiles;
import scores.DistanceCounter;
import dialogs.PauseText;

import java.util.Random;


public class Game {
    private static double wWidth;
    private static double wHeight;
    private Player player;
    private Random random;
    private PauseText pauseText;

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

        DistanceCounter distanceCounter = new DistanceCounter(pane, missiles);

        player = new Player(0, missiles, 5, 3000, pane);

        GameGenerationSystem gameGenerationSystem = new GameGenerationSystem(player, bombs, enemyPlanes, distanceCounter.getDistanceTravelled());

        GameOver gameOver = new GameOver(wWidth, wHeight, wHeight / 5, pane);

        pauseText = new PauseText(wWidth, wHeight, wHeight / 10, pane);

        //Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkUserInput(scene, this);
                if (!pauseText.isVisible()) {
                    gameGenerationSystem.work(pane);
                    gameBackground.moveGameBackground(getwWidth() / 300);
                    if (!gameBackground.isNextBackgroundPrepared())
                        gameBackground.prepareNextBackground(gameGenerationSystem.getNextBackgroundId(gameBackground.getCurrentBackgroundId()));
                    player.move();
                    if (player.isReleaseMissilePressed() && player.getAmmunition() > 0) {
                        player.releaseMissile(missiles, pane, wWidth / 200, wHeight / 100);
                        player.setReleaseMissilePressed(false);
                    }
                    if (player.getHp() > 0)
                        player.reload(missiles, pane);
                    player.animateSmoke();
                    enemyPlanes.attackPlayer(player, missiles, pane, wWidth / 200, wHeight / 100);
                    missiles.checkCollisions(bombs, enemyPlanes, player, pane, explosions);
                    missiles.moveObstacles( pane);
                    enemyPlanes.moveObstacles(pane);
                    bombs.moveObstacles(pane);
                    bombs.checkCollisions(player, enemyPlanes, pane, explosions);
                    enemyPlanes.checkCollisions(player, pane, explosions);
                    explosions.manageExplosions(pane);
                    explosions.moveExplosions();
                    if (player.getHp() == 0)
                        gameOver.showGameOver();
                    if (player.getHp() > 0)
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
                if (!pauseText.isVisible() && player.getHp() > 0)
                    player.setReleaseMissilePressed(true);

            if (event.getCode() == KeyCode.ESCAPE){
                pauseText.setVisible(!pauseText.isVisible());
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
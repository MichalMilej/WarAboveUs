package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import obstacles.Bombs;

import java.awt.*;
import java.util.Random;


public class Game extends Application {
    private String[] args;
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

        //Dynamiczna wielkość ekranu
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        wWidth = dimension.width / 1.2d; // window width
        wHeight = dimension.height / 1.2d; // window height

        Pane pane = new Pane();
        Scene scene = new Scene(pane, wWidth, wHeight);

        ImageOfObject imageOfPlayer = new ImageOfObject("images/303Division.png");
        player = new Player(imageOfPlayer);
        pane.getChildren().add(player.getImageView());

        Bombs bombs = new Bombs();
        bombs.addImageOfBomb("images/Plane Bomb.png");

        random = new Random();

        for (int i = 0; i < 10; i++) {
            bombs.addBomb(random.nextInt((int)wWidth), -100,
                    0, new MovingVector(false, false, false, true));
            pane.getChildren().add(bombs.getBombs().getLast().getImageView());
        }
        //Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkUserInput(scene);
                player.move();
                bombs.moveBombs(wHeight / 200, 0, pane);
                bombs.checkCollisions(player, pane);
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
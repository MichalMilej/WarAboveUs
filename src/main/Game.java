package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;


public class Game extends Application {
    private String[] args;
    private static double wWidth;
    private static double wHeight;
    private Player player;

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

        player = new Player();
        pane.getChildren().add(player.getImageView());
        //Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkUserInput(scene);
                player.move();
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

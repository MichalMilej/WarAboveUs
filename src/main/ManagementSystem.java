package main;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import fxml.Menu;

import java.io.File;

public class ManagementSystem extends Application{
    private static MediaPlayer musicMediaPlayer;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        String musicPath = "sounds/music/menu_music.mp3";
        Media media = new Media(new File(musicPath).toURI().toString());
        musicMediaPlayer = new MediaPlayer(media);
        musicMediaPlayer.setVolume(0.5);
        musicMediaPlayer.play();

        Menu menu = new Menu();
        menu.showMenu(primaryStage);

    }

    public static MediaPlayer getMusicMediaPlayer() {
        return musicMediaPlayer;
    }

    public static void changeMusicMediaPlayerStatus(){
        if (musicMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            musicMediaPlayer.stop();
        }
        else if (musicMediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
            musicMediaPlayer.play();
        }
    }
}

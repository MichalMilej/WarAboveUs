package fxml;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.Game;
import main.ManagementSystem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    Slider volumeSlider;
    @FXML
    Button musicButton;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaPlayer = ManagementSystem.getMusicMediaPlayer();
        if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED)
            musicButton.setText("Music: off");
        volumeSlider.setValue(mediaPlayer.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

    }

   @FXML
    public void changeMusicStatus(ActionEvent actionEvent) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            musicButton.setText("Music: off");
        }
        else if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
            musicButton.setText("Music: on");
        }
        ManagementSystem.changeMusicMediaPlayerStatus();
    }

    public void showScoreboards(ActionEvent actionEvent){
        String path = "Scoreboard.fxml";
        Parent scoreboardView;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            scoreboardView = loader.load();
        }catch (IOException e){
            System.out.println("Problem z plikiem: " + path);
            return;
        }

        Scene scoreboardScene = new Scene(scoreboardView);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(scoreboardScene);
        window.show();
    }
    public void playGame(ActionEvent actionEvent)throws IOException{
        Stage primaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        //if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            //ManagementSystem.changeMusicMediaPlayerStatus();

        Game game = new Game();
        game.startGame(primaryStage);

    }
}

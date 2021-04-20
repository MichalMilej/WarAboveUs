package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String path = "C:\\Users\\Miko\\IdeaProjects\\WAUMenu\\src\\sample\\music.mp3";

    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    @FXML
    Slider volumeSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.setValue(mediaPlayer.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

    }
    @FXML



    public void playSound(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.play();
        mediaPlayer.setVolume(0.05);

    }

    public void stopSound(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void showScoreboards(ActionEvent actionEvent) throws IOException {
        Parent scoreboardView = FXMLLoader.load(getClass().getResource("Scoreboards.fxml"));
        Scene scoreboardScene = new Scene(scoreboardView);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(scoreboardScene);
        window.show();
//        Parent root = FXMLLoader.load(getClass().getResource("Scoreboards.fxml"));
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setTitle("Settings");
//        stage.setScene(scene);
//        stage.show();
    }
    public void playGame(ActionEvent actionEvent)throws IOException{






    }
    public void backToTheMainScene(ActionEvent actionEvent) {

    }
}

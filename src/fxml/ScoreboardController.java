package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScoreboardController {
    public void backToTheMainScene(ActionEvent actionEvent) throws IOException {
        Parent mainMenuView = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuView);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(mainMenuScene);
        window.show();

    }

}

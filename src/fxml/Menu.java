package fxml;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Game;

import java.io.IOException;

public class Menu{

    public void showMenu(Stage primaryStage){
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Menu.fxml"));
            root = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("War Above Us");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
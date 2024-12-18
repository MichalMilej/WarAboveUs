import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.Controller;

public class Main extends Application {

    Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu/sample.fxml"));
        primaryStage.setTitle("War Above Us");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

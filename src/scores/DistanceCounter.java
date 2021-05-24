package scores;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.Game;
import obstacles.Missiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class DistanceCounter{
    private Label label;
    private double distanceTravelled = 0;
    private final String string = "Distance: ";
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public DistanceCounter(Pane pane, Missiles missiles){
        double missileHeight = missiles.getImagesOfObstacles().get(0).getImage().getHeight();
        label = new Label(string + decimalFormat.format(distanceTravelled) + " km");
        String path = "fonts/blackchancery/BLKCHCRY.TTF";
        Font font;
        try {
            font = Font.loadFont(new FileInputStream(new File(path)), missileHeight);
        }catch (FileNotFoundException e){
            System.out.println("Nie znaleziono czcionki :." + path);
            return;
        }
        label.setFont(font);
        label.setLayoutX(Game.getwWidth() / 4);
        label.setLayoutY(Game.getwHeight() - Game.getwHeight() / 25 - label.getHeight() - missileHeight * 1.2);
        pane.getChildren().add(label);
    }

    public void increaseDistanceTravelled(double value){
        distanceTravelled += value;
        label.setText(string + decimalFormat.format(distanceTravelled) + "km");
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }
}

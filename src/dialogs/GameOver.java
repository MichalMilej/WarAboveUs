package dialogs;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameOver {
    private Text text;

    public GameOver(double wWidth, double wHeigth, double px, Pane pane){
        String path = "fonts/blackchancery/BLKCHCRY.TTF";
        text = new javafx.scene.text.Text("Game Over");
        Font font;
        try {
            font = Font.loadFont(new FileInputStream(new File(path)), px);
        }catch (FileNotFoundException e){
            System.out.println("Nie znaleziono czcionki :." + path);
            return;
        }
        text.setFont(font);
        text.setLayoutX(wWidth / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(wHeigth * 2 / 5 - text.getLayoutBounds().getHeight() / 2);
        text.setVisible(false);
        pane.getChildren().add(text);
    }

    public void showGameOver(){
        text.setVisible(true);
    }

    public boolean isGameOverShowed(){
        return text.isVisible();
    }
}

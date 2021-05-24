package dialogs;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PauseText {
    private Text text;

    public PauseText(double wWidth, double wHeigth, double px, Pane pane){
        String path = "fonts/blackchancery/BLKCHCRY.TTF";
        text = new Text("Pause");
        Font font;
        try {
            font = Font.loadFont(new FileInputStream(new File(path)), px);
        }catch (FileNotFoundException e){
            System.out.println("Nie znaleziono czcionki :." + path);
            return;
        }
        text.setFont(font);
        pane.getChildren().add(text);
        text.setLayoutX(wWidth / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(wHeigth / 2 - text.getLayoutBounds().getHeight() / 2);
        text.setVisible(false);
    }

    public void setVisible(boolean visible){
        text.setVisible(visible);
    }

    public boolean isVisible(){
        return text.isVisible();
    }
}

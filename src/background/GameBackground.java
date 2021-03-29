package background;

import javafx.scene.image.Image;
import main.ImageOfObject;
import main.InteractiveGraphicThing;

import java.util.ArrayList;

public class GameBackground extends InteractiveGraphicThing {
    private ArrayList<ImageOfObject> imagesOfBackgrounds = new ArrayList<>();

    public void addImage(ImageOfObject imageOfObject){
        imagesOfBackgrounds.add(imageOfObject);
    }

    public void setBackgroundImageView(int index) {
        setImageView(imagesOfBackgrounds.get(index).getImage());
    }
}

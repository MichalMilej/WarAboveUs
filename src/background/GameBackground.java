package background;

import javafx.scene.layout.Pane;
import main.Game;
import main.ImageOfObject;

import java.util.ArrayList;

public class GameBackground{
    private ArrayList<ImageOfObject> imagesOfBackgrounds = new ArrayList<>();
    private ImageViewBackground currentBackground = new ImageViewBackground();
    private ImageViewBackground nextBackground = new ImageViewBackground();

    public GameBackground(int firstBackgroundIndex, int secondBackgroundIndex){
        ImageOfObject imageOfBackground = new ImageOfObject("images/backgrounds/underlay background.jpg");
        addImage(imageOfBackground);

        currentBackground.setImageView(imagesOfBackgrounds.get(firstBackgroundIndex).getImage());
        nextBackground.setImageView(imagesOfBackgrounds.get(secondBackgroundIndex).getImage());
        nextBackground.getImageView().setX(Game.getwWidth());
    }

    public void addToPane(Pane pane){
        pane.getChildren().add(currentBackground.getImageView());
        pane.getChildren().add(nextBackground.getImageView());
    }

    public void addImage(ImageOfObject imageOfObject){
        imagesOfBackgrounds.add(imageOfObject);
    }

    public void moveGameBackground(double value, int nextBackgroundIndex) {
        currentBackground.getImageView().setX(currentBackground.getImageView().getX() - value);
        if (isNewPictureOn(currentBackground)) {
            nextBackground.getImageView().setX(nextBackground.getImageView().getX() - value);
            if (nextBackground.getImageView().getX() <= 0) {
                currentBackground.setImageView(nextBackground.getImageView().getImage());
                currentBackground.getImageView().setX(nextBackground.getImageView().getX());

                nextBackground.setImageView(imagesOfBackgrounds.get(nextBackgroundIndex).getImage());
                nextBackground.getImageView().setX(Game.getwWidth());
            }
        }
    }

    private boolean isNewPictureOn(ImageViewBackground imageViewBackground){
        if (Math.abs(imageViewBackground.getImageView().getX()) + Game.getwWidth() >=
                imageViewBackground.getImageView().getImage().getWidth())
            return true;
        return false;
    }

}

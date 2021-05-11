package background;

import javafx.scene.layout.Pane;
import main.Game;
import main.ImageOfObject;

import java.util.ArrayList;

public class GameBackground{
    private ArrayList<ImageOfObject> imagesOfBackgrounds = new ArrayList<>();
    private ImageViewBackground currentBackground = new ImageViewBackground();
    private ImageViewBackground nextBackground = new ImageViewBackground();
    private int currentBackgroundId;
    private int nextBackgroundId;
    private boolean nextBackgroundPrepared;

    public GameBackground(int firstBackgroundIndex, int secondBackgroundIndex){
        addImage(new ImageOfObject("images/backgrounds/day background.png"));
        addImage(new ImageOfObject("images/backgrounds/evening background.png"));
        addImage(new ImageOfObject("images/backgrounds/night background.png"));
        addImage(new ImageOfObject("images/backgrounds/morning background.png"));

        nextBackgroundPrepared = false;
        currentBackgroundId = firstBackgroundIndex;
        currentBackground.setImageView(imagesOfBackgrounds.get(firstBackgroundIndex).getImage());
        nextBackgroundId = secondBackgroundIndex;
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

    public void moveGameBackground(double value) {
        currentBackground.getImageView().setX(currentBackground.getImageView().getX() - value);
        if (isNewPictureOn(currentBackground)) {
            nextBackground.getImageView().setX(currentBackground.getImageView().getX()
                    + currentBackground.getImageView().getImage().getWidth());
            if (nextBackground.getImageView().getX() <= 0) {
                currentBackgroundId = nextBackgroundId;
                currentBackground.setImageView(nextBackground.getImageView().getImage());
                currentBackground.getImageView().setX(nextBackground.getImageView().getX());
                nextBackgroundPrepared = false;
            }
        }
    }

    public void prepareNextBackground(int nextBackgroundId){
        this.nextBackgroundId = nextBackgroundId;
        nextBackground.setImageView(imagesOfBackgrounds.get(nextBackgroundId).getImage());
        nextBackground.getImageView().setX(Game.getwWidth());
        nextBackgroundPrepared = true;
    }

    private boolean isNewPictureOn(ImageViewBackground imageViewBackground){
        if (Math.abs(imageViewBackground.getImageView().getX()) + Game.getwWidth() >=
                imageViewBackground.getImageView().getImage().getWidth())
            return true;
        return false;
    }

    public int getCurrentBackgroundId() {
        return currentBackgroundId;
    }

    public boolean isNextBackgroundPrepared() {
        return nextBackgroundPrepared;
    }
}

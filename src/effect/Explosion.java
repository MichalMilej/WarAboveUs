package effect;

import javafx.scene.image.ImageView;
import main.ImageOfObject;

import java.util.ArrayList;

public class Explosion {
    private ImageView explosionImageView = new ImageView();
    private int actualStateIndex, endAtThisIndex;
    private double timer;
    private double x, y;
    private double explosionHorizontalSpeed;
    private double explosionVerticalSpeed;

    public Explosion(double x, double y, int actualStateIndex, int endAtThisIndex, ArrayList<ImageOfObject> images, double explosionHorizontalSpeed, double explosionVerticalSpeed){
        this.actualStateIndex = actualStateIndex;
        this.endAtThisIndex = endAtThisIndex;
        this.x = x;
        this.y = y;
        this.explosionHorizontalSpeed = explosionHorizontalSpeed;
        this.explosionVerticalSpeed = explosionVerticalSpeed;
    }
    public void explode(ArrayList<ImageOfObject> images){
        if (hasEnded())
            return;

        explosionImageView.setImage(images.get(actualStateIndex).getImage());
        explosionImageView.setX(x - explosionImageView.getImage().getWidth() / 2);
        explosionImageView.setY(y - explosionImageView.getImage().getHeight() / 2);

        if (actualStateIndex < endAtThisIndex)
            actualStateIndex++;
        else
            actualStateIndex--;
        timer = System.currentTimeMillis();
    }

    public boolean hasEnded(){
        return actualStateIndex == endAtThisIndex;
    }

    public void move(){
        explosionImageView.setX(explosionImageView.getX() + explosionHorizontalSpeed);
        explosionImageView.setY(explosionImageView.getY() + explosionVerticalSpeed);
    }

    public ImageView getExplosionImageView() {
        return explosionImageView;
    }

    public double getTimer() {
        return timer;
    }
}

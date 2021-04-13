package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InteractiveGraphicThing {
    private ImageView imageView;
    private MovingVector movingVector;
    private int imageId;

    public InteractiveGraphicThing(){
        movingVector = new MovingVector(false, false, false, false);
    }

    public void setImageView(Image image){
        imageView = new ImageView(image);
    }

    public ImageView getImageView(){
        return imageView;
    }

    public void setImageViewPosition(double x, double y){
        imageView.setX(x);
        imageView.setY(y);
    }

    public void setMovingVector(MovingVector movingVector) {
        this.movingVector = movingVector;
    }

    public MovingVector getMovingVector() {
        return movingVector;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}

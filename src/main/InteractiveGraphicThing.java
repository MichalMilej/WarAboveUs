package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InteractiveGraphicThing {
    private ImageView imageView = new ImageView();
    private MovingVector movingVector;
    private double horizontalSpeed;
    private double verticalSpeed;

    public InteractiveGraphicThing(){
        movingVector = new MovingVector(false, false, false, false);
    }

    public void setImageView(Image image){
        imageView.setImage(image);
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

    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public void setHorizontalSpeed(double horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
}

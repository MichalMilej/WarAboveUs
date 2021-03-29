package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InteractiveGraphicThing {
    //private Image image;
    private ImageView imageView;
    private MovingVector movingVector;

    public InteractiveGraphicThing(){
        movingVector = new MovingVector(false, false, false, false);
    }

    /*public void setImage(String path){
        try {
            image = new Image(new FileInputStream(path));
        }catch (FileNotFoundException e){
            System.out.println("File not found: " + path);
        }
    }*/

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
}

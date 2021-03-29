package main;

import javafx.scene.image.Image;

import java.io.FileInputStream;

public class ImageOfObject {
    private Image image;

    public ImageOfObject(String path){
        try {
            image = new Image(new FileInputStream(path));
        }catch (Exception e){
            System.out.println("Problem z plikiem: " + path);
        }
    }

    public Image getImage() {
        return image;
    }
}

package effect;

import javafx.scene.layout.Pane;
import main.ImageOfObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Explosions {
    private ArrayList<ImageOfObject> images = new ArrayList<>();
    private LinkedList<Explosion> explosions = new LinkedList<>();
    private double animationTimeInMilliseconds = 200;

    public Explosions(){
        images.add(new ImageOfObject("images/explosions/Explosion 1.png"));
        images.add(new ImageOfObject("images/explosions/Explosion 2.png"));
        images.add(new ImageOfObject("images/explosions/Explosion 3.png"));
    }

    public void createExplosion(double x, double y, int startingIndex, int endingIndex, Pane pane, double explosionHorizontalSpeed, double explosionVerticalSpeed){
        if (startingIndex > images.size()|| endingIndex > images.size())
            return;
        explosions.add(new Explosion(x, y, startingIndex, endingIndex, images, explosionHorizontalSpeed, explosionVerticalSpeed));
        pane.getChildren().add(explosions.getLast().getExplosionImageView());
        explosions.getLast().explode(images);
    }

    public void manageExplosions(Pane pane){
        for (int i = 0; i < explosions.size(); i++){
            if (System.currentTimeMillis() - explosions.get(i).getTimer() >= animationTimeInMilliseconds){
                if (explosions.get(i).hasEnded()){
                    pane.getChildren().remove(explosions.get(i).getExplosionImageView());
                    explosions.remove(i--);
                }
                else {
                    explosions.get(i).explode(images);
                }
            }
        }
    }

    public void moveExplosions(){
        for (int i = 0; i < explosions.size(); i++){
            explosions.get(i).move();
        }
    }
}

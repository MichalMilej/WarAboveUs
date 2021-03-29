package obstacles;

import javafx.scene.layout.Pane;
import main.Game;
import main.ImageOfObject;
import main.MovingVector;
import main.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bombs {
    private ArrayList<ImageOfObject> imagesOfBombs = new ArrayList<>();
    private LinkedList<Bomb> bombs = new LinkedList<>();

    public void addImageOfBomb(String path){
        imagesOfBombs.add(new ImageOfObject(path));
    }

    public void addBomb(double x, double y, int imageId, MovingVector movingVector){
        bombs.add(new Bomb(x, y, imagesOfBombs.get(imageId), movingVector));
    }

    public LinkedList<Bomb> getBombs() {
        return bombs;
    }

    public void moveBombs(double fallingSpeed, double leftOrRightSpeed, Pane pane){
        double x, y;
        for (int i = 0; i < bombs.size(); i++){
            x = 0; y = 0;
            if (bombs.get(i).getMovingVector().down)
                y = fallingSpeed;
            if (bombs.get(i).getMovingVector().left)
                x = -leftOrRightSpeed;
            else if (bombs.get(i).getMovingVector().right)
                x = leftOrRightSpeed;

            bombs.get(i).setImageViewPosition(bombs.get(i).getImageView().getX() + x,
                    bombs.get(i).getImageView().getY() + y);

            if (bombs.get(i).getImageView().getX() + bombs.get(i).getImageView().getImage().getWidth() < 0 ||
                    bombs.get(i).getImageView().getX() > Game.getwWidth() ||
                    bombs.get(i).getImageView().getY() > Game.getwHeight()) {
                pane.getChildren().remove(bombs.get(i).getImageView());
                bombs.remove(i);
                continue;
            }
        }
    }

    public void checkCollisions(Player player, Pane pane){
        double x, y;
        for (int i = 0; i < bombs.size(); i++){
            for (int j = 0; j < 3; j++) {
                if (j == 0) { //First hitbox
                    x = bombs.get(i).getImageView().getX() + bombs.get(i).getImageView().getImage().getWidth() / 2;
                    y = bombs.get(i).getImageView().getY() + bombs.get(i).getImageView().getImage().getHeight();
                } else if (j == 1){ // Second hitbox
                    x = bombs.get(i).getImageView().getX() + bombs.get(i).getImageView().getImage().getWidth() / 2;
                    y = bombs.get(i).getImageView().getY() + bombs.get(i).getImageView().getImage().getHeight() / 2;
                } else { // Third hitbox
                    x = bombs.get(i).getImageView().getX() + bombs.get(i).getImageView().getImage().getWidth() / 2;
                    y = bombs.get(i).getImageView().getY();
                }
                if (x >= player.getImageView().getX() &&
                        x <= player.getImageView().getX() + player.getImageView().getImage().getWidth() &&
                        y >= player.getImageView().getY() &&
                        y <= player.getImageView().getY() + player.getImageView().getImage().getHeight()){
                    pane.getChildren().remove(bombs.get(i).getImageView());
                    bombs.remove(i);
                    break;
                }
            }
        }
    }
}

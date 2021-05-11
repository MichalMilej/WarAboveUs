package obstacles;

import javafx.scene.layout.Pane;
import main.MovingVector;
import main.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnemyPlanes extends Obstacles {
    public EnemyPlanes(){
        addImageOfObstacle("images/planes/enemy_plane_1.png");
        addImageOfObstacle("images/planes/enemy_plane_2.png");
        addImageOfObstacle("images/planes/enemy_plane_3.png");
    }

    public void checkCollisions(Player player, Pane pane) { // With player

        for (int i = 0; i < objectsOfObstacles.size(); i++) {
            // Enemy plane parameters
            double xPos = objectsOfObstacles.get(i).getImageView().getX();
            double yPos = objectsOfObstacles.get(i).getImageView().getY();
            double width = objectsOfObstacles.get(i).getImageView().getImage().getWidth();
            double height = objectsOfObstacles.get(i).getImageView().getImage().getHeight();

            // Enemy plane hit points
            double x, y;
            y = yPos + height / 2;
            double multiply = 0.2d;
            for (int j = 0; j < 5; j++) { // 4 points (0.2, 0.4, 0.6, 0.8 width)
                if (j < 4) {
                    x = xPos + multiply * width;
                    multiply += 0.2d;
                }else{
                    x = objectsOfObstacles.get(i).getImageView().getX() + player.getImageView().getImage().getWidth() / 2;
                    y = objectsOfObstacles.get(i).getImageView().getY() + player.getImageView().getImage().getHeight() / 3;
                }

                if (    x >= player.getImageView().getX() &&
                        x <= player.getImageView().getX() + player.getImageView().getImage().getWidth() &&
                        y >= player.getImageView().getY() &&
                        y <= player.getImageView().getY() + player.getImageView().getImage().getHeight()){
                    pane.getChildren().remove(objectsOfObstacles.get(i).getImageView());
                    objectsOfObstacles.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public void attackPlayer(Player player, Missiles missiles, Pane pane, double horizontalSpeed, double verticalSpeed){
        double playerPosY = player.getImageView().getY();
        double playerHeight = player.getImageView().getImage().getHeight();
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            double enemyPosY = getObjectsOfObstacles().get(i).getImageView().getY();
            double enemyPlaneHeight = getObjectsOfObstacles().get(i).getImageView().getImage().getHeight();
            if (playerPosY >= enemyPosY * 0.9 && playerPosY + playerHeight <= enemyPosY + enemyPlaneHeight * 1.1){
                ;
            }
        }
    }

    public void releaseMissile(Missiles missiles, int planeIndex, MovingVector movingVector, Pane pane, double horizontalSpeed, double verticalSpeed){
        Obstacle enemyPlane = getObjectsOfObstacles().get(planeIndex);
        double x = enemyPlane.getImageView().getX() - missiles.getImagesOfObstacles().get(2).getImage().getWidth() * 1.2;
        double y = enemyPlane.getImageView().getY() +
                enemyPlane.getImageView().getImage().getHeight() -
                enemyPlane.getImageView().getImage().getHeight() / 10;

        missiles.addObstacle(x, y, 2, movingVector, horizontalSpeed, verticalSpeed, 1);
        pane.getChildren().add(missiles.getObjectsOfObstacles().getLast().getImageView());
    }
}

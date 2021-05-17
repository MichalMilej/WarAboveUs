package obstacles;

import javafx.scene.layout.Pane;
import main.MovingVector;
import main.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnemyPlanes extends Obstacles {
    private final String armedPlaneName = "enemy_plane_1_armed.png";
    private double timer = 0;
    private double shootingBreakInMilliseconds = 500;
    private int enemyMissileIndex = 2;

    public EnemyPlanes(){
        addImageOfObstacle("images/planes/" + armedPlaneName);
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
        if (System.currentTimeMillis() - timer <= shootingBreakInMilliseconds)
            return;
        double playerPosX = player.getImageView().getX();
        double playerPosY = player.getImageView().getY();
        double playerHeight = player.getImageView().getImage().getHeight();
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            if (objectsOfObstacles.get(i).getImageView().getImage() != getImagesOfObstacles().get(0).getImage())
                continue;
            double x = objectsOfObstacles.get(i).getImageView().getX();
            double y = objectsOfObstacles.get(i).getImageView().getY() + objectsOfObstacles.get(i).getImageView().getImage().getHeight() * 0.7;
            if (x > playerPosX){
                if (y >= playerPosY && y <= playerPosY + playerHeight) {
                    boolean lineOfFireClear = true;
                    for (int j = 0; j < objectsOfObstacles.size(); j++){ // Check if line of fire is clear
                        if (i == j)
                            continue;
                        if (getObjectsOfObstacles().get(j).getImageView().getX() < x){
                            double planeYPos = getObjectsOfObstacles().get(j).getImageView().getY();
                            double planeHeight = getObjectsOfObstacles().get(j).getImageView().getImage().getHeight();
                            if (planeYPos <= y && planeYPos + planeHeight >= y){
                                lineOfFireClear = false;
                                break;
                            }
                        }
                    }
                    if (lineOfFireClear) {
                        releaseMissile(missiles, i, new MovingVector(true, false, false, false), pane, horizontalSpeed, verticalSpeed);
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    public void releaseMissile(Missiles missiles, int planeIndex, MovingVector movingVector, Pane pane, double horizontalSpeed, double verticalSpeed){
        double x = getObjectsOfObstacles().get(planeIndex).getImageView().getX() - missiles.getImagesOfObstacles().get(enemyMissileIndex).getImage().getWidth() * 1.4;
        double y = getObjectsOfObstacles().get(planeIndex).getImageView().getY() + getObjectsOfObstacles().get(planeIndex).getImageView().getImage().getHeight() * 0.7;

        missiles.addObstacle(x, y, enemyMissileIndex, movingVector, horizontalSpeed, verticalSpeed, 1);
        pane.getChildren().add(missiles.getObjectsOfObstacles().getLast().getImageView());
    }
}

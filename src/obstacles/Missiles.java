package obstacles;

import effect.Explosions;
import javafx.scene.layout.Pane;
import main.MovingVector;
import main.Player;

public class Missiles extends Obstacles {

    public Missiles(){
        addImageOfObstacle("images/missiles/Anti-aircraft Missile 1 up.png");
        addImageOfObstacle("images/missiles/Anti-aircraft Missile 1 right.png");
        addImageOfObstacle("images/missiles/Nazi missile 1.png");
    }

    public void checkCollisions(Bombs bombs, EnemyPlanes enemyPlanes, Player player, Pane pane, Explosions explosions){
        double x, y;
        double multiply = 0.3;
        boolean targetHit;
        int startingExplosionImageIndex = 0, endingExplosionImageIndex = 0;
        // For every obstacle
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            targetHit = false;
            double missileXPosForExplosion = getObjectsOfObstacles().get(i).getImageView().getX() + getObjectsOfObstacles().get(i).getImageView().getImage().getWidth() / 2;
            double missileYPosForExplosion = getObjectsOfObstacles().get(i).getImageView().getY() + getObjectsOfObstacles().get(i).getImageView().getImage().getHeight() / 2;
            double obstacleHorizontalSpeed = 0;
            double obstacleVerticalSpeed = 0;
            // Every missile have 9 hit points
            multiply = 0.3d;
            for (int j = 0; j < 9; j++){
                if ((j+1) % 3 == 0)
                    multiply += 0.3d;
                x =  objectsOfObstacles.get(i).getImageView().getX() +
                        (multiply * objectsOfObstacles.get(i).getImageView().getImage().getWidth());
                if (j < 3){
                    y = objectsOfObstacles.get(i).getImageView().getY() +
                            objectsOfObstacles.get(i).getImageView().getImage().getHeight() / 3;
                }
                else if (j < 6){
                    y = objectsOfObstacles.get(i).getImageView().getY() +
                            objectsOfObstacles.get(i).getImageView().getImage().getHeight() / 2;
                }else{
                    y = objectsOfObstacles.get(i).getImageView().getY() +
                            (objectsOfObstacles.get(i).getImageView().getImage().getHeight() * 2) / 3;
                }

                // Checking collisions with player
                if (    x >= player.getImageView().getX() &&
                        x <= player.getImageView().getX() + player.getImageView().getImage().getWidth() &&
                        y >= player.getImageView().getY() &&
                        y <= player.getImageView().getY() + player.getImageView().getImage().getHeight()) {
                    pane.getChildren().remove(getObjectsOfObstacles().get(i).getImageView());
                    getObjectsOfObstacles().remove(i);
                    startingExplosionImageIndex = 0;
                    endingExplosionImageIndex = 1;
                    player.playerHit(explosions, pane);
                    targetHit = true;
                }
                // Checking collisions with enemy planes
                if (!targetHit) {
                    for (int k = 0; k < enemyPlanes.getObjectsOfObstacles().size(); k++) {
                        Obstacle enemyPlane = enemyPlanes.getObjectsOfObstacles().get(k);
                        if (x >= enemyPlane.getImageView().getX() &&
                                x <= enemyPlane.getImageView().getX() + enemyPlane.getImageView().getImage().getWidth() &&
                                y >= enemyPlane.getImageView().getY() &&
                                y <= enemyPlane.getImageView().getY() + enemyPlane.getImageView().getImage().getHeight()) {
                            if (enemyPlanes.getObstacleHealthPoints().get(k) == 1) { // Destroyed
                                missileXPosForExplosion = enemyPlane.getImageView().getX() + enemyPlane.getImageView().getImage().getWidth() / 2;
                                pane.getChildren().remove(enemyPlane.getImageView());
                                enemyPlanes.getObstacleHealthPoints().remove(k);
                                enemyPlanes.getObjectsOfObstacles().remove(k--);
                                endingExplosionImageIndex = 3;
                            }else{
                                missileXPosForExplosion = getObjectsOfObstacles().get(i).getImageView().getX() + getObjectsOfObstacles().get(i).getImageView().getImage().getWidth();
                                endingExplosionImageIndex = 1;
                                obstacleHorizontalSpeed = -enemyPlane.getHorizontalSpeed();
                                enemyPlanes.decreaseHealthPoints(k);
                            }
                            pane.getChildren().remove(getObjectsOfObstacles().get(i).getImageView());
                            getObjectsOfObstacles().remove(i);
                            targetHit = true;
                        }
                    }
                }
                // Checking collisions with bombs
                if (!targetHit){
                    for (int k = 0; k < bombs.getObjectsOfObstacles().size(); k++){
                        Obstacle bomb = bombs.getObjectsOfObstacles().get(k);
                        if (x >= bomb.getImageView().getX() &&
                                x <= bomb.getImageView().getX() + bomb.getImageView().getImage().getWidth() &&
                                y >= bomb.getImageView().getY() &&
                                y <= bomb.getImageView().getY() + bomb.getImageView().getImage().getHeight()){
                            // Remove from GUI
                            pane.getChildren().remove(bomb.getImageView());
                            pane.getChildren().remove(getObjectsOfObstacles().get(i).getImageView());
                            // Remove from array
                            bombs.getObjectsOfObstacles().remove(k--);
                            getObjectsOfObstacles().remove(i);
                            endingExplosionImageIndex = 2;
                            targetHit = true;
                        }
                    }
                }
                // Checking collisions with other missiles
                if (!targetHit){
                    for (int k = 0; k < getObjectsOfObstacles().size(); k++){
                        if (k != i) {
                            Obstacle missile = getObjectsOfObstacles().get(k);
                            if (    x >= missile.getImageView().getX() &&
                                    x <= (missile.getImageView().getX() + missile.getImageView().getImage().getWidth()) &&
                                    y >= missile.getImageView().getY() &&
                                    y <= (missile.getImageView().getY() + missile.getImageView().getImage().getHeight())) {
                                missileXPosForExplosion = getObjectsOfObstacles().get(i).getImageView().getX() +
                                        getObjectsOfObstacles().get(i).getImageView().getImage().getHeight();
                                // Remove from GUI
                                pane.getChildren().remove(missile.getImageView());
                                pane.getChildren().remove(getObjectsOfObstacles().get(i).getImageView());
                                // Remove from array
                                getObjectsOfObstacles().remove(i);
                                getObjectsOfObstacles().remove(missile);
                                startingExplosionImageIndex = 0;
                                endingExplosionImageIndex = 1;
                                targetHit = true;
                            }
                        }
                    }
                }
                if (targetHit) {
                    explosions.createExplosion(missileXPosForExplosion, missileYPosForExplosion, startingExplosionImageIndex,
                            endingExplosionImageIndex, pane, obstacleHorizontalSpeed, obstacleVerticalSpeed);
                    break;
                }
            }
        }
    }
}

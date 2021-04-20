package obstacles;

import javafx.scene.layout.Pane;
import main.Player;

public class Bombs extends Obstacles {
    public Bombs(){
        addImageOfObstacle("images/bombs/bomb 1.png");
    }

    public void checkCollisions(Player player, EnemyPlanes enemyPlanes, Pane pane){
        double x, y;
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            for (int j = 0; j < 3; j++) {
                if (j == 0) { //First hitbox
                    x = objectsOfObstacles.get(i).getImageView().getX() +
                            objectsOfObstacles.get(i).getImageView().getImage().getWidth() / 2;
                    y = objectsOfObstacles.get(i).getImageView().getY() +
                            objectsOfObstacles.get(i).getImageView().getImage().getHeight();
                } else if (j == 1){ // Second hitbox
                    x = objectsOfObstacles.get(i).getImageView().getX() +
                            objectsOfObstacles.get(i).getImageView().getImage().getWidth() / 2;
                    y = objectsOfObstacles.get(i).getImageView().getY() +
                            objectsOfObstacles.get(i).getImageView().getImage().getHeight() / 2;
                } else { // Third hitbox
                    x = objectsOfObstacles.get(i).getImageView().getX() +
                            objectsOfObstacles.get(i).getImageView().getImage().getWidth() / 2;
                    y = objectsOfObstacles.get(i).getImageView().getY();
                }

                // Collisions with enemy planes
                boolean isCollision = false;
                for (int k = 0; k < enemyPlanes.getObjectsOfObstacles().size(); k++){
                    if (    x >= enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getX() &&
                            x <= enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getX()
                                    + enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getImage().getWidth() &&
                            y >= enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getY() &&
                            y <= enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getY() +
                                    enemyPlanes.getObjectsOfObstacles().get(k).getImageView().getImage().getHeight()){
                        pane.getChildren().remove(objectsOfObstacles.get(i).getImageView());
                        objectsOfObstacles.remove(i);
                        pane.getChildren().remove(enemyPlanes.getObjectsOfObstacles().get(k).getImageView());
                        enemyPlanes.getObjectsOfObstacles().remove(k);
                        isCollision = true;
                        break;
                    }
                }
                if (isCollision)
                    break;

                // Collisions with player
                if (x >= player.getImageView().getX() &&
                        x <= player.getImageView().getX() + player.getImageView().getImage().getWidth() &&
                        y >= player.getImageView().getY() &&
                        y <= player.getImageView().getY() + player.getImageView().getImage().getHeight()){
                    pane.getChildren().remove(objectsOfObstacles.get(i).getImageView());
                    objectsOfObstacles.remove(i);
                    break;
                }
            }
        }
    }
}

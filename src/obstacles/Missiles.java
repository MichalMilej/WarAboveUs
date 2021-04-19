package obstacles;

import javafx.scene.layout.Pane;

public class Missiles extends Obstacles {
    public Missiles(){
        addImageOfObstacle("images/Anti-aircraft Missile 1.png");
    }

    public void checkCollisions(Bombs bombs, EnemyPlanes enemyPlanes, Pane pane){
        double x, y;
        double multiply = 0.3;
        boolean targetHit;
        // For every obstacle
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            targetHit = false;
            // Every missile have 9 hit points
            for (int j = 0; j < 9; j++){
                if ((j+1) % 3 == 0)
                    multiply += 0.3d;
                x =  objectsOfObstacles.get(i).getImageView().getX() +
                        multiply * objectsOfObstacles.get(i).getImageView().getImage().getWidth();
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

                // Checking collisions with enemy planes
                for (int k = 0; k < enemyPlanes.getObjectsOfObstacles().size(); k++){
                    Obstacle enemyPlane = enemyPlanes.getObjectsOfObstacles().get(k);
                    if (x >= enemyPlane.getImageView().getX() &&
                            x <= enemyPlane.getImageView().getX() + enemyPlane.getImageView().getImage().getWidth() &&
                            y >= enemyPlane.getImageView().getY() &&
                            y <= enemyPlane.getImageView().getY() + enemyPlane.getImageView().getImage().getHeight()){
                        pane.getChildren().remove(enemyPlane.getImageView());
                        enemyPlanes.getObjectsOfObstacles().remove(k);
                        targetHit = true;
                        break;
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
                            pane.getChildren().remove(bomb.getImageView());
                            bombs.getObjectsOfObstacles().remove(k);
                            targetHit = true;
                            break;
                        }
                    }
                }
                if (targetHit){
                    pane.getChildren().remove(getObjectsOfObstacles().get(i).getImageView());
                    getObjectsOfObstacles().remove(i);
                    break;
                }

            }

        }
    }
}

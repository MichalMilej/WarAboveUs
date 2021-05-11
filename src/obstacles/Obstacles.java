package obstacles;

import javafx.scene.layout.Pane;
import main.Game;
import main.ImageOfObject;
import main.MovingVector;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Obstacles {
    protected ArrayList<ImageOfObject> imagesOfObstacles = new ArrayList<>();
    protected LinkedList<Obstacle> objectsOfObstacles = new LinkedList<>();
    protected LinkedList<Integer> obstacleHealthPoints = new LinkedList<>();

    public void moveObstacles(Pane pane){
        double x, y;
        for (int i = 0; i < objectsOfObstacles.size(); i++){
            x = 0; y = 0;
            if (objectsOfObstacles.get(i).getMovingVector().down)
                y = objectsOfObstacles.get(i).getVerticalSpeed();
            else if (objectsOfObstacles.get(i).getMovingVector().up)
                y = -objectsOfObstacles.get(i).getVerticalSpeed();
            if (objectsOfObstacles.get(i).getMovingVector().left)
                x = -objectsOfObstacles.get(i).getHorizontalSpeed();
            else if (objectsOfObstacles.get(i).getMovingVector().right)
                x = objectsOfObstacles.get(i).getHorizontalSpeed();

            objectsOfObstacles.get(i).setImageViewPosition(objectsOfObstacles.get(i).getImageView().getX() + x,
                    objectsOfObstacles.get(i).getImageView().getY() + y);

            if (objectsOfObstacles.get(i).getImageView().getX() + objectsOfObstacles.get(i).getImageView().getImage().getWidth() < 0 ||
                    objectsOfObstacles.get(i).getImageView().getY() > Game.getwHeight()) {
                pane.getChildren().remove(objectsOfObstacles.get(i).getImageView());
                objectsOfObstacles.remove(i);
                i--;
            }
        }
    }

    public void addImageOfObstacle(String path){
        imagesOfObstacles.add(new ImageOfObject(path));
    }

    public void addObstacle(double x, double y, int imageId, MovingVector movingVector, double horizontalSpeed, double verticalSpeed, int healthPoints){
        objectsOfObstacles.add(new Obstacle(x, y, imagesOfObstacles.get(imageId), movingVector, horizontalSpeed, verticalSpeed));
        obstacleHealthPoints.add(healthPoints);
    }

    public ArrayList<ImageOfObject> getImagesOfObstacles() {
        return imagesOfObstacles;
    }

    public LinkedList<Obstacle> getObjectsOfObstacles() {
        return objectsOfObstacles;
    }

    public void decreaseHealthPoints(int index){
        obstacleHealthPoints.set(index, obstacleHealthPoints.get(index) - 1);
    }

    public LinkedList<Integer> getObstacleHealthPoints() {
        return obstacleHealthPoints;
    }
}

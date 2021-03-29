package obstacles;

import main.ImageOfObject;
import main.InteractiveGraphicThing;
import main.MovingVector;

public class Bomb extends InteractiveGraphicThing {
    public Bomb(double x, double y, ImageOfObject imageOfObject, MovingVector movingVector){
        setImageView(imageOfObject.getImage());
        setImageViewPosition(x, y);
        setMovingVector(movingVector);
    }
}

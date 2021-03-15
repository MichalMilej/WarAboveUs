package main;

public class Player extends InteractiveGraphicThing {


    public Player(){
        setImage("images/plane.png");
        setImageView();
        setStartingPosition();
    }

    public void setStartingPosition(){
        setImageViewPosition(0, Game.getwHeight() / 2 - (float)getImageView().getImage().getHeight() / 2);
    }

    public void move() { // Plane movement system
        double x = 0, y = 0;
        if (getMovingVector().up && !getMovingVector().down)
            y = -(Game.getwHeight() / 400); // 60 times per second
        else if (getMovingVector().down && !getMovingVector().up)
            y = Game.getwHeight() / 400; // 60 times per second
        if (getMovingVector().left && !getMovingVector().right)
            x = -(Game.getwHeight() / 150); // 60 times per second
        else if (getMovingVector().right && !getMovingVector().left)
            x = Game.getwHeight() / 150; // 60 times per second

        setImageViewPosition(getImageView().getX() + x, getImageView().getY() + y);
    }
}

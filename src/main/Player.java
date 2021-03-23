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
        double downSpeed = (Game.getwHeight() / 200);
        double upSpeed = -(Game.getwHeight() / 200);
        double leftSpeed = -(Game.getwHeight() / 150);
        double rightSpeed = Game.getwHeight() / 150;
        if (getMovingVector().up && !getMovingVector().down && isPossible(0, upSpeed))
            y = upSpeed; // 60 times per second
        else if (getMovingVector().down && !getMovingVector().up && isPossible(0, downSpeed))
            y = downSpeed; // 60 times per second
        if (getMovingVector().left && !getMovingVector().right && isPossible(leftSpeed, 0))
            x = leftSpeed; // 60 times per second
        else if (getMovingVector().right && !getMovingVector().left && isPossible(rightSpeed, 0))
            x = rightSpeed; // 60 times per second

        setImageViewPosition(getImageView().getX() + x, getImageView().getY() + y);
    }

    private boolean isPossible(double x, double y){
        if (getImageView().getX() + x < 0 // Left edge
                || getImageView().getX() + getImageView().getImage().getWidth() + x > Game.getwWidth()) // Right edge
            return false;

        if (getImageView().getY() + y < 0  // Top edge
                || getImageView().getY() + getImageView().getImage().getHeight() + y > Game.getwHeight()) // Bottom Edge
            return false;

        return true;
    }
}

package main;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import obstacles.Missiles;

public class Player extends InteractiveGraphicThing {
    private ImageOfObject imageOfPlayer;
    private int ammunition;
    private Label ammunitionValueLabel;
    private boolean releaseMissilePressed;

    public Player(int playerImageIndex, int ammunition){
        if (playerImageIndex == 0)
            imageOfPlayer = new ImageOfObject("images/303Division.png");

        this.ammunition = ammunition;
        releaseMissilePressed = false;
        setImageView(imageOfPlayer.getImage());
        setStartingPosition();
    }

    public void addPlayerToPane(Pane pane){
        pane.getChildren().add(getImageView());
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

    public void releaseMissile(Missiles missiles, Pane pane){
        double x = getImageView().getX() + getImageView().getImage().getWidth();
        double y = getImageView().getY() + getImageView().getImage().getHeight() - getImageView().getImage().getHeight() / 10;

        missiles.addObstacle(x, y, 0,
                new MovingVector(false, true, false, false));

        pane.getChildren().add(missiles.getObjectsOfObstacles().getLast().getImageView());

        ammunition--;
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

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public boolean isReleaseMissilePressed() {
        return releaseMissilePressed;
    }

    public void setReleaseMissilePressed(boolean releaseMissilePressed){
        this.releaseMissilePressed = releaseMissilePressed;
    }
}

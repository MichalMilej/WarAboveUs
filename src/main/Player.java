package main;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import obstacles.Missiles;

import java.util.ArrayList;

public class Player extends InteractiveGraphicThing {
    private ImageOfObject imageOfPlayer;
    private int ammunition;
    private ArrayList<ImageView> ammunitionNumberDisplay = new ArrayList<>();
    private boolean releaseMissilePressed;

    public Player(int playerImageIndex, Missiles missiles, int ammunition){
        if (playerImageIndex == 0)
            imageOfPlayer = new ImageOfObject("images/303Division.png");

        this.ammunition = ammunition;
        // Preparing Graphical display of ammunition number
        for (int i = 0; i < ammunition; i++){
            createMissileImageView(missiles);
        }

        releaseMissilePressed = false;
        setImageView(imageOfPlayer.getImage());
        setStartingPosition();
    }

    public void addPlayerToPane(Pane pane){
        pane.getChildren().add(getImageView());
    }

    public void addAmmunitionNumberDisplayToPane(Pane pane){
        for (int i = 0; i < ammunitionNumberDisplay.size(); i++)
            pane.getChildren().add(ammunitionNumberDisplay.get(i));
    }

    private void createMissileImageView(Missiles missiles){
        ammunitionNumberDisplay.add(new ImageView(missiles.getImagesOfObstacles().get(0).getImage()));
        int index = ammunitionNumberDisplay.size() - 1;
        if (index == 0)
            ammunitionNumberDisplay.get(index).setX(Game.getwWidth() / 30);
        else
            ammunitionNumberDisplay.get(index).setX(ammunitionNumberDisplay.get(index-1).getX() + Game.getwWidth() / 60);
        ammunitionNumberDisplay.get(index).setY(
                Game.getwHeight() - Game.getwHeight() / 25 - ammunitionNumberDisplay.get(index).getImage().getHeight());
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

        missiles.addObstacle(x, y, 1,
                new MovingVector(false, true, false, false));

        pane.getChildren().add(missiles.getObjectsOfObstacles().getLast().getImageView());

        pane.getChildren().remove(ammunitionNumberDisplay.get(ammunitionNumberDisplay.size() - 1));
        ammunitionNumberDisplay.remove(ammunitionNumberDisplay.size() - 1);

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

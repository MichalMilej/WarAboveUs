package main;

import effect.Explosions;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import obstacles.Missiles;
import scores.DistanceCounter;

import java.util.ArrayList;

public class Player extends InteractiveGraphicThing {
    private ImageOfObject imageOfPlayer;
    private ImageOfObject[] imagesOfSmoke;

    private ImageView smokeImageView;
    private int currentSmokeId = -1; // At start -1 because nothing set
    private double timer = System.currentTimeMillis();
    private final double howManyMillisecondsForChangingAnimation = 600;

    private int ammunition;
    private int maxAmmunition;
    private ArrayList<ImageView> ammunitionNumberDisplay = new ArrayList<>();

    private boolean releaseMissilePressed;
    private double timerForGettingAmmunition;
    private double reloadOneBulletTimeInMilliseconds;

    private int hp;

    public Player(int playerImageIndex, Missiles missiles, int ammunition, double reloadOneBulletTimeInMilliseconds, Pane pane){
        if (playerImageIndex == 0)
            imageOfPlayer = new ImageOfObject("images/planes/303Division.png");
        imagesOfSmoke = new ImageOfObject[3];
        imagesOfSmoke[0] = new ImageOfObject("images/smoke/smoke1.png");
        imagesOfSmoke[1] = new ImageOfObject("images/smoke/smoke2.png");
        imagesOfSmoke[2] = new ImageOfObject("images/smoke/smoke3.png");

        smokeImageView = new ImageView();
        smokeImageView.setVisible(false);

        this.ammunition = ammunition;
        this.maxAmmunition = ammunition;
        // Preparing Graphical display of ammunition number
        for (int i = 0; i < ammunition; i++){
            createMissileImageView(missiles, pane);
        }

        this.reloadOneBulletTimeInMilliseconds = reloadOneBulletTimeInMilliseconds;

        this.hp = 2;

        releaseMissilePressed = false;
        setImageView(imageOfPlayer.getImage());
        setStartingPosition();
        pane.getChildren().add(smokeImageView);
        pane.getChildren().add(getImageView());
    }

    public void playerHit(Explosions explosions, Pane pane){
        if (hp == 2){
            hp--;
            smokeImageView.setVisible(true);
        }else if (hp == 1){
            double x = getImageView().getX() + getImageView().getImage().getWidth() / 2;
            double y = getImageView().getY() + getImageView().getImage().getHeight() / 2;
            explosions.createExplosion(x, y, 0, 3, pane, 0, 0);
            getImageView().setX(-500);
            getImageView().setVisible(false);
            smokeImageView.setVisible(false);
            for (int i = 0; i < ammunitionNumberDisplay.size(); i++){
                pane.getChildren().remove(ammunitionNumberDisplay.get(i));
                ammunitionNumberDisplay.remove(i--);
            }
            hp--;
        }
    }

    public void animateSmoke(){
        if (smokeImageView.isVisible() && System.currentTimeMillis() - timer >= howManyMillisecondsForChangingAnimation) {
            if (currentSmokeId == 2 || currentSmokeId < 0){
                smokeImageView.setImage(imagesOfSmoke[0].getImage());
                currentSmokeId = 0;
            }
            else if (currentSmokeId == 0) {
                smokeImageView.setImage(imagesOfSmoke[1].getImage());
                currentSmokeId = 1;
            }else if (currentSmokeId == 1){
                smokeImageView.setImage(imagesOfSmoke[2].getImage());
                currentSmokeId = 2;
            }
            smokeImageView.setX(getImageView().getX() + getImageView().getImage().getWidth() / 3 - smokeImageView.getImage().getWidth() / 2);
            smokeImageView.setY(getImageView().getY() + getImageView().getImage().getHeight() / 3 - smokeImageView.getImage().getHeight() / 2);
            timer = System.currentTimeMillis();
        }
    }

    private void createMissileImageView(Missiles missiles, Pane pane){
        ammunitionNumberDisplay.add(new ImageView(missiles.getImagesOfObstacles().get(0).getImage()));
        int index = ammunitionNumberDisplay.size() - 1;
        if (index == 0)
            ammunitionNumberDisplay.get(index).setX(Game.getwWidth() / 30);
        else
            ammunitionNumberDisplay.get(index).setX(ammunitionNumberDisplay.get(index-1).getX() + Game.getwWidth() / 60);
        ammunitionNumberDisplay.get(index).setY(
                Game.getwHeight() - Game.getwHeight() / 25 - ammunitionNumberDisplay.get(index).getImage().getHeight());
        pane.getChildren().add(ammunitionNumberDisplay.get(index));
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
        if (hp < 2){
            smokeImageView.setX(smokeImageView.getX() + x);
            smokeImageView.setY(smokeImageView.getY() + y);
        }
    }

    public void releaseMissile(Missiles missiles, Pane pane, double horizontalSpeed, double verticalSpeed){
        double x = getImageView().getX() + getImageView().getImage().getWidth();
        double y = getImageView().getY() + getImageView().getImage().getHeight() - getImageView().getImage().getHeight() / 10;

        missiles.addObstacle(x, y, 1,
                new MovingVector(false, true, false, false), horizontalSpeed, verticalSpeed, 1);

        pane.getChildren().add(missiles.getObjectsOfObstacles().getLast().getImageView());

        pane.getChildren().remove(ammunitionNumberDisplay.get(ammunitionNumberDisplay.size() - 1));
        ammunitionNumberDisplay.remove(ammunitionNumberDisplay.size() - 1);

        ammunition--;
        if (timerForGettingAmmunition == 0)
            timerForGettingAmmunition = System.currentTimeMillis();
    }

    public void reload(Missiles missiles, Pane pane){
        if (ammunition < maxAmmunition) {
            if (System.currentTimeMillis() - timerForGettingAmmunition >= reloadOneBulletTimeInMilliseconds) {
                createMissileImageView(missiles, pane);
                ammunition++;
                timerForGettingAmmunition = System.currentTimeMillis();
            }
        }
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

    public int getAmmunition() {
        return ammunition;
    }

    public int getHp() {
        return hp;
    }

    public boolean isReleaseMissilePressed() {
        return releaseMissilePressed;
    }

    public void setReleaseMissilePressed(boolean releaseMissilePressed){
        this.releaseMissilePressed = releaseMissilePressed;
    }
}

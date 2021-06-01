package generating;

import javafx.scene.layout.Pane;
import main.Game;
import main.MovingVector;
import main.Player;
import obstacles.Bombs;
import obstacles.EnemyPlanes;

import java.util.Random;

public class GameGenerationSystem {
    private Player player;
    private Bombs bombs;
    private EnemyPlanes enemyPlanes;
    private Random random = new Random();
    private double distanceTravelled;

    public GameGenerationSystem(Player player, Bombs bombs, EnemyPlanes enemyPlanes, double distanceTravelled) {
        this.player = player;
        this.bombs = bombs;
        this.enemyPlanes = enemyPlanes;
        this.distanceTravelled = distanceTravelled;
    }

    public void work(Pane pane){
        if (allObstaclesBehind()){
            double difficulty;
            if (distanceTravelled < 20)
                difficulty = distanceTravelled * 10;
            else
                difficulty = 200;
            switch (random.nextInt(6)){
                case 0: {
                    generateBombs(random.nextInt(2) + 4, BombsComposition.FLAT, random.nextInt(5) + 3, 0, pane,
                            Game.getwWidth() / 1500 - difficulty, Game.getwHeight() / (random.nextInt(50) + 400 - difficulty));
                    break;
                }
                case 1: {
                    generateBombs(random.nextInt(2) + 3, BombsComposition.STAIRS, random.nextInt(5) + 1, 0, pane,
                            Game.getwWidth() / 1200 - difficulty, Game.getwHeight() / (random.nextInt(50) + 400 - difficulty));
                    break;
                }
                case 2: {
                    generateBombs(random.nextInt(2) + 3, BombsComposition.UPRIGHT, random.nextInt(5) + 6, 0, pane,
                            Game.getwWidth() / 1200 - difficulty, Game.getwHeight() / (random.nextInt(50) + 400 - difficulty));
                    break;
                }
                case 3:{
                    generateEnemyPlanes(random.nextInt(20) + 5, PlanesComposition.RANDOM,  1, random.nextInt(3),
                            pane, Game.getwWidth() / (random.nextInt(50) + 450 - difficulty), Game.getwHeight() / (random.nextInt(100) + 300 - difficulty));
                    break;
                }
                case 4:{
                    generateEnemyPlanes(random.nextInt(2) + 2, PlanesComposition.SQUADRON, random.nextInt(3) + 4, random.nextInt(3),
                            pane, Game.getwWidth() / (random.nextInt(50) + 400 - difficulty), Game.getwHeight() / (random.nextInt(50) + 250 - difficulty));
                    break;
                }
                case 5:{
                    generateEnemyPlanes(random.nextInt(3) + 3, PlanesComposition.KEY, random.nextInt(3) + 2, random.nextInt(3),
                            pane, Game.getwWidth() / (random.nextInt(50) + 400 - difficulty), Game.getwHeight() / (random.nextInt(50) + 300 - difficulty));
                    break;
                }
            }
        }
    }

    public void generateBombs(int amount, BombsComposition composition, int howManyWaves, int imageIndex, Pane pane, double hozSpeed, double verSpeed) {
        double x = 0, y = 0;
        double bombHeight = bombs.getImagesOfObstacles().get(imageIndex).getImage().getHeight();
        double bombWidth = bombs.getImagesOfObstacles().get(imageIndex).getImage().getWidth();
        if (composition == BombsComposition.FLAT) {
            for (int i = 0; i < howManyWaves; i++) {
                y = -bombHeight - i * (3*bombHeight);
                int wind = random.nextInt(3);
                boolean left = wind == 0;
                boolean right = wind == 1;
                for (int j = 0; j < amount; j++) {
                    x = player.getImageView().getX() +
                            random.nextInt((int)player.getImageView().getImage().getWidth() * 5) -
                            player.getImageView().getImage().getWidth() * 2.5;
                    if (x < 0 || x > Game.getwWidth())
                        x = random.nextInt((int)Game.getwWidth());
                    bombs.addObstacle(x, y, imageIndex, new MovingVector(left, right, false, true), hozSpeed, verSpeed, 1);
                    pane.getChildren().add(bombs.getObjectsOfObstacles().getLast().getImageView());
                }
            }
        }
        if (composition == BombsComposition.STAIRS){
            double multiply = 2;
            for (int i = 0; i < howManyWaves; i++) {
                y = y - bombHeight;
                x = random.nextInt((int)Game.getwWidth());
                Site site;
                if (x < bombWidth * multiply * amount)
                    site = Site.RIGHT;
                else if (Game.getwWidth() - x < bombWidth * multiply * amount)
                    site = Site.LEFT;
                else
                    site = random.nextBoolean() ? Site.LEFT : Site.RIGHT;
                int wind = random.nextInt(3);
                boolean left = wind == 0;
                boolean right = wind == 1;
                for (int j = 0; j < amount; j++) {
                    bombs.addObstacle(x, y, imageIndex, new MovingVector(left, right, false, true), hozSpeed, verSpeed, 1);
                    pane.getChildren().add(bombs.getObjectsOfObstacles().getLast().getImageView());
                    y = y - bombHeight - bombHeight / 1.5d;
                    if (site == Site.LEFT)
                        x -= bombWidth * multiply;
                    else
                        x += bombWidth * multiply;
                }
            }
        }
        if (composition == BombsComposition.UPRIGHT){
            for (int i = 0; i < howManyWaves; i++){
                y = y - bombHeight;
                x = random.nextInt((int)Game.getwWidth());
                int wind = random.nextInt(3);
                boolean left = wind == 0;
                boolean right = wind == 1;
                for (int j = 0; j < amount; j++) {
                    bombs.addObstacle(x, y, 0, new MovingVector(left, right, false, true), hozSpeed, verSpeed, 1);
                    pane.getChildren().add(bombs.getObjectsOfObstacles().getLast().getImageView());
                    y = y - bombHeight - bombHeight / 1.5d;
                }
            }
        }
    }

    public void generateEnemyPlanes(int amount, PlanesComposition planesComposition,int howManyWaves, int imageIndex, Pane pane, double hozSpeed, double verSpeed){
        double x = Game.getwWidth(), y;
        double gapScaleVer = 1.2d, gapScaleHor = 1.5d;
        double enemyHeight = enemyPlanes.getImagesOfObstacles().get(imageIndex).getImage().getHeight();
        double enemyWidth = enemyPlanes.getImagesOfObstacles().get(imageIndex).getImage().getWidth();
        int numberOfImages = enemyPlanes.getImagesOfObstacles().size();
        if (planesComposition == PlanesComposition.SQUADRON) {
            for (int i = 0; i < howManyWaves; i++){
                y = random.nextInt((int)(Game.getwHeight() - enemyHeight * amount * gapScaleVer));
                for (int j = 0; j < amount; j++){
                    enemyPlanes.addObstacle(x, y, random.nextInt(numberOfImages), new MovingVector(true, false, false, false), hozSpeed, verSpeed, random.nextInt(3) + 1);
                    y += enemyHeight * gapScaleVer;
                    pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
                }
                x += enemyWidth * gapScaleHor;
            }
        }
        if (planesComposition == PlanesComposition.KEY){
            gapScaleHor = 1.1d;
            for (int i = 0; i < howManyWaves; i++){
                y = random.nextInt((int)(Game.getwHeight() - enemyHeight * amount * gapScaleVer)) + enemyHeight * amount / 2 * gapScaleVer;
                for (int j = 0, counter = 0; counter < amount; j++){
                    if (j == 0) { // Then put only one plane (leader)
                        enemyPlanes.addObstacle(x, y, random.nextInt(numberOfImages), new MovingVector(true, false, false, false), hozSpeed, verSpeed, random.nextInt(3) + 1);
                        pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
                        counter++;
                    }
                    else{
                        enemyPlanes.addObstacle(x, y - enemyHeight * gapScaleVer * j, random.nextInt(numberOfImages),
                                new MovingVector(true, false, false, false), hozSpeed, verSpeed, random.nextInt(3) + 1);
                        pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
                        counter++;
                        if (counter < amount) {
                            enemyPlanes.addObstacle(x, y + enemyHeight * gapScaleVer * j, random.nextInt(numberOfImages),
                                    new MovingVector(true, false, false, false), hozSpeed, verSpeed, random.nextInt(3) + 1);
                            counter++;
                            pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
                        }
                    }
                    x += enemyWidth * gapScaleHor;
                }

                x += enemyWidth * gapScaleHor * random.nextInt(3);
            }
        }
        if (planesComposition == PlanesComposition.RANDOM){
            gapScaleHor = 1.1d;
            for (int i = 0; i < howManyWaves; i++) {
                y = 0;
                for (int j = 0; j < amount; j++){
                    if (y == 0)
                        y += random.nextInt((int)(Game.getwHeight() - enemyHeight));
                    else
                        y += random.nextInt((int)(Game.getwHeight() - enemyHeight * 2 * gapScaleVer)) + enemyHeight * gapScaleVer;
                    if (y > Game.getwHeight() - enemyHeight){
                        j--;
                        x += enemyWidth * gapScaleHor;
                        y = 0;
                    }else{
                        enemyPlanes.addObstacle(x, y, random.nextInt(numberOfImages), new MovingVector(true, false, false, false),
                                hozSpeed, verSpeed, random.nextInt(2) + 1);
                        pane.getChildren().add(enemyPlanes.getObjectsOfObstacles().getLast().getImageView());
                    }
                }
                x += random.nextInt(3) * enemyWidth * gapScaleHor;
            }
        }

    }

    public boolean allObstaclesBehind(){
        for (int i = 0; i < bombs.getObjectsOfObstacles().size(); i++){
            if (player.getImageView().getY() > bombs.getObjectsOfObstacles().get(i).getImageView().getY())
                return false;
        }
        for (int i = 0; i < enemyPlanes.getObjectsOfObstacles().size(); i++){
            if (player.getImageView().getX() < enemyPlanes.getObjectsOfObstacles().get(i).getImageView().getX())
                return false;
        }

        return true;
    }

    public int getNextBackgroundId(int currentBackgroundId) {
        if (currentBackgroundId < 3)
            return currentBackgroundId + 1;
        else
            return 0;
    }
}

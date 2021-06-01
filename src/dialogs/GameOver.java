package dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameOver {
    private Text text;
    private Button restartButton;
    private Button menuButton;
    private boolean returnToMenu = false;
    private boolean restartGame = false;
    private TextField playerNameTextField;
    private Button confirmPlayerNameButton;
    private boolean saveDistance = false;

    public GameOver(double wWidth, double wHeigth, double px, Pane pane){
        String path = "fonts/blackchancery/BLKCHCRY.TTF";
        text = new javafx.scene.text.Text("Game Over");
        Font bigFont;
        try {
            bigFont = Font.loadFont(new FileInputStream(new File(path)), px);
        }catch (FileNotFoundException e){
            System.out.println("Nie znaleziono czcionki :." + path);
            return;
        }
        text.setFont(bigFont);
        text.setLayoutX(wWidth / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(wHeigth * 2 / 5 - text.getLayoutBounds().getHeight() / 2);
        text.setVisible(false);

        String buttonStyleWithoutPx = "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;" +
                "    -fx-background-radius: 8;" +
                "    -fx-background-color: " +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%)," +
                "        #9d4024," +
                "        #d86e3a," +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );" +
                "    -fx-font-weight: bold;" +
                "    -fx-font-size: ";

        restartButton = new Button("Restart");
        restartButton.setStyle(buttonStyleWithoutPx + px / 2.5 + ";");
        restartButton.setPrefSize(wWidth / 4, wHeigth / 12);
        restartButton.setLayoutX(wWidth / 3 - restartButton.getPrefWidth() / 2);
        restartButton.setLayoutY(wHeigth * 2.75 / 4 - restartButton.getPrefHeight() / 2);
        restartButton.setVisible(false);

        menuButton = new Button("Menu");
        menuButton.setStyle(buttonStyleWithoutPx + px / 2.5 + ";");
        menuButton.setPrefSize(wWidth / 4 , wHeigth / 12);
        menuButton.setLayoutX(wWidth * 2 / 3 - menuButton.getPrefWidth() / 2);
        menuButton.setLayoutY(wHeigth * 2.75 / 4 - menuButton.getPrefHeight() / 2);
        menuButton.setVisible(false);

        menuButton.setOnAction(actionEvent -> {
            if (!restartGame)
                returnToMenu = true;
        });
        restartButton.setOnAction(actionEvent -> {
            if (!returnToMenu)
                restartGame = true;
        });

        Font smallFont;
        try {
            smallFont = Font.loadFont(new FileInputStream(new File(path)), px / 4);
        }catch (FileNotFoundException e){
            System.out.println("Nie znaleziono czcionki :." + path);
            return;
        }

        playerNameTextField = new TextField();
        playerNameTextField.setPromptText("Enter your name pilot");
        playerNameTextField.setPrefSize(wWidth / 3, wHeigth/ 22);
        playerNameTextField.setFont(smallFont);
        playerNameTextField.setLayoutX(wWidth / 2 - playerNameTextField.getPrefWidth() / 2);
        playerNameTextField.setLayoutY(wHeigth / 2 - playerNameTextField.getPrefHeight() / 2);
        playerNameTextField.setVisible(false);

        confirmPlayerNameButton = new Button("Confirm");
        confirmPlayerNameButton.setStyle(buttonStyleWithoutPx + px / 4 + ";");
        confirmPlayerNameButton.setPrefSize(wWidth / 6, wHeigth/ 22);
        confirmPlayerNameButton.setLayoutX(playerNameTextField.getLayoutX() + playerNameTextField.getPrefWidth() * 1.05);
        confirmPlayerNameButton.setLayoutY(playerNameTextField.getLayoutY());
        confirmPlayerNameButton.setVisible(false);

        confirmPlayerNameButton.setOnAction(actionEvent -> {
            saveDistance = true;
        });

        pane.getChildren().add(text);
        pane.getChildren().add(menuButton);
        pane.getChildren().add(restartButton);
        pane.getChildren().add(playerNameTextField);
        pane.getChildren().add(confirmPlayerNameButton);
    }

    public boolean isReturnToMenu() {
        return returnToMenu;
    }

    public boolean isRestartGame() {
        return restartGame;
    }

    public void showGameOver(){
        text.setVisible(true);
        menuButton.setVisible(true);
        restartButton.setVisible(true);
        playerNameTextField.setVisible(true);
        confirmPlayerNameButton.setVisible(true);
    }

    public boolean isGameOverShowed(){
        return text.isVisible();
    }

    public boolean isSaveDistance() {
        return saveDistance;
    }

    public String getPlayerName(){
        return playerNameTextField.getText();
    }
}

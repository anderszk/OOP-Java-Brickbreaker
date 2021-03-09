package sample.breakoutGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class gamePaddle extends Rectangle {
    int height;

    /**
     * Creates the paddle in the game
     *
     * @param x the x-position to spawn in the game
     * @param y the y-position to spawn in the game
     * @param w the width of the paddle
     * @param h the heigth of the paddle
     * @param color the color of the paddle
     */
    public gamePaddle(int x, int y, int w, int h, Color color){
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
        setArcHeight(40.0);
        setArcWidth(26.0);
        getStyleClass().add("paddle");
    }

}




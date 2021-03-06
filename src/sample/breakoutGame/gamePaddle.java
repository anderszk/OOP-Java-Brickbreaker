package sample.breakoutGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class gamePaddle extends Rectangle{
    int height;

    public gamePaddle(int x, int y, int w, int h, Color color){
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
        setArcHeight(40.0);
        setArcWidth(26.0);
        getStyleClass().add("paddle");
    }


    public void movePaddleRight(){
        setTranslateX(getTranslateX() + 20);
    }
    public void movePaddleLeft(){
        setTranslateX(getTranslateX() - 20);
    }

}




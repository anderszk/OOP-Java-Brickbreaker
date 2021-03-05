package sample.breakoutGame;

import javafx.scene.shape.Circle;

public class gameBall extends Circle {
    private int x, y, r;

    public gameBall(int radius){
        super(radius);
        setTranslateX(325);
        setTranslateY(450);
    }

}

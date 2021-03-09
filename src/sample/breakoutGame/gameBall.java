package sample.breakoutGame;

import javafx.scene.shape.Circle;

public class gameBall extends Circle{

    /**
     * Creates an object of a gameball from the circleclass
     *
     * @param radius the radius of the ball
     */
    public gameBall(int radius){
        super(radius);
        setTranslateX(325);
        setTranslateY(450);
    }

}

package sample.breakoutGame;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class gameBrick extends Rectangle {

    private int lifePoints;
    private boolean isDestroyed;

    public gameBrick(int lifePoints, int width, int height){
        super(width, height);
        this.lifePoints = lifePoints;
    }

    public void setDestroyed(){
        this.isDestroyed = true;
    }

    public boolean getDestroyed(){
        return this.isDestroyed;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void decreaseLifepoints(){
        if (getLifePoints() > 1){
            setLifePoints(getLifePoints() - 1);
        }
        else{
            //removeBrick();
        }
    }

    public void popBrick(AnchorPane pane){
        pane.getChildren().remove(this);
    }

}

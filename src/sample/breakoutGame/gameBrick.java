package sample.breakoutGame;

import javafx.scene.shape.Rectangle;

public class gameBrick extends Rectangle{

    private int lifePoints;
    private boolean isDestroyed;
    private final long value;

    /**
     * Creates a brick from the rectangle class
     *
     * @param lifePoints Amount of hits necessary to break it
     * @param width Width of the recatangle
     * @param height height of the rectangle
     * @param value The value (points) the player receives when broken
     */
    public gameBrick(int lifePoints, int width, int height, long value){
        super(width, height);
        this.lifePoints = lifePoints;
        this.value = value;
    }

    /**
     * Gets the value of the brick selected
     *
     * @return brickvalue
     */
    public long getValue(){
        return this.value;
    }

    /**
     *Sets the states of the brick to broken
     */
    public void setDestroyed(){
        this.isDestroyed = true;
    }

    /**
     * Gets the state of the brick
     *
     * @return state of brick
     */
    public boolean getDestroyed(){
        return this.isDestroyed;
    }

    /**
     * Gets the selected bricks lifepoints
     *
     * @return lifepoints of brick
     */
    public int getLifePoints() {
        return this.lifePoints;
    }

    /**
     *Sets the lifepoints of the brick
     *
     * @param lifePoints
     */
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    /**
     *Decrement the lifepoints of the selected brick by 1
     */
    public void decreaseLifepoints(){
        if (getLifePoints() > 1){
            setLifePoints(getLifePoints() - 1);
        }
        else{
            setDestroyed();
        }
    }

}

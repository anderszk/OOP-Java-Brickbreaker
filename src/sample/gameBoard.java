package sample;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

public class gameBoard{

    //Må detektere når brukeren trykker på quit eller krysser ut, litt usikker på hvordan man dettekterer den siste.
    private int sessionCount;
    private long currentBest;
    private long score = 0;

    /**
     *Getters and setters for the private fields
     */

    public int getSessionCount(){
        return this.sessionCount;
    }
    public void addSessionCount(){
        this.sessionCount += 1;
    }

    public long getCurrentBest(){
        return this.currentBest;
    }
    public void setCurrentBest(long newBest){
        this.currentBest = newBest;
    }

    public void updateScore(Text score, long points){
        this.score += points;
        if (this.score > 999999){
            this.score = 999999;
            score.setText(String.valueOf(this.score));
        }
        else {
            score.setText(String.valueOf(this.score));
        }
    }
    public long getScore(){
        return this.score;
    }


    /**
     *Creates the paddle in the selected gamepane
     *
     * @param pane the pane the paddle is being added to
     * @param pad the paddle that is going to be added
     */
    protected void createPaddle(AnchorPane pane, gamePaddle pad){
        pane.getChildren().add(pad);
    }

    /**
     * Creates the bricks in the gameboard
     *
     * @param pane the pane the bricks should be added to
     */
    protected void createBricks(GridPane pane){
        for(int i=0; i < 9; i++){
            gameBrick brick1 = new gameBrick(2, 70, 25, 50000);
            gameBrick brick2 = new gameBrick(2, 70, 25, 30000);
            gameBrick brick3 = new gameBrick(2, 70, 25,18750);
            gameBrick brick4 = new gameBrick(2, 70, 25,85000);

            brick1.getStyleClass().add("topbrick");
            brick2.getStyleClass().add("middlebrick1");
            brick3.getStyleClass().add("middlebrick2");
            brick4.getStyleClass().add("bottombrick");


            pane.add(brick1, i, 0);
            pane.add(brick2, i, 1);
            pane.add(brick3, i, 2);
            pane.add(brick4, i, 3);
        }

    }

    /**
     * Creates the ball
     *
     * @param pane the pane the ball should be added to
     * @param ball the ball that is being added
     */
    protected void createBall(AnchorPane pane, gameBall ball){
        pane.getChildren().add(ball);
        ball.getStyleClass().add("ball");
    }


    /**
     * Blinks the selected node in with x-milliseconds delay
     *
     * @param element the node that should blink
     * @param duration the delay between each blink in millis
     */
    public void blinker(Node element, int duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), element);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }


}



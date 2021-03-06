package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

public class gameBoard{

    //Må detektere når brukeren trykker på quit eller krysser ut, litt usikker på hvordan man dettekterer den siste.
    private int sessionCount;
    private long currentBest;
    private long score = 0;

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


    protected void createPaddle(AnchorPane pane, gamePaddle pad){
        pane.getChildren().add(pad);
    }
    protected void createBricks(GridPane pane){
        for(int i=0; i < 9; i++){
            gameBrick brick1 = new gameBrick(2, 70, 25, 10000);
            gameBrick brick2 = new gameBrick(2, 70, 25, 30000);
            gameBrick brick3 = new gameBrick(2, 70, 25,18750);
            gameBrick brick4 = new gameBrick(2, 70, 25,12500);

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
    protected void createBall(AnchorPane pane, gameBall ball){
        pane.getChildren().add(ball);
        ball.getStyleClass().add("ball");
    }


}



package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

import java.util.ArrayList;
import java.util.List;

public class gameBoard{

    //Må detektere når brukeren trykker på quit eller krysser ut, litt usikker på hvordan man dettekterer den siste.
    private int sessionCount = 50;
    private long currentBest = 265389;
    private long score = 0;

    private List<gameBrick> top_brick = new ArrayList<>();
    private List<gameBrick> middle1_brick = new ArrayList<>();
    private List<gameBrick> middle2_brick = new ArrayList<>();
    private List<gameBrick> bottom_brick = new ArrayList<>();




    public int getSessionCount(){
        return this.sessionCount;
    }
    public long getCurrentBest(){
        return this.currentBest;
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
    protected void createBricks(AnchorPane pane){

        for(int k=0; k < 9; k++){
            this.top_brick.add(new gameBrick(1, 70, 25));
            this.middle1_brick.add(new gameBrick(1, 70, 25));
            this.middle2_brick.add(new gameBrick(1, 70, 25));
            this.bottom_brick.add(new gameBrick(1, 70, 25));
        }

        for(int i=0; i < this.top_brick.size(); i++){
            this.top_brick.get(i).setTranslateX(i*80+49);
            this.top_brick.get(i).setTranslateY(60);
            this.middle1_brick.get(i).setTranslateX(i*80+49);
            this.middle1_brick.get(i).setTranslateY(90);
            this.middle2_brick.get(i).setTranslateX(i*80+49);
            this.middle2_brick.get(i).setTranslateY(120);
            this.bottom_brick.get(i).setTranslateX(i*80+49);
            this.bottom_brick.get(i).setTranslateY(150);
            pane.getChildren().add(top_brick.get(i));
            pane.getChildren().add(middle1_brick.get(i));
            pane.getChildren().add(middle2_brick.get(i));
            pane.getChildren().add(bottom_brick.get(i));
            top_brick.get(i).getStyleClass().add("topbrick");
            middle1_brick.get(i).getStyleClass().add("middlebrick1");
            middle2_brick.get(i).getStyleClass().add("middlebrick2");
            bottom_brick.get(i).getStyleClass().add("bottombrick");
        }

    }

    protected void createBall(AnchorPane pane, gameBall ball){
        pane.getChildren().add(ball);
        ball.getStyleClass().add("ball");
    }

    public void ballStop(gameBall ball) {
        ball.setTranslateY(400);
        ball.setTranslateX(300);
    }

}



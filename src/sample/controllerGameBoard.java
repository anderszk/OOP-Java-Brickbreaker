package sample;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerGameBoard implements Initializable {

    private final gamePaddle paddle = new gamePaddle(325, 500, 150, 25, Color.RED);
    private final gameBall ball = new gameBall(12);
    private final gameBrick brick = new gameBrick(1, 70, 25);
    private final gameBoard gb = new gameBoard();
    private final Hiscores hs = new Hiscores();
    private int xSpeed = 6;
    private int ySpeed = 6;
    private long points;

    @FXML
    AnchorPane gamePane;
    @FXML
    Text score;
    @FXML
    GridPane gridBrick;

    public  AnimationTimer timer = new AnimationTimer() {
        double speed = 0;
        @Override
        public void handle(long now) {
            Scene gameScene = gamePane.getScene();
            if(paddle.getTranslateX() < 0) {
                paddle.setTranslateX(0);
                speed = 0;
            }
            else if(paddle.getTranslateX() > 645){
                paddle.setTranslateX(645);
                speed = 0;
            }
            else{
                gameScene.setOnKeyPressed(f -> {
                            switch (f.getCode()) {
                                case A -> {
                                    if (paddle.getTranslateX() <= 0) {
                                        speed = 0;
                                    } else {
                                        speed = -8;
                                    }
                                }
                                case D -> {
                                    if (paddle.getTranslateX() >= 645) {
                                        speed = 0;
                                    } else {
                                        speed = 8;
                                    }
                                }
                            }
                        }
                );
            }
            paddle.setTranslateX(paddle.getTranslateX()+speed);

    }};

    public  AnimationTimer runGame = new AnimationTimer() {

        @Override
        public void handle(long now) {
           {

                if (ball.getTranslateY() > 600){
                    ball.setTranslateY(600);
                    //Må finne en boolsk måte å få den til å quitte ut av loopen  sånn at vi får stoppet timeren
                }
                else{
                    for (int i=0; i < gridBrick.getChildren().size(); i++){
                        if(new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(gridBrick.getChildren().get(i).getBoundsInParent().getMinX() + 35, gridBrick.getChildren().get(i).getBoundsInParent().getCenterY() + 70, 70, 20) && ((gameBrick)gridBrick.getChildren().get(i)).getDestroyed() != true){
                            int x = i/4;
                            int y =  i%4;
                            ((gameBrick)gridBrick.getChildren().get(i)).setDestroyed();
                            gridBrick.getChildren().get(i).setVisible(false);
                            ySpeed = 6;
                            ball.setTranslateY(ball.getTranslateY() + ySpeed);
                            gb.updateScore(score, 19000L);
                        }
                    }

                    if (ball.getTranslateX() < 10){
                        xSpeed = 6;
                        ball.setTranslateX(ball.getTranslateX()+xSpeed);
                    }
                    else if(ball.getTranslateX() > 780){
                        xSpeed = -6;
                        ball.setTranslateX(ball.getTranslateX()+xSpeed);
                    }
                    else if(ball.getTranslateY() < 10){
                        ySpeed = 6;
                        ball.setTranslateY(ball.getTranslateY()+ySpeed);
                    }

                    else if(new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX()+10, paddle.getTranslateY()+2,145, 15)){
                        ySpeed = -6;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                    }
                    else{
                        ball.setTranslateY(ball.getTranslateY()+ySpeed);
                        ball.setTranslateX(ball.getTranslateX()+xSpeed);
                    }

                }
            }
        }
    };

    @FXML
    private void toMainMenu(ActionEvent e) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gamePane.getChildren().setAll(pane);
    }
    @FXML
    public void move(KeyEvent event) throws InterruptedException {
        if(event.getCode() == KeyCode.A  ^ event.getCode() == KeyCode.D) {
            timer.start();
        }
    }
    @FXML
    public void stop(KeyEvent event) throws InterruptedException {
        timer.stop();
    }
    @FXML
    public void setScore(long score){
        this.score.setText(String.valueOf(score));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gb.createPaddle(gamePane, paddle);
        gb.createBricks(gridBrick);
        gb.createBall(gamePane, ball);
        gridBrick.setAlignment(Pos.CENTER);
        paddle.getStyleClass().add("paddle");
        paddle.setArcHeight(40.0);
        paddle.setArcWidth(26.0);
        System.out.println(paddle.getTranslateX());
        runGame.start();
        System.out.println(gridBrick.getChildren().get(2).getParent().getBoundsInParent());
        System.out.println(gridBrick.getChildren().get(2).getParent().getBoundsInParent().getMinX());
        System.out.println(gridBrick.getChildren().get(2).getParent().getBoundsInParent().getMinY());

    }
}


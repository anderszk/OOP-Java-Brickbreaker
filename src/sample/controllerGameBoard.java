package sample;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
    private gameBoard gb = new gameBoard();
    private Hiscores hs = new Hiscores();
    private int xSpeed = 5;
    private int ySpeed = 5;
    private long points;

    @FXML
    AnchorPane gamePane;
    @FXML
    Text score;

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
                                        speed = -7;
                                    }
                                }
                                case D -> {
                                    if (paddle.getTranslateX() >= 645) {
                                        speed = 0;
                                    } else {
                                        speed = 7;
                                    }
                                }
                            }
                        }
                );
            }
            paddle.setTranslateX(paddle.getTranslateX()+speed);
            System.out.println(paddle.getTranslateX());

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
                    if (ball.getTranslateX() < 10){
                        xSpeed = 5;
                        ball.setTranslateX(ball.getTranslateX()+xSpeed);
                    }
                    else if(ball.getTranslateX() > 780){
                        xSpeed = -5;
                        ball.setTranslateX(ball.getTranslateX()+xSpeed);
                    }
                    else if(ball.getTranslateY() < 10){
                        ySpeed = 5;
                        ball.setTranslateY(ball.getTranslateY()+ySpeed);
                    }

                    else if(new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(brick.getTranslateX(),brick.getTranslateY()+2,145, 15)){
                        ySpeed = 5;
                        ball.setTranslateY(ball.getTranslateY()+ySpeed);
                        gb.updateScore(score, 9000);
                    }
                    else if(new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX(), paddle.getTranslateY()+2,145, 15)){
                        ySpeed = -5;
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
        gb.createBricks(gamePane);
        gb.createBall(gamePane, ball);
        paddle.getStyleClass().add("paddle");
        paddle.setArcHeight(40.0);
        paddle.setArcWidth(26.0);
        System.out.println(paddle.getTranslateX());
        runGame.start();
    }
}

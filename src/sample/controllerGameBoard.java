package sample;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerGameBoard implements Initializable {

    private final gamePaddle paddle = new gamePaddle(325, 500, 150, 25, Color.RED);
    private final gameBall ball = new gameBall(12);
    private final gameBoard gb = new gameBoard();
    private final Hiscores hs = new Hiscores();
    private final Text startText = new Text("Press m to start!");
    private final Text instructions = new Text("<- A, D ->");
    private int xSpeed = 6;
    private int ySpeed = 6;

    @FXML
    AnchorPane gamePane;
    @FXML
    Text score;
    @FXML
    Text scoreString;
    @FXML
    GridPane gridBrick;


    public void blinker(Node element, int duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), element);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        System.out.println("Blinker on@@@@");
    }

    public AnimationTimer timer = new AnimationTimer() {
        double speed = 0;

        @Override
        public void handle(long now) {
            Scene gameScene = gamePane.getScene();
            if (paddle.getTranslateX() < 0) {
                paddle.setTranslateX(0);
                speed = 0;
            } else if (paddle.getTranslateX() > 645) {
                paddle.setTranslateX(645);
                speed = 0;
            } else {
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
            paddle.setTranslateX(paddle.getTranslateX() + speed);

        }
    };

    public AnimationTimer runGame = new AnimationTimer() {
        public int finished;

        @Override
        public void handle(long now) {
            {
                for (int i = 0; i < gridBrick.getChildren().size(); i++) {
                    if ((gridBrick.getChildren().get(i)).isVisible() == false) {
                        finished++;
                    }
                }

                if (finished >= 36) {
                    this.stop();
                    System.out.println("Youre done!");
                }

                if (ball.getTranslateY() > 600) {
                    ball.setTranslateY(600);
                    this.stop();
                    resetFailed();

                } else {
                    for (int i = 0; i < gridBrick.getChildren().size(); i++) {
                        if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(gridBrick.getChildren().get(i).getBoundsInParent().getMinX() + 35, gridBrick.getChildren().get(i).getBoundsInParent().getCenterY() + 80, 70, 20) && ((gameBrick) gridBrick.getChildren().get(i)).getDestroyed() != true) {

                            ((gameBrick) gridBrick.getChildren().get(i)).decreaseLifepoints();
                            blinker(gridBrick.getChildren().get(i), 400);
                            if (((gameBrick) gridBrick.getChildren().get(i)).getDestroyed() == true) {
                                gridBrick.getChildren().get(i).setVisible(false);
                                gb.updateScore(score, ((gameBrick) gridBrick.getChildren().get(i)).getValue());
                            }

                            ySpeed = 6;
                            ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        }
                    }

                    if (ball.getTranslateX() < 10) {
                        xSpeed = 6;
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    } else if (ball.getTranslateX() > 780) {
                        xSpeed = -6;
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    } else if (ball.getTranslateY() < 10) {
                        ySpeed = 6;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                    } else if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX() + 10, paddle.getTranslateY() + 2, 145, 15)) {
                        ySpeed = -6;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                    } else {
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    }

                }
                finished = 0;
            }
        }
    };

    @FXML
    public void resetFailed() {
        gamePane.getChildren().remove(gridBrick);
        gamePane.getChildren().remove(score);
        gamePane.getChildren().remove(scoreString);

        Text failedText = new Text("HAHA! You failed!");
        Button restartButton = new Button("Press here to restart!");

        failedText.getStyleClass().add("failedText");
        restartButton.getStyleClass().add("restartButton");

        failedText.setFont(Font.font("Super Legend Boy", 32));
        failedText.setFill(Color.WHITE);
        restartButton.setFont(Font.font("Super Legend Boy", 15));
        restartButton.setTextFill(Color.WHITE);

        failedText.setTranslateX(200);
        failedText.setTranslateY(200);
        restartButton.setTranslateX(gamePane.getPrefWidth()/2-135);
        restartButton.setTranslateY(330);

        restartButton.setOnAction((event) -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("gameBoard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

            System.out.println("SHOULD RESTART");
        });

        blinker(restartButton, 600);

        gamePane.getChildren().add(failedText);
        gamePane.getChildren().add(restartButton);
    }

    @FXML
    public void resetFinished() throws IOException {
        hs.writeHS("Anders", String.valueOf(gb.getScore()));
        if (gb.getCurrentBest() < gb.getScore()){
            gb.setCurrentBest(gb.getScore());
        }

    }

    @FXML
    private void toMainMenu (ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void move (KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.A ^ event.getCode() == KeyCode.D) {
            timer.start();
        }
        else if (event.getCode() == KeyCode.M){
            gb.addSessionCount();

            removeStartTexts();

            try {
                gb.createBall(gamePane, ball);
                gb.createPaddle(gamePane, paddle);
            }
            catch(RuntimeException e) {
                System.out.println("Ball already on the field!");
            }

            runGame.start();
        }
    }
    @FXML
    public void stop (KeyEvent event) throws InterruptedException {
        timer.stop();
    }

    @FXML
    private void setStartTexts(){
        startText.setTranslateX(250);
        startText.setTranslateY(350);
        instructions.setTranslateX(300);
        instructions.setTranslateY(420);

        startText.getStyleClass().add("instructions");
        instructions.getStyleClass().add("instructions");

        startText.setFont(Font.font("Super Legend Boy", 24));
        startText.setFill(Color.WHITE);
        instructions.setFont(Font.font("Super Legend Boy", 24));
        instructions.setFill(Color.WHITE);

        gamePane.getChildren().add(startText);
        gamePane.getChildren().add(instructions);

        blinker(startText, 600);
        blinker(instructions, 600);
    }
    @FXML
    private void removeStartTexts(){
        gamePane.getChildren().remove(instructions);
        gamePane.getChildren().remove(startText);
    }



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        gb.createBricks(gridBrick);
        gridBrick.setAlignment(Pos.CENTER);
        setStartTexts();
    }
}



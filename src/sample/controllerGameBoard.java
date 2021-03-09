package sample;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.breakoutGame.gameBall;
import sample.breakoutGame.gameBrick;
import sample.breakoutGame.gamePaddle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerGameBoard implements Initializable{
    private final Media losemp3 = new Media(new File("sound/lose.mp3").toURI().toString());
    private final MediaPlayer mediaPlayer1 = new MediaPlayer(losemp3);
    private final Media winmp3 = new Media(new File("sound/win.mp3").toURI().toString());
    private final MediaPlayer mediaPlayer2 = new MediaPlayer(winmp3);

    private final gamePaddle paddle = new gamePaddle(325, 500, 150, 25, Color.RED);
    private final gameBall ball = new gameBall(12);
    private final gameBoard gb = new gameBoard();
    private final Hiscores hs = new Hiscores();
    private final Pane finishedPane = new Pane();
    private Player player = new Player(null);
    private final Text startText = new Text("Press m to start!");
    private final Text instructions = new Text("<- A, D ->");
    private final TextField username = new TextField();
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
    @FXML
    Pane playerPane;

    /**
     * The timer for the paddle, also contains logic for the gamepaddle
     */
    @FXML
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
    /**
     * The timer for the ball and gamelogic
     */
    @FXML
    public AnimationTimer runGame = new AnimationTimer() {
        public int finished;

        @Override
        public void handle(long now) {
            {
                for (int i = 0; i < gridBrick.getChildren().size(); i++) {
                    if (!(gridBrick.getChildren().get(i)).isVisible()) {
                        finished++;
                    }
                }

                if (finished >= 36) {
                    this.stop();
                    resetFinished();
                }

                if (ball.getTranslateY() > 600) {
                    ball.setTranslateY(600);
                    this.stop();
                    try {
                        hs.writePlayerInfo(player.getBestScore(), player.getCount());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        resetFailed();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    for (int i = 0; i < gridBrick.getChildren().size(); i++) {
                        if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(gridBrick.getChildren().get(i).getBoundsInParent().getMinX() + 35, gridBrick.getChildren().get(i).getBoundsInParent().getCenterY() + 80, 70, 20) && !((gameBrick) gridBrick.getChildren().get(i)).getDestroyed()) {
                            Media sound = new Media(new File("sound/hit.m4a").toURI().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(sound);
                            mediaPlayer.play();

                            ((gameBrick) gridBrick.getChildren().get(i)).decreaseLifepoints();
                            gb.blinker(gridBrick.getChildren().get(i), 400);
                            if (((gameBrick) gridBrick.getChildren().get(i)).getDestroyed()) {
                                gridBrick.getChildren().get(i).setVisible(false);
                                gb.updateScore(score, ((gameBrick) gridBrick.getChildren().get(i)).getValue());
                                player.setScore(player.getScore() + ((gameBrick) gridBrick.getChildren().get(i)).getValue());
                            }

                            ySpeed = -ySpeed;
                            ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        }
                    }

                    if (ball.getTranslateX() < 10) {
                        xSpeed = -xSpeed;
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    } else if (ball.getTranslateX() > 780) {
                        xSpeed = -xSpeed;
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    } else if (ball.getTranslateY() < 10) {
                        ySpeed = -ySpeed;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                    } else if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX() + 15, paddle.getTranslateY() + 2, 35, 15)) {
                        Media sound = new Media(new File("sound/boink.m4a").toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                        ySpeed = -3;
                        xSpeed = -9;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);

                    } else if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX() + 40, paddle.getTranslateY() + 2, 80, 15)) {
                        Media sound = new Media(new File("sound/boink.m4a").toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                        if (Math.abs(xSpeed) != xSpeed) {
                            ySpeed = -8;
                            xSpeed = -4;
                        } else {
                            xSpeed = 4;
                            ySpeed = -8;
                        }
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);

                    } else if (new Rectangle(ball.getTranslateX(), ball.getTranslateY(), 12, 12).intersects(paddle.getTranslateX() + 110, paddle.getTranslateY() + 2, 35, 15)) {
                        Media sound = new Media(new File("sound/boink.m4a").toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                        ySpeed = -3;
                        xSpeed = 9;
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    } else {
                        ball.setTranslateY(ball.getTranslateY() + ySpeed);
                        ball.setTranslateX(ball.getTranslateX() + xSpeed);
                    }

                }
                finished = 0;
            }
        }
    };


    /**
     * Executed to get the finishedscreen when popping all the bricks
     *
     * @throws IOException
     */
    @FXML
    public void resetFailed() throws IOException {
        mediaPlayer1.setStopTime(Duration.seconds(14));
        mediaPlayer1.play();

        System.out.println(player.getScore());

        hs.writeHS(player.getUsername(), String.valueOf(player.getScore()));

        gamePane.getChildren().remove(gridBrick);
        gamePane.getChildren().remove(score);
        gamePane.getChildren().remove(scoreString);

        Text failedText = new Text("HAHA! You failed!");
        Text failedScore = new Text();
        Button restartButton = new Button("Press here to restart!");

        failedText.getStyleClass().add("failedText");
        restartButton.getStyleClass().add("restartButton");
        failedScore.setText("Score: " + player.getScore());

        failedText.setFont(Font.font("Super Legend Boy", 32));
        failedText.setFill(Color.WHITE);
        failedScore.setFont(Font.font("Super Legend Boy", 22));
        failedScore.setFill(Color.WHITE);
        restartButton.setFont(Font.font("Super Legend Boy", 15));
        restartButton.setTextFill(Color.WHITE);

        failedText.setTranslateX(200);
        failedText.setTranslateY(200);
        failedScore.setTranslateX(275);
        failedScore.setTranslateY(280);
        restartButton.setTranslateX(gamePane.getPrefWidth() / 2 - 135);
        restartButton.setTranslateY(330);

        restartButton.setOnAction(event -> {
            resetScene(event);
            mediaPlayer1.stop();
        });

        gb.blinker(restartButton, 600);

        gamePane.getChildren().add(failedText);
        gamePane.getChildren().add(failedScore);
        gamePane.getChildren().add(restartButton);
    }

    /**
     * Executed when the player miss the ball.
     */
    @FXML
    public void resetFinished() {
        mediaPlayer2.play();
        try {
            hs.writeHS(player.getUsername(), String.valueOf(player.getScore()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (player.getBestScore() < player.getScore()) {
            player.setBestScore(player.getScore());
        }

        this.gamePane.getChildren().remove(ball);
        this.gamePane.getChildren().remove(score);
        this.gamePane.getChildren().remove(paddle);
        this.gamePane.getChildren().remove(scoreString);
        this.gamePane.getChildren().remove(gridBrick);

        this.finishedPane.setPrefWidth(500);
        this.finishedPane.setPrefHeight(350);

        Button toMenu = new Button("Main Menu");
        Button toHighscores = new Button("Highscores");
        Button restart = new Button("Restart");
        Text finishedMessage = new Text(120, 80, "Well Done!");
        Text finishedScore = new Text(130, 180, "Score:");

        finishedScore.setText("Score: " + player.getScore());
        gb.blinker(gamePane, 195);

        toMenu.setTranslateX(30);
        toHighscores.setTranslateX(180);
        restart.setTranslateX(330);
        this.finishedPane.setTranslateX(150);

        toMenu.setTranslateY(285);
        toHighscores.setTranslateY(285);
        restart.setTranslateY(285);
        this.finishedPane.setTranslateY(100);

        restart.setOnAction((event) -> {
            resetScene(event);
            mediaPlayer2.stop();
        });
        toMenu.setOnAction((event) -> {
            mediaPlayer2.stop();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert root != null;
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });
        toHighscores.setOnAction((event) -> {
            mediaPlayer2.stop();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("hiscores.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });


        toMenu.getStyleClass().add("finishedButton");
        toHighscores.getStyleClass().add("finishedButton");
        restart.getStyleClass().add("finishedButton");
        this.finishedPane.getStyleClass().add("finishedPane");
        finishedMessage.getStyleClass().add("finishedMessage");
        finishedScore.getStyleClass().add("finishedScore");

        gb.blinker(finishedMessage, 600);
        gb.blinker(finishedScore, 600);

        this.finishedPane.getChildren().add(toMenu);
        this.finishedPane.getChildren().add(toHighscores);
        this.finishedPane.getChildren().add(restart);
        this.finishedPane.getChildren().add(finishedScore);
        this.finishedPane.getChildren().add(finishedMessage);

        this.gamePane.getChildren().add(finishedPane);

    }

    /**
     *Resets the whole scene. - Reload
     *
     * @param event holds the event from the mouseclick
     */
    @FXML
    private void resetScene(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("app.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    private void toMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Moves the ball on keypress
     *
     * @param event the mouseevent
     * @throws InterruptedException
     */
    @FXML
    public void move(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.A ^ event.getCode() == KeyCode.D) {
            timer.start();
        } else if (event.getCode() == KeyCode.M) {
            gb.addSessionCount();

            removeStartTexts();

            try {
                gb.createBall(gamePane, ball);
                gb.createPaddle(gamePane, paddle);
            } catch (RuntimeException e) {
                System.out.println("Ball already on the field!");
            }

            runGame.start();
        }
    }

    /**
     * Stops the ball on keyreleased
     *
     * @param event the mouseevent
     */
    @FXML
    public void stop(KeyEvent event) {
        timer.stop();
    }


    /**
     * Sets the instruction on load
     */
    @FXML
    private void setStartTexts() {
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

        gb.blinker(startText, 600);
        gb.blinker(instructions, 600);
    }

    /**
     * Removes the text when player starts the game
     */
    @FXML
    private void removeStartTexts() {
        gamePane.getChildren().remove(instructions);
        gamePane.getChildren().remove(startText);
    }

    /**
     *Contains the usernamepane where the player enters their username
     */
    @FXML
    public void createPlayerPane() {
        Button startButton = new Button("Start!");
        Text instructions = new Text(42, 70, "Enter you username:");
        username.setPromptText("username");
        playerPane.getStyleClass().add("finishedPane");
        startButton.setTranslateX(118);
        startButton.setTranslateY(200);
        username.setTranslateX(75);
        username.setTranslateY(120);
        startButton.getStyleClass().add("startButton");
        instructions.getStyleClass().add("playerInstructions");
        username.getStyleClass().add("usernameField");
        playerPane.getChildren().add(startButton);
        playerPane.getChildren().add(instructions);
        playerPane.getChildren().add(username);
        startButton.setOnAction((event) -> {
            sendUserName();
        });
    }

    /**
     * Retrieves the text from the textfield, stores it and starts the game
     */
    @FXML
    public void sendUserName() {
        if (username.getText().isEmpty() != true) {
            this.player = new Player(username.getText());
            this.gamePane.getChildren().remove(playerPane);
            System.out.println(player.getUsername());
            setStartTexts();
            gb.createBricks(gridBrick);
        } else {
            return;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gb.createBricks(gridBrick);
        gridBrick.setAlignment(Pos.CENTER);
        createPlayerPane();
    }
}



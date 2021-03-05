package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerMainMenu implements Initializable {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button playButton, hiscoreButton, exitButton;
    @FXML
    private GridPane gridBrick;

    @FXML
    public void toGame(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gameBoard.fxml"));
        Scene gameScene = new Scene(root);
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    @FXML
    public void toHiscores(ActionEvent e) throws IOException {
        AnchorPane hiscores = FXMLLoader.load(getClass().getResource("hiscores.fxml"));
        rootPane.getChildren().setAll(hiscores);
    }

    @FXML
    public void quitApplication(ActionEvent e){
        //sende data til filen, også ved detect force quit
        Platform.exit();
        System.out.println("Quitting application");
    }

    @FXML
    public void blinkerButton(Button button) {
        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(button);
        fadeIn.setToValue(1);
        button.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(button);
        fadeOut.setToValue(0.6);
        button.setOnMouseExited(e -> fadeOut.playFromStart());

        button.setOpacity(0.6);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new MainMenu().blinker(rootPane.lookup("#title"));
        blinkerButton(playButton);
        blinkerButton(hiscoreButton);
        blinkerButton(exitButton);
    }

}

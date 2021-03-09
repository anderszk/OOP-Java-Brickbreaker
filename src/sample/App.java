package sample;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {

    /**
     * Highlights the button on hover
     *
     * @param button The selected button to run the animation on
     */
    public void highlightButton(Button button) {
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

    /**
     * Makes the node blink once every second
     *
     * @param element the node to blink
     */
    public void blinker(Node element) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), element);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    /**
     * Starts the application
     *
     * @param stage the stage to be shown
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        blinker(root.lookup("#title"));
        stage.setScene(scene);
        stage.setTitle("TDT4100 - MAIN MENU - Anders og Elvira");
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        new Hiscores().makeFiles();
        launch(args);
    }
}

package sample;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainMenu extends Application{

    public void blinker(Node element) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), element);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    @Override
    public void start(Stage stage) throws Exception { ;
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

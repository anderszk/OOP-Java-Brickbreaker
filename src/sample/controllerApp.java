package sample;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerApp implements Initializable {

    App app = new App();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button playButton, hiscoreButton, exitButton;
    @FXML
    private GridPane gridBrick;

    /**
     * Redirects the user to the game
     *
     * @param e holding the clickevent from the button
     * @throws IOException
     */
    @FXML
    public void toGame(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
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

        Platform.exit();
        System.out.println("Quitting application");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        app.blinker(rootPane.lookup("#title"));
        app.highlightButton(playButton);
        app.highlightButton(hiscoreButton);
        app.highlightButton(exitButton);
    }

}

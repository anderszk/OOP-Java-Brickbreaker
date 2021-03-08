package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class controllerHiscores implements Initializable {

    @FXML
    private AnchorPane hiscorePane;
    @FXML
    private BarChart last5;
    @FXML
    private NumberAxis y;
    @FXML
    private Text playerHiscore, playerGameCount, playerSessions;

    private final Hiscores hs = new Hiscores();

    @FXML
    private void toMainMenu() throws IOException {
        System.out.println("To main menu");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        hiscorePane.getChildren().setAll(pane);
    }


    @FXML
    private void setScores(){
        try {
            hs.readHS();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> names = hs.getHSNames();
        List<Long> scores = hs.getHSScores();

        for (int i =0; i < hs.getHSScores().size(); i++) {
            Text name = (Text)(hiscorePane.lookup("#name" + (i+1)));
            Text score = (Text)(hiscorePane.lookup("#score" + (i+1)));
            name.setText(names.get(i));
            score.setText(String.valueOf(scores.get(i)));
        }

    }

    @FXML
    private void setPerson(){
        try {
           hs.updatePlayerInfo(playerHiscore, playerGameCount, playerSessions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setGraph() throws FileNotFoundException {
        List<String> scoresToBeAdded = new ArrayList<>();
        List<String> allScores = new ArrayList<>();

        File playerfile = new File("storedPlayer.txt");
        Scanner read = new Scanner(playerfile);
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String[] data = line.split(":");
            allScores.add(data[0]);
        }
        read.close();

        for(int i=allScores.size()-1; i > allScores.size()-6; i--){
            scoresToBeAdded.add(allScores.get(i));
        }

        Collections.reverse(scoresToBeAdded);

        System.out.println(scoresToBeAdded.size());

        XYChart.Series scores = new XYChart.Series();

        for(int i=0; i < scoresToBeAdded.size(); i++){
            scores.getData().add(new XYChart.Data(String.valueOf(5-i), Long.parseLong(scoresToBeAdded.get(i))));
        }

        this.last5.getData().add(scores);
        this.last5.setCategoryGap(1);
        this.last5.setBarGap(1);
        this.last5.getXAxis().setOpacity(0);
        this.y.setTickLabelFont(Font.font("Super Legend Boy", 7));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setScores();
        try {
            setGraph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setPerson();
    }
}

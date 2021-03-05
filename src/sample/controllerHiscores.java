package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class controllerHiscores implements Initializable {

    @FXML
    private AnchorPane hiscorePane;
    @FXML
    private Pane graphPane;
    @FXML
    private BarChart last5;
    @FXML
    private NumberAxis y;
    @FXML
    private Text name1,name2,name3,name4,name5,name6,name7,name8,name9,name10;
    @FXML
    private Text score1,score2,score3,score4,score5,score6,score7,score8,score9,score10;
    @FXML
    private Text playerHiscore, playerGameCount, playerSessions;


    @FXML
    private void toMainMenu() throws IOException {
        System.out.println("To main menu");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        hiscorePane.getChildren().setAll(pane);
    }

    @FXML
    private void setScores(){
        Hiscores hs = new Hiscores();
        try {
            hs.readHS();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> names = hs.getHSNames();
        List<Long> scores = hs.getHSScores();

        name1.setText(names.get(0));
        name2.setText(names.get(1));
        name3.setText(names.get(2));
        name4.setText(names.get(3));
        name5.setText(names.get(4));
        name6.setText(names.get(5));
        name7.setText(names.get(6));
        name8.setText(names.get(7));
        name9.setText(names.get(8));
        name10.setText(names.get(9));


        score1.setText(String.valueOf(scores.get(0)));
        score2.setText(String.valueOf(scores.get(1)));
        score3.setText(String.valueOf(scores.get(2)));
        score4.setText(String.valueOf(scores.get(3)));
        score5.setText(String.valueOf(scores.get(4)));
        score6.setText(String.valueOf(scores.get(5)));
        score7.setText(String.valueOf(scores.get(6)));
        score8.setText(String.valueOf(scores.get(7)));
        score9.setText(String.valueOf(scores.get(8)));
        score10.setText(String.valueOf(scores.get(9)));
    }

    @FXML
    private void setPerson(){
        try {
            new Hiscores().updatePlayerInfo(playerHiscore, playerGameCount, playerSessions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setGraph(){
        //Les av siste scores;
        List<Long> last5 = new ArrayList<>();
        last5.add(503467L);
        last5.add(655590L);
        last5.add(980832L);
        last5.add(236452L);
        last5.add(999432L);

        XYChart.Series scores = new XYChart.Series();

        for(int i=0; i < last5.size(); i++){
            scores.getData().add(new XYChart.Data(String.valueOf(5-i), last5.get(i)));
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
        setGraph();
        setPerson();
    }
}

package sample;


import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

public class Hiscores extends MainMenu{

    private List<String> unsortedhsName = new ArrayList<>(); //String-objekter eller highscore-objekter?
    private List<Long> unsortedhsScore = new ArrayList<>();
    private List<String> hsName = new ArrayList<>(); //String-objekter eller highscore-objekter?
    private List<Long> hsScore = new ArrayList<>();
    private List<Long> personHiscores = new ArrayList<>(); //Inneholder høyeste highscore for hver session.

    private int count;

    private void sortHighscores(){
        for(int k=0; k < 10; k++){
            int highest = unsortedhsScore.indexOf(Collections.max(unsortedhsScore));

            hsScore.add(unsortedhsScore.get(highest));
            hsName.add(unsortedhsName.get(highest));

            unsortedhsScore.remove(highest);
            unsortedhsName.remove(highest);
        }
    }

    protected void makeFiles() throws IOException {
        File hsFile = new File("storedHighScores.txt");
        File playerFile = new File("storedPlayer.txt");
        if (hsFile.createNewFile()) {
            System.out.println("File created: " + hsFile.getName());
        } else {
            System.out.println("Highscore file already exists.");
        }
        if (playerFile.createNewFile()) {
            System.out.println("File created: " +playerFile.getName());
        } else {
            System.out.println("Player file already exists.");
        }
    }

    public void writeHS(String playername, String highscore) throws IOException {
        readHS();
        if(Long.parseLong(highscore) > getHSScores().get(9)) {
            FileWriter hs = new FileWriter("storedHighScores.txt", true);
            hs.write(playername + ":" + highscore + "\n");
            System.out.println("Wrote to system: " + playername + ", " + highscore);
            hs.close();
        }
        else{
            System.out.println("Score not top 10, better luck next time :)");
        }

    }

    protected void readHS() throws IOException { //Vet ikke hvilken synlighetsmod den skal sa mtp at den blir brukt i main
        try {
            File file = new File("storedHighScores.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isEmpty() == true){
                    break;
                }
                String[] data = line.split(":");
                System.out.println(data[0]+" "+data[1]);
                unsortedhsName.add(data[0]); //Bruker index for å skille mellom spillere, viktig å lagre index da.
                unsortedhsScore.add(Long.parseLong(data[1]));
                System.out.println("Players: "+unsortedhsName);
                System.out.println("Score: "+unsortedhsScore);
            }
            sortHighscores();
            reader.close();
            System.out.println("file closed");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public List<String> getHSNames(){
        System.out.println(hsName);
        return this.hsName;
    }
    public List<Long> getHSScores(){
        return this.hsScore;
    }


    //Trenger en variabel som holder den høyeste scores for hver session
    //Trenger en variabel som holder gamecounten for den sessionen

    public void writePlayerInfo(Long highscore, int gamecount) throws IOException { //Fungerer ikke, må finne ny linje.
        FileWriter pi = new FileWriter("storedPlayer.txt", true);
        pi.write(highscore+":"+gamecount+"\n");
        System.out.println("Wrote to system: "+highscore+":"+gamecount);
        pi.close();
    }

    protected void updatePlayerInfo(Text hiscore, Text gameCount, Text gameSession) throws FileNotFoundException {

        File playerfile = new File("storedPlayer.txt");
        Scanner read = new Scanner(playerfile);
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String[] data = line.split(":");
            this.personHiscores.add(Long.parseLong(data[0])); //Bruker index for å skille mellom spillere, viktig å lagre index da.
            this.count += Integer.parseInt(data[1]);
        }
        read.close();
        personHiscores.add(new gameBoard().getCurrentBest());

        gameSession.setText(String.valueOf(personHiscores.size()));
        gameCount.setText(String.valueOf(this.count + new gameBoard().getSessionCount()));
        hiscore.setText(String.valueOf(Collections.max(personHiscores)));
    }

}

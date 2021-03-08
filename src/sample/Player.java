package sample;

public class Player {
    private String username;
    private long score;
    private long bestScore;
    private int count;

    public Player(String username){
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getBestScore(){
        return this.bestScore;
    }

    public void setBestScore(long score){
        this.bestScore = score;
    }

    public void incrementPlayCount(){
        this.count++;
    }

    public int getCount(){
        return this.count;
    }
}

package sample;

public class Player {
    private String username;
    private long score;
    private long bestScore;
    private int count;

    /**
     *Creates an instance of a player when entering the name
     *
     * @param username The username of the player
     */
    public Player(String username){
        this.username = username;
    }


    /**
     * Getters and setters
     */

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

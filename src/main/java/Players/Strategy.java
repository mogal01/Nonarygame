package Players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Strategy {

    //private ArrayList<Boolean> opponentChoiceHistory;
    //private int opponentScore;

    private int score;

    private ArrayList<Boolean> playerChoiceHistory;
    private Individual playerChoicePath;

    private boolean isOpponentFriend;

    private Player ally;

    private Player opponent;


    public Strategy(){
        //opponentChoiceHistory=new ArrayList<>();
        playerChoiceHistory=new ArrayList<>();
        playerChoicePath=new Individual();
        score=3;
        isOpponentFriend=false;
        ally=null;
    }

   /* public void setOpponentChoiceHistory(ArrayList<Boolean> opponentChoiceHistory) {
        this.opponentChoiceHistory = opponentChoiceHistory;
    }*/

    public ArrayList<Boolean> getPlayerChoiceHistory() {
        return playerChoiceHistory;
    }

    public void addPlayerChoiceHistory(Boolean choice){
        playerChoiceHistory.add(choice);
    }

   /* public int getOpponentScore() {
        return opponentScore;
    }*/

    /*public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }*/

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*public ArrayList<Boolean> getOpponentChoiceHistory() {
        return opponentChoiceHistory;
    }*/

    public Individual getPlayerChoicePath() {
        return playerChoicePath;
    }

    public void setPlayerChoicePath(Individual playerChoicePath) {
        this.playerChoicePath = playerChoicePath;
    }

    public abstract int getCare();

    public abstract int[] getTrust();

    public abstract void setTrust(int[] newTrust);

    public abstract void startingPopulation(String n);

    public abstract void changeTrust(int variation, int indexPlayer);

    //public abstract void updateSequences();

    /*public boolean pickChoice(){

        if(playerChoicePath.size()<=2)
            Goal.refreshSequence(playerChoicePath);
        boolean pick=playerChoicePath.remove();
        playerChoiceHistory.add(pick);
        updateSequences();

        return pick;
    }*/

    public abstract boolean pickChoice();

    public Player getAlly() {
        return ally;
    }

    public void setAlly(Player ally) {
        if(ally!=null)
            ally=new Player(ally.getName(),ally.getMainStrat());
        this.ally = ally;
    }

    public boolean isOpponentFriend() {
        return isOpponentFriend;
    }

    public void setOpponentFriend(boolean opponentFriend) {
        isOpponentFriend = opponentFriend;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}

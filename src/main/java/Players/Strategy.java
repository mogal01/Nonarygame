package Players;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Strategy {

    private ArrayList<Boolean> opponentChoiceHistory;
    private int opponentScore;

    private int score;

    private ArrayList<Boolean> playerChoiceHistory;
    private Queue<Boolean> playerChoicePath;


    public Strategy(){
        opponentChoiceHistory=new ArrayList<>();
        playerChoiceHistory=new ArrayList<>();
        playerChoicePath=new LinkedList<>();
        score=3;
    }

    public void setOpponentChoiceHistory(ArrayList<Boolean> opponentChoiceHistory) {
        this.opponentChoiceHistory = opponentChoiceHistory;
    }

    public ArrayList<Boolean> getPlayerChoiceHistory() {
        return playerChoiceHistory;
    }

    public void addOpponentChoice(Boolean opponentChoice){
        opponentChoiceHistory.add(opponentChoice);
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Boolean> getOpponentChoiceHistory() {
        return opponentChoiceHistory;
    }

    public Queue<Boolean> getPlayerChoicePath() {
        return playerChoicePath;
    }

    public void setPlayerChoicePath(Queue<Boolean> playerChoicePath) {
        this.playerChoicePath = playerChoicePath;
    }

    public abstract int getCare();

    public abstract void setCare(int c);

    public abstract Queue<Boolean> createInitialSequence();

    public abstract void updateSequences();

    public boolean pickChoice(){

        if(playerChoicePath.size()<=2)
            Goal.refreshSequence(playerChoicePath);
        boolean pick=playerChoicePath.remove();
        playerChoiceHistory.add(pick);
        updateSequences();

        return pick;
    }
}

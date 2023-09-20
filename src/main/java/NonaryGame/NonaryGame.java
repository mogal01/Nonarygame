package NonaryGame;

import Players.Goal;
import Players.Player;

import java.util.ArrayList;

public class NonaryGame {
    private static NonaryGame instance = null;

    private ArrayList<Player> activePlayers;

    private ArrayList<Player> allPlayers;

    private Round currentRound;

    private ArrayList<Player> winningPlayers;




    private NonaryGame() {
        winningPlayers=new ArrayList<>();
    }

    public static NonaryGame getInstance() {
        if (instance == null) {
            instance = new NonaryGame();
        }

        return instance;
    }


    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public ArrayList<Player> getWinningPlayers() {
        return winningPlayers;
    }
    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void setActivePlayers(ArrayList<Player> activePlayers) {
        this.activePlayers = activePlayers;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public void removePlayer(Player p){
        activePlayers.remove(p);
    }

    public void setAllSequenceSize(){
        int count=0;
        for(int i=0;i< activePlayers.size();i++){
            if(activePlayers.get(i).getScore()>2)
                count++;
        }
        Goal.setSequenceSize(count-1);

    }





    public String playRound() {

        String picks=currentRound.play();

        if(!currentRound.checkScoreP1()) {
            removePlayer(currentRound.getP1());
            currentRound.getP1().setActive(false);
        }
        if(!currentRound.checkScoreP2()) {
            removePlayer(currentRound.getP2());
            currentRound.getP2().setActive(false);
        }

        //Verifica se i giocatori P1 e P2 hanno raggiunto il valore 9 e inserirli nella lista unica winningPlayers

        if((currentRound.getP1().getScore()>=9)&&(!winningPlayers.contains(currentRound.getP1()))) {
            winningPlayers.add(currentRound.getP1());
            currentRound.getP1().endGame();
        }

        if((currentRound.getP1().getScore()<9)&&(winningPlayers.contains(currentRound.getP1()))){
            winningPlayers.remove(currentRound.getP1());
            currentRound.getP1().endGame();
        }

            if((currentRound.getP2().getScore()>=9)&&(!winningPlayers.contains(currentRound.getP2()))) {
            winningPlayers.add(currentRound.getP2());
            currentRound.getP2().endGame();
        }

         if((currentRound.getP2().getScore()<9)&&(winningPlayers.contains(currentRound.getP2()))){
            winningPlayers.remove(currentRound.getP2());
            currentRound.getP2().endGame();
        }



        return picks;
    }

    public void setWinnings(){
        for (Player winningPlayer : winningPlayers) {
            winningPlayer.endGame();
        }
    }



    public boolean checkEndGame(){

        if(activePlayers.isEmpty())
            return true;

        for (Player winningPlayer : winningPlayers) {
            if (winningPlayer.endGame())
                return true;
        }

        return false;
    }


}

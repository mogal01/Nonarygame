package NonaryGame;

import Players.Goal;
import Players.Player;

import java.util.ArrayList;
import java.util.Collections;

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

    public void shuffle(ArrayList<Player> lista){
        Player l1=lista.get(0);
        Player l2=lista.get(3);
        Player l3=lista.get(6);


        Collections.shuffle(lista);

        while ((lista.indexOf(l1)==0) || (lista.indexOf(l1)==3) || (lista.indexOf(l1)==6)
                || (lista.indexOf(l2)==0) || (lista.indexOf(l2)==3) || (lista.indexOf(l2)==6)
                || (lista.indexOf(l3)==0) || (lista.indexOf(l3)==3) || (lista.indexOf(l3)==6))
            Collections.shuffle(lista);


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
            currentRound.getP1().setActive(false);
        }

        if(!currentRound.checkScoreP2()) {
            currentRound.getP2().setActive(false);
        }

        if(!currentRound.checkScoreP3()) {
            currentRound.getP3().setActive(false);
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

        if((currentRound.getP3().getScore()>=9)&&(!winningPlayers.contains(currentRound.getP3()))) {
            winningPlayers.add(currentRound.getP3());
            currentRound.getP3().endGame();
        }

        if((currentRound.getP3().getScore()<9)&&(winningPlayers.contains(currentRound.getP3()))){
            winningPlayers.remove(currentRound.getP3());
            currentRound.getP3().endGame();
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

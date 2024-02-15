package Players;

import java.util.ArrayList;

public class Individual {
    private boolean nextChoice;
    private ArrayList<Player> trustedPlayers;

    public Individual(boolean sceltaRoundImmediato, ArrayList<Player> giocatoriFidati) {
        this.nextChoice = sceltaRoundImmediato;
        this.trustedPlayers = giocatoriFidati;
    }

    public Individual(boolean sceltaRoundImmediato) {
        this.nextChoice = sceltaRoundImmediato;
        this.trustedPlayers=new ArrayList<>();

    }

    public Individual() {
        this.trustedPlayers=new ArrayList<>();

    }


    public boolean getNextChoice() {
        return nextChoice;
    }

    public void setNextChoice(boolean nextChoice) {
        this.nextChoice = nextChoice;
    }

    public ArrayList<Player> getTrustedPlayers() {
        return trustedPlayers;
    }

    public void setTrustedPlayers(ArrayList<Player> trustedPlayers) {
        this.trustedPlayers = trustedPlayers;
    }

    public void addTrustedPlayer(Player tP){
        trustedPlayers.add(tP);
    }
}

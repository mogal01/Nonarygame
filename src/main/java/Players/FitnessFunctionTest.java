package Players;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Players.FitnessFunction.evaluateChoice;

public class FitnessFunctionTest {

    public static void main(String[] args){
        // Esempio di strategia del giocatore
        Individual playerPath = new Individual();
        playerPath.setNextChoice(false);
        // Storia dell'avversario
        ArrayList<Boolean> opponentHistory = new ArrayList<>();

        int care = 4;
        int[] trust = new int[9];
        trust[0]=38;
        int score = 6;
        int opponentScore = 6;
        Player opponent=new Player("Corvo",new GeneticStrategy(care,"Corvo"));
        opponent.setScore(opponentScore);
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        opponent.getMainStrat().addPlayerChoiceHistory(false); // Betray
        double fitness = evaluateChoice(playerPath, care, trust, score, false,null,opponent);

        System.out.println("Fitness della strategia: " + fitness);
    }
}

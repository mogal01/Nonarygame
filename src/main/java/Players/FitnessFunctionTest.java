package Players;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Players.FitnessFunction.evaluateStrategy;

public class FitnessFunctionTest {

    public static void main(String[] args){
        // Esempio di strategia del giocatore
        Queue<Boolean> playerPath = new LinkedList<>();
        playerPath.offer(false); // Ally
        playerPath.offer(true); // Betray
        playerPath.offer(true); // Ally

        // Storia dell'avversario
        ArrayList<Boolean> opponentHistory = new ArrayList<>();
        opponentHistory.add(true); // Ally
        opponentHistory.add(true); // Betray
        opponentHistory.add(false); // Betray
        opponentHistory.add(true); // Ally

        int care = 2;
        int trust = 7;
        int score = 2;
        int opponentScore = 7;

        double fitness = evaluateStrategy(playerPath, opponentHistory, care, trust, score, opponentScore,false);

        System.out.println("Fitness della strategia: " + fitness);
    }
}

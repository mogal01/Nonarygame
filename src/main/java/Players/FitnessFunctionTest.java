package Players;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Players.FitnessFunction.evaluateStrategy;

public class FitnessFunctionTest {

    public static void main(String[] args){
        // Esempio di strategia del giocatore
        Queue<Boolean> playerPath = new LinkedList<>();
        playerPath.offer(true); // Ally
        playerPath.offer(false); // Betray
        playerPath.offer(false); // Ally

        // Storia dell'avversario
        ArrayList<Boolean> opponentHistory = new ArrayList<>();
        opponentHistory.add(false); // Ally
        opponentHistory.add(false); // Betray
        opponentHistory.add(false); // Betray
        opponentHistory.add(false); // Ally

        int care = 2;
        int trust = 8;
        int score = 2;
        int opponentScore = 7;

        double fitness = evaluateStrategy(playerPath, opponentHistory, care, trust, score, opponentScore);

        System.out.println("Fitness della strategia: " + fitness);
    }
}

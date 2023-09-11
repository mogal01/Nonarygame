package Players;

import java.util.ArrayList;
import java.util.Queue;

public class FitnessFunction {


    public static double evaluateStrategy(Queue<Boolean> playerPath, ArrayList<Boolean> opponentHistory, int care, int trust, int score, int opponentScore) {

       int opponentBetrays=0;
       int opponentAllys=0;
    //Conto il numero di volte che l'avversario ha scelto Betray nei round precedenti

       for(int i=0;i<opponentHistory.size();i++){
           if(!opponentHistory.get(i))
               opponentBetrays++;
           else opponentAllys++;
       }

       double trustScore = (opponentBetrays <= trust) ? 0.8 * ((double) opponentBetrays /(opponentAllys+1)) :  0.3 * ((double) opponentAllys /(opponentBetrays+1)); // confronto con il valore trust

        trustScore=opponentBetrays*trustScore;

        int futureAllys=0;
        int futureBetrays=0;
        Queue<Boolean> playerPathCopy=playerPath;

        do{
            if(playerPathCopy.remove())
                futureAllys++;
            else
                futureBetrays++;
        }while (!playerPathCopy.isEmpty());

        double careAllysScore = (futureAllys>=care) ? 0.8 * ((double) futureBetrays /(futureAllys+1)) : 0.3 * ((double) futureAllys /(futureBetrays+1)); //si contano gli allys nella strategia e si confrontano con il care;

        careAllysScore = futureAllys*careAllysScore;

        double playerBetraysValue= futureBetrays * (1.0 - (double) score/9.0); //prendo in considerazione meglio le strategia con tanti "betray" quanto più il punteggio è basso

        double opponentBetraysValue=(futureBetrays * (1.0 - (double) opponentScore/9.0))/(care+1); //quanto più il punteggio dell'avversario è basso, tanto più si valutano i betray in relazione al care

        double fitness=careAllysScore+playerBetraysValue+opponentBetraysValue+trustScore + (opponentBetrays / (1.0 - (double) score/9.0));

       return fitness;
    }


}
package Players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FitnessFunction {


    public static double evaluateStrategy(Queue<Boolean> playerPath, ArrayList<Boolean> opponentHistory, int care, int trust, int score, int opponentScore, boolean isOpponentFriend, Player Ally) {

       int opponentBetraysCounter=0;
       int opponentAllysCounter=0;

    //Conto il numero di volte che l'avversario ha scelto Betray nei round precedenti
       for(int i=0;i<opponentHistory.size();i++){
           if(!opponentHistory.get(i))
               opponentBetraysCounter++;
           else opponentAllysCounter++;
       }

      // double trustScore = (opponentBetraysCounter <= trust) ? 0.8 * ((double) opponentBetraysCounter /(opponentAllysCounter+1)) :  0.3 * ((double) opponentAllysCounter /(opponentBetraysCounter+1)); // confronto con il valore trust

        double trustScore= (double) 1 /((double) opponentBetraysCounter / trust);

        int futureAllysCounter=0;
        int futureBetraysCounter=0;
        Queue<Boolean> playerPathCopy=new LinkedList<>(playerPath);


        //estrae la successiva scelta, ossia quella del round corrente
        boolean nextPick=playerPathCopy.element();
        double safeValue=1.0;

        //Verifica se la prima futura scelta sarà un betray, che nel caso in cui lo score è 3 o meno significherebbe determinare la sconfitta o il continuo del gioco
        if((score<=2)&&(!nextPick))
            safeValue=2.0;

        if((score<=2)&&(nextPick))
            safeValue=0.5;


        double friendValue=1.0;


        // Modifica la valutazione della scelta "ally" se l'avversario è un amico
        if (isOpponentFriend && nextPick) {
            friendValue = 3.5; // Aumenta il punteggio per aver scelto "ally" con un amico
        }

        if (isOpponentFriend && !nextPick) {
            friendValue = 0.75; // Diminuisce il punteggio per aver scelto "betray" con un amico
        }

        int i=1;
        double futureAllysValue=1.0;
        double futureBetraysValue=1.0;
        do{
            if(playerPathCopy.remove()) {
                futureAllysCounter++;
                futureAllysValue=futureAllysValue+(futureAllysValue*((double) 1 / i));
            } else {
                futureBetraysCounter++;
                futureBetraysValue=futureBetraysValue+(futureBetraysValue*((double) 1 / i));
            }
            i++;
        }while (!playerPathCopy.isEmpty());

        double allyValue=1.0;
        if(Ally!=null){
            if((Ally.getScore()<=2)&&(nextPick))
                allyValue=0.75;
            if((Ally.getScore()<=2)&&(!nextPick))
                allyValue=1.75;
        }

        double careAllysScore;

       //careAllysScore = (futureAllysCounter>=care) ? 0.8 * ((double) futureBetraysCounter /(futureAllysCounter+1)) : 0.3 * ((double) futureAllysCounter /(futureBetraysCounter+1)); //si contano gli allys nella strategia e si confrontano con il care;

        careAllysScore = (futureAllysCounter>=care) ? 1.3 * (futureAllysValue*futureAllysCounter) : 0.7 * (futureAllysValue*futureAllysCounter) ;

        double playerBetraysValue= futureBetraysCounter * futureBetraysValue * (1.0 - (double) score/9.0); //prendo in considerazione meglio le strategia con tanti "betray" quanto più il punteggio è basso

        double opponentBetraysValue=(futureBetraysCounter * (1.0 - (double) opponentScore/9.0))/(care+1); //quanto più il punteggio dell'avversario è basso, tanto più si valutano i betray in relazione al care

        double fitness= careAllysScore + playerBetraysValue + opponentBetraysValue + trustScore + (opponentBetraysCounter / (1.0 - (double) score/9.0)) * safeValue;

        fitness= trustScore * ( (futureAllysCounter*futureAllysValue) + (futureBetraysCounter*futureBetraysValue) + careAllysScore + playerBetraysValue + opponentBetraysValue) * safeValue * friendValue * allyValue;

        return fitness;
    }


}
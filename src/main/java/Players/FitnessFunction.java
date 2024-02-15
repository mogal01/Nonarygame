package Players;

import NonaryGame.TestFile;

import java.util.ArrayList;

public class FitnessFunction {

    public static double[] evaluateStrategy(Individual playerPath, int care, int[] trust, int score, boolean isOpponentFriend, Player Ally, Player opponent){
        double[] fitnessArray= new double[1 + care];
        fitnessArray[0]=evaluateChoice(playerPath,care,trust,score,isOpponentFriend,Ally,opponent);
        double extraValutation;
        if(opponent.getMainStrat().getAlly() != null){
            extraValutation=FitnessFunction.evaluateChoice(playerPath, care, trust, score, isOpponentFriend, Ally, opponent.getMainStrat().getAlly());
            fitnessArray[0]=(fitnessArray[0]+extraValutation)/2;
        }
        for(int i=1;i<care+1;i++){
            fitnessArray[i]=evaluateTrustedPlayer(playerPath.getTrustedPlayers().get(i-1),care,trust,score);
        }
        //System.out.println(fitnessArray[0]);
        for(int j=1;j<fitnessArray.length;j++) {
            //System.out.println(fitnessArray[j]);
        }
        return fitnessArray;
    }

    public static double evaluateChoice(Individual playerPath, int care, int[] trust, int score, boolean isOpponentFriend, Player Ally, Player opponent) {

       int opponentBetraysCounter=0;
       int opponentAllysCounter=0;

       ArrayList<Boolean> opponentHistory=opponent.getMainStrat().getPlayerChoiceHistory();
       int opponentScore=opponent.getMainStrat().getScore();

    //Conto il numero di volte che l'avversario ha scelto Betray nei round precedenti
       for(int i=0;i<opponentHistory.size();i++){
           if(!opponentHistory.get(i))
               opponentBetraysCounter++;
           else opponentAllysCounter++;
       }

        int trueIndex=findTrueIndex(opponent.getName());
        int trustScore= trust[trueIndex];

        int tempTrust=trustScore;
        int countDec=1;
        while(tempTrust>10){
            tempTrust=tempTrust/10;
            countDec++;
        }

        String nomeAvversario;
        if(opponent.getMainStrat().getAlly()!=null)
            nomeAvversario=opponent.getMainStrat().getAlly().getName();
        else
            nomeAvversario="test";

        double trustValue=1.0+(((double) trustScore /Math.pow(10,countDec))*5);

        double choiceHistoryValue= trustValue /((double) (opponentBetraysCounter+1));


        //estrae la successiva scelta, ossia quella del round corrente
        boolean nextPick=playerPath.getNextChoice();
        double safeValue=1.0;

        //Verifica se la prima futura scelta sarà un betray, che nel caso in cui lo score è 3 o meno significherebbe determinare la sconfitta o il continuo del gioco
        if((score<=2)&&(!nextPick))
            safeValue=2.0;

        if((score<=2)&&(nextPick))
            safeValue=0.5;


        double friendValue=1.0;

        double personalTrust=1.0;
        int mediaTrust=0;
        for(int t:trust){
            mediaTrust=mediaTrust+t;
        }
        mediaTrust=mediaTrust/9;
        if((nextPick)&&(trustScore<((double) mediaTrust /2))) {
            personalTrust = 0.35;
        }


        // Modifica la valutazione della scelta "ally" se l'avversario è un amico
        /*if (isOpponentFriend && nextPick) {
            friendValue = 3.5; // Aumenta il punteggio per aver scelto "ally" con un amico
        }

        if (isOpponentFriend && !nextPick) {
            friendValue = 0.75; // Diminuisce il punteggio per aver scelto "betray" con un amico
        }*/

        int i=1;

        double allyValue=1.0;
        /*if(Ally!=null){
            if((Ally.getScore()<=2)&&(nextPick))
                allyValue=0.75*care;
            if((Ally.getScore()<=2)&&(!nextPick))
                allyValue=1.75*care;
        }*/

        double careAllysScore=care;
        int tempCare=care;
        if(tempCare==0)
            tempCare=1;
        if ((playerPath.getTrustedPlayers().contains(opponent)) && nextPick) {
            careAllysScore = careAllysScore*1.5*tempCare; // Aumenta il punteggio per aver scelto "ally" con un amico
        }

        /*if ((playerPath.getTrustedPlayers().contains(opponent)) && !nextPick) {
            careAllysScore = 0.50; // Diminuisce il punteggio per aver scelto "betray" con un amico
        }*/

        /*if(nextPick){
            careAllysScore=careAllysScore*care;
        }*/

        double nextBetrayValue=1.0;
        if(!nextPick)
            nextBetrayValue = (double) (3.5 - (opponentScore / 9)) / (care + 1);

        if(personalTrust<1)
            nextBetrayValue=nextBetrayValue+1.0;
        /*System.out.println("trustValue: " + trustValue);
        System.out.println("careAllysScore: " + careAllysScore);
        System.out.println("nextBetrayValue: " + nextBetrayValue);
        System.out.println("safeValue: " + safeValue);
        System.out.println("friendValue: " + friendValue);
        System.out.println("allyValue: " + allyValue);
        System.out.println("personalTrust: " + personalTrust);
        System.out.println("choiceHistoryValue: " + choiceHistoryValue);*/
        double fitness= trustValue * choiceHistoryValue * careAllysScore  * safeValue * personalTrust * nextBetrayValue;
        //System.out.println("\n\nfitness: " + fitness + " \n\n");

        //if((opponent.getName().equals("Corvo"))|| (nomeAvversario.equals("Corvo"))){
            /*System.out.println("\n\ntrustValue: " + trustValue);
            System.out.println("careAllysScore: " + careAllysScore);
            System.out.println("nextBetrayValue: " + nextBetrayValue);
            System.out.println("safeValue: " + safeValue);
            System.out.println("friendValue: " + friendValue);
            System.out.println("allyValue: " + allyValue);
            System.out.println("personalTrust: " + personalTrust);
            System.out.println("choiceHistoryValue: " + choiceHistoryValue);
            System.out.println("totale: " + fitness);*/


        //else
        //System.out.println("NON Sono contro Corvo e il mio trustValue è: " + trustValue);
        return fitness;
    }

    public static double evaluateAllies(Individual playerPath, int care, int[] trust, Player opponent){
        int totalBetrayCount = 0;
        int totalAllyCount = 0;
        for(int i=0; i<playerPath.getTrustedPlayers().size();i++){
            Player p=playerPath.getTrustedPlayers().get(i);
            if(!p.isActive())
                return 0;
            //Conto il numero di volte che l'avversario ha scelto Betray nei round precedenti
            for(int j=0;j<p.getMainStrat().getPlayerChoiceHistory().size();j++){
                if(!p.getMainStrat().getPlayerChoiceHistory().get(j))
                    totalBetrayCount++;
                else totalAllyCount++;
            }
        }

        int trustValue=trust[TestFile.playerList.indexOf(opponent)];
        double trustScore= (double) 1 /((double) (totalBetrayCount+1) / trustValue);
        return trustScore;

    }

    public static double evaluateTrustedPlayer(Player gene,int care, int[] trust, int score){
        int betraysHistoryCounter=0;
        int allysHistoryCounter=0;
        for(int i=0; i<gene.getMainStrat().getPlayerChoiceHistory().size();i++)
            if(gene.getMainStrat().getPlayerChoiceHistory().get(i))
                allysHistoryCounter++;
            else
                betraysHistoryCounter++;

        int trustScore=trust[TestFile.playerList.indexOf(gene)];
        double allyHistoryValue=1.0+(((double) allysHistoryCounter /10)*2);

        int tempTrust=trustScore;
        int countDec=1;
        while(tempTrust>10){
            tempTrust=tempTrust/10;
            countDec++;
        }
        double trustValue=1.0+(((double) trustScore /Math.pow(10,countDec))*5);
        //double choiceValue=((double) 1 /(betraysHistoryCounter+1))*allyHistoryValue;

        double careValue=1.0+(allyHistoryValue/(care+1));
        //double careScore=((double) betraysHistoryCounter /(care+1))*allyHistoryValue;
        double convenienceScore= (double) (gene.getScore() * score) /100;
        if(convenienceScore<0)
            convenienceScore=0;

        /*System.out.println("trustValue: " + trustValue);
        //System.out.println("choiceValue: " + choiceValue);
        System.out.println("convenienceScore: " + convenienceScore);
        System.out.println("careValue: " + careValue);*/
        double fitnessValue=trustValue*convenienceScore*careValue;
        //System.out.println("\n\nfitnessValue: " + fitnessValue + " \n\n\n");
        if(fitnessValue==0.0){
            //System.out.println("allysHistoryCounter: " + allysHistoryCounter + "\n\n\n");
        }
        return fitnessValue;


    }

    public static int findTrueIndex(String name){
        for(int i=0;i<TestFile.playerList.size();i++){
            if(TestFile.playerList.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

}
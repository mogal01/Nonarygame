package Players;

import java.util.ArrayList;

public class evolvedStrategy extends Strategy {
    private int care;
    private String OwnName;
    private int[] trust;
    private double pBetray; // Probabilità iniziale che l'avversario scelga Betray
    private double beta = 3.839796567069294E-4; // Incremento di P_betray per ogni scelta di Betray
    private double alpha = 0.7; // Fattore di sconto alfa
    private int rounds = 100; // Numero di turni considerati per il calcolo

    private double betaGrowthRate = 1.2; // Fattore di crescita di beta per ogni scelta di Betray


    //private Node minimaxTree;

    public evolvedStrategy(int c, String n) {
        OwnName = n;
        int[] trust = new int[9];
        for (int i = 0; i < 9; i++)
            trust[i] = 50;
        this.trust = trust;
        this.care = c;
        pBetray=LastMoveManager.readPbetrayFromFile();
        //alpha=LastMoveManager.readAlphaFromAFile();
        LastMoveManager.savePbetrayToFile(pBetray);
    }



    @Override
    public int getCare() {
        return care;
    }

    @Override
    public int[] getTrust() {
        return trust;
    }

    public void changeTrust(int variation, int indexPlayer) {
        if (trust[indexPlayer] + variation > 0)
            trust[indexPlayer] = trust[indexPlayer] + variation;
    }

    public void setTrust(int[] trust) {
        this.trust = trust;
    }


    @Override
    public void startingPopulation(String n) {

    }


    @Override
    public boolean pickChoice() {
        boolean choice=choose();
        addPlayerChoiceHistory(choice);
        LastMoveManager.saveLastMove(OwnName,getOpponent().getName(),choice);
        if(getOpponent().getMainStrat().getAlly()!=null)
            LastMoveManager.saveLastMove(OwnName,getOpponent().getMainStrat().getAlly().getName(),choice);
        return choice;
    }


    // Funzione Minimax
    public boolean choose() {
        double choiceValueAlly = 1.0;
        double choiceValueBetray = 1.0;
        boolean prediction = makePrediction();
        int bestScore = Integer.MAX_VALUE;
        if (!prediction) {
            pBetray = pBetray + beta;
            beta=beta*betaGrowthRate;
            LastMoveManager.savePbetrayToFile(pBetray);
            //System.out.println("Ho previsto betray");
            return false;
        } else {
            double val1=calculateExpectedGainForAlly();
            double val2=calculateExpectedGainForBetray();
            boolean ris=val1>val2;
            System.out.println("Il mio calcolo ha dato come risultato: " + ris);
            //System.out.println("Il valore di Ally è: " + val1);
            //System.out.println("Il valore di Betray è: " + val2);
            //System.out.println("la probabilità di betray è di: " + pBetray);
            if(!ris) {
                pBetray = pBetray + beta;
                beta=beta*betaGrowthRate;
                LastMoveManager.savePbetrayToFile(pBetray);
            }
            return ris;
        }
    }

    // Calcola il guadagno atteso scegliendo Ally
    public double calculateExpectedGainForAlly() {
        double gain = 2;
        double pAlly=1-pBetray;
        for (int i = 1; i < rounds; i++) {
            double currentGain = 2*pAlly;
            //System.out.println("current gain vale: " + currentGain);
            //System.out.println("11gain vale:" + gain);
            gain +=  currentGain*Math.pow(alpha, i);
            //System.out.println("quindi gain: " + gain);
        }
        return gain;
    }


    // Calcola il guadagno atteso scegliendo Betray
    public double calculateExpectedGainForBetray() {
        double gain = 3; // Guadagno immediato da Betray nel primo turno
        double currentPBetray = pBetray;
        double tempBeta=beta;
        for (int i = 1; i < rounds; i++) {
            currentPBetray += tempBeta;// Aumenta la probabilità che l'avversario scelga Betray
            tempBeta=tempBeta*betaGrowthRate;
            if (currentPBetray > 1) currentPBetray = 1; // Assicura che P_betray non superi 1
            //double currentGain = 3 * (1 - currentPBetray);
            double currentGain = 3*(1-pBetray);
            gain += Math.pow(alpha, i) * currentGain;
        }
        return gain;
    }

    // Calcola il guadagno atteso scegliendo Ally
    /*public double calculateExpectedGainForAlly() {
        double gain = 2;
        double pAlly=1-pBetray;
        for (int i = 0; i < rounds; i++) {
            double currentGain = 2*pAlly - 2*pBetray + 3*pAlly;
            //System.out.println("current gain vale: " + currentGain);
            gain += Math.pow(alpha, i) * currentGain;
            //System.out.println("quindi gain: " + gain);
        }
        return gain;
    }*/


    // Calcola il guadagno atteso scegliendo Betray
    /*public double calculateExpectedGainForBetray() {
        double gain = 3; // Guadagno immediato da Betray nel primo turno
        double currentPBetray = pBetray;
        double tempBeta=beta;
        for (int i = 1; i < rounds; i++) {
            currentPBetray += tempBeta;// Aumenta la probabilità che l'avversario scelga Betray
            tempBeta=tempBeta*betaGrowthRate;
            if (currentPBetray > 1) currentPBetray = 1; // Assicura che P_betray non superi 1
            double currentGain = 2*(1-currentPBetray) + 3 * (1 - currentPBetray) - 2 * (currentPBetray);
            gain += Math.pow(alpha, i) * currentGain;
        }
        return gain;
    }*/

    /*private void buildTree(){
        Node radice=new Node("A",0);
        Node opponentAllyChoice=new Node("OpponentAlly",0);
        Node opponentBetrayChoice=new Node("OpponentBetray",0);
        Node allyChoiceGivenAlly=new Node("AllyChoiceGivenAlly",2);
        Node betrayChoiceGivenAlly=new Node("BetrayChoiceGivenAlly",3);
        Node allyChoiceGivenBetray=new Node("AllyChoiceGivenBetray",-3);
        Node betrayChoiceGivenBetray=new Node("BetrayChoiceGivenBetray",0);
        opponentAllyChoice.addAdjacentNode(allyChoiceGivenAlly);
        opponentAllyChoice.addAdjacentNode(betrayChoiceGivenAlly);
        opponentBetrayChoice.addAdjacentNode(allyChoiceGivenBetray);
        opponentBetrayChoice.addAdjacentNode(betrayChoiceGivenBetray);
        radice.addAdjacentNode(opponentAllyChoice);
        radice.addAdjacentNode(opponentBetrayChoice);


        this.minimaxTree=radice;
    }

    private void buildTree2(){
        Node radice=new Node("A",0);
        Node AllyChoice=new Node("AllyChoice",0);
        Node BetrayChoice=new Node("BetrayChoice",0);
        Node opponentAllyChoiceGivenAlly=new Node("opponentAllyChoiceGivenAlly",2);
        Node opponentBetrayChoiceGivenAlly=new Node("opponentBetrayChoiceGivenAlly",-2);
        Node opponentAllyChoiceGivenBetray=new Node("opponentAllyChoiceGivenBetray",+3);
        Node opponentBetrayChoiceGivenBetray=new Node("opponentBetrayChoiceGivenBetray",0);
        BetrayChoice.addAdjacentNode(opponentAllyChoiceGivenBetray);
        BetrayChoice.addAdjacentNode(opponentBetrayChoiceGivenBetray);
        AllyChoice.addAdjacentNode(opponentAllyChoiceGivenAlly);
        AllyChoice.addAdjacentNode(opponentBetrayChoiceGivenAlly);
        radice.addAdjacentNode(AllyChoice);
        radice.addAdjacentNode(BetrayChoice);


        this.minimaxTree=radice;
    }*/

    private boolean makePrediction() {
        int opponentBetraysCounter = 0;
        int opponentAllysCounter = 0;

        ArrayList<Boolean> opponentHistory = getOpponent().getMainStrat().getPlayerChoiceHistory();
        int opponentScore = getOpponent().getScore();

        //Conto il numero di volte che l'avversario ha scelto Betray nei round precedenti
        for (int i = 0; i < opponentHistory.size(); i++) {
            if (!opponentHistory.get(i))
                opponentBetraysCounter++;
            else opponentAllysCounter++;
        }


        int trueIndex = FitnessFunction.findTrueIndex(getOpponent().getName());
        int trustScore = trust[trueIndex];

        int tempTrust = trustScore;
        int countDec = 1;
        while (tempTrust > 10) {
            tempTrust = tempTrust / 10;
            countDec++;
        }

        double trustValue = 1.0 + (((double) trustScore / Math.pow(10, countDec)) * 5);

        double choiceHistoryValue = trustValue / ((double) (opponentBetraysCounter + 1));

        double safeValue = 0;
        //Verifica se la prima futura scelta sarà un betray, che nel caso in cui lo score è 3 o meno significherebbe determinare la sconfitta o il continuo del gioco
        if ((opponentScore <= 2))
            safeValue = -5;

        double result = trustValue + opponentAllysCounter + choiceHistoryValue + safeValue - opponentBetraysCounter;

        return result > 5;
    }

    public double getpBetray() {
        return pBetray;
    }

    public void setpBetray(double pBetray) {
        this.pBetray = pBetray;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}

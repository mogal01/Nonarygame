package Players;



import java.util.ArrayList;

public class SoloStrategy extends Strategy{
    private int care;
    private String OwnName;
    private int[] trust;
    private boolean lastOpponentMove;

    public int activeStrategy;


    public SoloStrategy(int c, String n, int s) {
        // All'inizio, assumiamo che l'opponente giocherà "ally"
        this.lastOpponentMove = true;
        OwnName=n;
        int[] trust = new int[9];
        for(int i=0;i<9;i++)
            trust[i]=50;
        this.trust=trust;
        this.care=c;
        activeStrategy=s;
    }


    // Metodo per aggiornare l'ultima mossa dell'opponente
    public void updateLastOpponentMove(boolean opponentMove) {
        lastOpponentMove=opponentMove;
    }

    @Override
    public int getCare() {
        return care;
    }

    @Override
    public int[] getTrust() {
        return trust;
    }

    public void changeTrust(int variation, int indexPlayer){
        if(trust[indexPlayer]+variation>0)
            trust[indexPlayer]=trust[indexPlayer]+variation;
    }

    public void setTrust(int[] trust) {
        this.trust = trust;
    }


    @Override
    public void startingPopulation(String n) {

    }


    @Override
    public boolean pickChoice() {
        if(activeStrategy==1)
            return TitForTat();
        if(activeStrategy==2)
         return alwaysAlly();
        if(activeStrategy==3)
            return alwaysBetray();
        return true;
    }

    public boolean TitForTat(){
        // Tit for Tat: Gioca la mossa dell'avversario nell'ultimo round
        updateLastOpponentMove(LastMoveManager.getLastMove(getOpponent().getName(), OwnName));
        addPlayerChoiceHistory(lastOpponentMove);

        if((getOpponent().getName().equals("Corvo"))||(getOpponent().getName().equals("Falco"))) {
            LastMoveManager.saveLastMove(OwnName, getOpponent().getName(), lastOpponentMove);
        }
        String nomeAvversario;
        if(getOpponent().getMainStrat().getAlly()!=null)
            nomeAvversario=getOpponent().getMainStrat().getAlly().getName();
        else
            nomeAvversario="test";

        if((nomeAvversario.equals("Corvo"))||(nomeAvversario.equals("Falco"))) {
            LastMoveManager.saveLastMove(OwnName, nomeAvversario, lastOpponentMove);
        }

        return lastOpponentMove;
    }

    public boolean alwaysBetray(){
        updateLastOpponentMove(LastMoveManager.getLastMove(getOpponent().getName(), OwnName));
        addPlayerChoiceHistory(lastOpponentMove);
        LastMoveManager.saveLastMove(OwnName,getOpponent().getName(),false);
        if(getOpponent().getMainStrat().getAlly()!=null)
            LastMoveManager.saveLastMove(OwnName,getOpponent().getMainStrat().getAlly().getName(),false);
        return false;
    }

    public boolean alwaysAlly(){
        updateLastOpponentMove(LastMoveManager.getLastMove(getOpponent().getName(), OwnName));
        addPlayerChoiceHistory(lastOpponentMove);
        LastMoveManager.saveLastMove(OwnName,getOpponent().getName(),true);
        if(getOpponent().getMainStrat().getAlly()!=null)
            LastMoveManager.saveLastMove(OwnName,getOpponent().getMainStrat().getAlly().getName(),true);
        return true;
    }







}

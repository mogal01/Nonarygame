package Players;

import java.util.ArrayList;

public class Player {

    private String name;
    private Strategy mainStrat;
    private boolean active;
    private boolean winningFlag;

    private ArrayList<Player> allies;

    //Anche un parametro di fiducia?

    public Player(String n, Strategy m){
        name=n;
        mainStrat=m;
        winningFlag=false;
        active=true;
        allies=new ArrayList<>();
    }


    public Player(String n){
        name=n;
        winningFlag=false;
        active=true;
        allies=new ArrayList<>();
        mainStrat=new GeneticStrategy(n);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean endGame(){
        /*NonaryGame game = NonaryGame.getInstance();
        int useCare= mainStrat.getCare();
        if (mainStrat.getCare()>game.getActivePlayers().size())
            useCare=game.getActivePlayers().size();

        if ( (mainStrat.getScore() >= 9) && (game.getWinningPlayers().size()-1 >= useCare)) {
            winningFlag=true;
            return true; }
        else {
            winningFlag=false;
            return false;
        }*/

        boolean alliesWin=true;
        for(int i=0;i<mainStrat.getPlayerChoicePath().getTrustedPlayers().size();i++){
            if(mainStrat.getPlayerChoicePath().getTrustedPlayers().get(i).getScore()<9) {

                alliesWin = false;
            }
        }
        if((mainStrat.getScore() >= 9)&&(alliesWin)) {

            winningFlag=true;

             return true; }
        else {

        winningFlag=false;
        return false;
        }

    }

    public boolean pick(){
        if((mainStrat.getScore()==0)||(mainStrat.getScore()==-1)||(mainStrat.getScore()==-2)) {
            mainStrat.addPlayerChoiceHistory(true);
            if(name.equals("Corvo"))
                System.out.println("Sono Corvo e ho perso");
            return true;
        }
        else
        return mainStrat.pickChoice();
    }

    public String getName() {
        return name;
    }

    public Strategy getMainStrat() {
        return mainStrat;
    }

    public int getScore() {
        return mainStrat.getScore();
    }

    public void setScore(int score) {
        if(isActive())
        mainStrat.setScore(score);
    }

    public boolean isWinningFlag() {
        return winningFlag;
    }

    public ArrayList<Player> getAllies() {
        return allies;
    }

    public void setAllies(ArrayList<Player> allies) {
        this.allies = allies;
    }


}

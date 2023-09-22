package Players;

import NonaryGame.NonaryGame;

public class Player {

    private String name;
    private Strategy mainStrat;
    private boolean active;
    private boolean winningFlag;

    //Anche un parametro di fiducia?

    public Player(String n, Strategy m){
        name=n;
        mainStrat=m;
        winningFlag=false;
        active=true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean endGame(){
        NonaryGame game = NonaryGame.getInstance();
        int useCare= mainStrat.getCare();
        if (mainStrat.getCare()>game.getActivePlayers().size())
            useCare=game.getActivePlayers().size();

        if ( (mainStrat.getScore() >= 9) && (game.getWinningPlayers().size()-1 >= useCare)) {
            winningFlag=true;
            return true; }
        else {
            winningFlag=false;
            return false;
        }

    }

    public boolean pick(){
        if((mainStrat.getScore()==0)||(mainStrat.getScore()==-1)||(mainStrat.getScore()==-2))
            return true;
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


}

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
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean endGame(){
        NonaryGame game = NonaryGame.getInstance();

        if ( (mainStrat.getScore()>=9) && (game.getWinningPlayers().size()-2>=mainStrat.getCare()) ){
            winningFlag=true;
            return true; }
        else
            return false;

    }

    public boolean pick(){
        if(mainStrat.getScore()<=0)
            return true;
        else
        return mainStrat.pickChoice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Strategy getMainStrat() {
        return mainStrat;
    }

    public void setMainStrat(Strategy mainStrat) {
        this.mainStrat = mainStrat;
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

package NonaryGame;

import Players.Player;

public class Round {

    private Player p1;
    private Player p2;

    public Round(Player p1, Player p2){
        this.p1=p1;
        this.p2=p2;
    }

    public String play(){
        p2.getMainStrat().setOpponentChoiceHistory(p1.getMainStrat().getPlayerChoiceHistory());
        p1.getMainStrat().setOpponentChoiceHistory(p2.getMainStrat().getPlayerChoiceHistory());

        p1.getMainStrat().setOpponentScore(p2.getScore());
        p2.getMainStrat().setOpponentScore(p1.getScore());

       boolean s1= p1.pick();
       boolean s2= p2.pick();

       String c1=s1 ? "Ally" : "Betray";
       String c2=s2 ? "Ally" : "Betray";

       String picks="First player choice: " + c1 + "\nSecond player choice: " + c2;
       if(s1&&s2) {
           p1.setScore(p1.getScore() + 2);
           p2.setScore(p2.getScore() + 2);
       }

           if(s1 && !s2){
               p1.setScore(p1.getScore() - 2);
               p2.setScore(p2.getScore() + 3);
           }

        if(!s1 && s2){
            p1.setScore(p1.getScore() + 3 );
            p2.setScore(p2.getScore() - 2 );
        }


        return picks;
       }

    public boolean checkScoreP1(){
        return p1.getScore() > 0;
    }

    public boolean checkScoreP2(){
        return p2.getScore() > 0;
    }

    public boolean checkWinP1(){
        return p1.endGame();
    }

    public boolean checkWinP2(){
        return p2.endGame();
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }
}

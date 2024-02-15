package NonaryGame;

import Players.Player;
import Players.Team;

public class Round {

    private Player p1;
    private Player p2;
    private Player p3;
    private Team pair;

    public Round(Player p1, Player p2){
        this.p1=p1;
        this.p2=p2;
    }

    public Round(Player p1, Player p2, Player p3){
        this.p1=p1;
        this.p2=p2;
        this.p3=p3;
        pair=new Team(p1,p2);
        p1.getMainStrat().setAlly(p2);
        p2.getMainStrat().setAlly(p1);

    }

    public String play(){
        /*p2.getMainStrat().setOpponentChoiceHistory(p3.getMainStrat().getPlayerChoiceHistory());
        p1.getMainStrat().setOpponentChoiceHistory(p3.getMainStrat().getPlayerChoiceHistory());
        p3.getMainStrat().setOpponentChoiceHistory(p1.getMainStrat().getPlayerChoiceHistory());

        p1.getMainStrat().setOpponentScore(p3.getScore());
        p2.getMainStrat().setOpponentScore(p3.getScore());
        p3.getMainStrat().setOpponentScore(p1.getScore());*/

        p1.getMainStrat().setOpponent(p3);
        p2.getMainStrat().setOpponent(p3);
        p3.getMainStrat().setOpponent(p1);

       boolean s1= pair.pick();
       boolean s2= p3.pick();

       String c1=s1 ? "Ally" : "Betray";
       String c2=s2 ? "Ally" : "Betray";

       String picks="First player choice: " + c1 + "\nSecond player choice: " + c2;
       if(s1&&s2) {
           p1.setScore(p1.getScore() + 2);
           p2.setScore(p2.getScore() + 2);
           p3.setScore(p3.getScore() + 2);
           p1.getMainStrat().changeTrust(+2,TestFile.playerList.indexOf(p3));
           p2.getMainStrat().changeTrust(+2,TestFile.playerList.indexOf(p3));
           p3.getMainStrat().changeTrust(+2,TestFile.playerList.indexOf(p1));
       }

           if(s1 && !s2){
               p1.setScore(p1.getScore() - 2);
               p2.setScore(p2.getScore() - 2);
               p3.setScore(p3.getScore() + 3);
               p1.getMainStrat().changeTrust(-2,TestFile.playerList.indexOf(p3));
               p2.getMainStrat().changeTrust(-2,TestFile.playerList.indexOf(p3));
           }

        if(!s1 && s2){
            p1.setScore(p1.getScore() + 3 );
            p2.setScore(p2.getScore() + 3 );
            p3.setScore(p3.getScore() - 2 );
            p3.getMainStrat().changeTrust(-2,TestFile.playerList.indexOf(p1));
        }

        if(!s1 && !s2){
            p1.getMainStrat().changeTrust(-1,TestFile.playerList.indexOf(p3));
            p2.getMainStrat().changeTrust(-1,TestFile.playerList.indexOf(p3));
            p3.getMainStrat().changeTrust(-1,TestFile.playerList.indexOf(p1));
        }


        return picks;
       }

    public boolean checkScoreP1(){
        return p1.getScore() > 0;
    }

    public boolean checkScoreP2(){
        return p2.getScore() > 0;
    }

    public boolean checkScoreP3(){
        return p3.getScore() > 0;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getP3() {
        return p3;
    }

    public Team getPair() {
        return pair;
    }

    public void setPair(Team pair) {
        this.pair = pair;
    }
}

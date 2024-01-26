package Players;

public class Team {

    private Player leader;

    private Player subordinate;

    public Team(Player l, Player s) {
        leader=l;
        subordinate=s;
    }

    public boolean pick(){
        boolean LChoice=leader.pick();
        subordinate.getMainStrat().addPlayerChoiceHistory(LChoice);
        return LChoice;
    }


}

package Players;

public class Team {

    private Player leader;

    private Player subordinate;

    public Team(Player l, Player s) {
        leader=l;
        subordinate=s;
    }

    public boolean pick(){
        return leader.pick();
    }


}

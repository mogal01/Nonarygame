package NonaryGame;

import Players.Goal;
import Players.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestTeam {

    public static void main(String[] args) throws IOException {
        test();
    }


    public static ArrayList<Player> test() throws IOException{
        ArrayList<String> nomi = new ArrayList<String>(){{
            add("Corvo");
            add("Falco");
            add("Aquila");
            add("Gheppio");
            add("Condor");
            add("Gufo");
            add("Sparviere");
            add("Civetta");
            add("Colomba");}
        };
        String logFilePath = "resocontoTeam.log";
        FileWriter fileWriter = new FileWriter(logFilePath, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.newLine();


        NonaryGame game = NonaryGame.getInstance();
        Random random=new Random();
        Player p;
        Goal g;
        int i;
        ArrayList<Player> lista=new ArrayList<>();
        ArrayList<String> appoggio=nomi;

        for(i=0;i<9;i++){
            g=new Goal(random.nextInt(9));
            String nomeGenerato=appoggio.get(random.nextInt(appoggio.size()));
            p=new Player(nomeGenerato,g);
            lista.add(p);
            appoggio.remove(nomeGenerato);
        }

        game.setActivePlayers(lista);
        game.setAllPlayers(new ArrayList<>(lista));

        ArrayList<ArrayList<Player>> alliances=new ArrayList<>();
        ArrayList<Player> appoggio2=new ArrayList<>(lista);

            for(i=0;i<=appoggio2.size()-1;i++){
                if(appoggio2.size()>1) {
                    int GenerateNumber = random.nextInt(100);
                    if (GenerateNumber < 31) {
                        Player firstGroupMember = appoggio2.get(i);
                        appoggio2.remove(i);
                        int nextIndex = random.nextInt(appoggio2.size() - 1);
                        Player secondGroupMember = appoggio2.get(nextIndex);
                        ArrayList<Player> alliance = new ArrayList<>();
                        alliance.add(firstGroupMember);
                        alliance.add(secondGroupMember);
                        appoggio2.remove(nextIndex);
                        if (GenerateNumber < 21) {
                            nextIndex = random.nextInt(appoggio2.size() - 1);
                            Player thirdGroupMember = appoggio2.get(nextIndex);
                            alliance.add(thirdGroupMember);
                            appoggio2.remove(nextIndex);
                            thirdGroupMember.setAllies(alliance);
                        }
                        firstGroupMember.setAllies(alliance);
                        secondGroupMember.setAllies(alliance);
                        alliances.add(alliance);

                    }
                }

            }

        bufferedWriter.write("ALLEANZA FORMATE: \n");
        for(i=0;i<=alliances.size()-1;i++){
            bufferedWriter.write("\n Alleanza numero: "+ i + " Membri: \n");
            ArrayList<Player> alliance=alliances.get(i);
            for(int j=0; j<=alliance.size()-1; j++){
                bufferedWriter.write("\n " + alliance.get(j).getName() + " \n");
            }
        }

        bufferedWriter.write("\n ELENCO PARTECIPANTI E VALORI INIZIALI:\n");

        for(i=0;i<=lista.size()-1;i++){
            p=lista.get(i);
            bufferedWriter.write("\n"+p.getName() + "\n");
            bufferedWriter.write("Care: " + p.getMainStrat().getCare() + "\n");
            bufferedWriter.write("Punteggio: " + p.getScore()+ "\n\n");
            bufferedWriter.write("_____________________________________________________________________________");
        }


        int countRound=1;

        do {
            bufferedWriter.write("_____________________________________________________________________________");
            bufferedWriter.write("\n      INIZIO ROUND NUMERO: " + countRound + "\n");
            game.shuffle(lista);
            int countGruppo=1;
            for(i=0;i<=lista.size()-3;i=i+3) {

                Player p1=lista.get(i);
                Player p2=lista.get(i+1);
                Player p3=lista.get(i+2);

                if(p1.getAllies().contains(p3)) {
                    System.out.println("Ho messo gli alleati");
                    p1.getMainStrat().setOpponentFriend(true);
                    p3.getMainStrat().setOpponentFriend(true);
                }


                Round round = new Round(p1,p2,p3);

                bufferedWriter.write("_____________________________________________________________________________");
                bufferedWriter.write("\n Round " + countRound + " Gruppo: " + countGruppo + "\n");

                game.setCurrentRound(round);
                String picks = game.playRound();


                bufferedWriter.write("Fine round, dati: ");

                bufferedWriter.write("\nScelte: \n" + picks);

                bufferedWriter.write("\n\nPunteggio " + p1.getName() + " **LEADER** : " + p1.getScore() + "\n");
                bufferedWriter.write("\nPunteggio " + p2.getName() + " : " + p2.getScore() + "\n");
                bufferedWriter.write("\nPunteggio " + p3.getName() + " : " + p3.getScore() + "\n");
                countGruppo++;

                game.setAllSequenceSize();
                p1.getMainStrat().setOpponentFriend(false);
                p2.getMainStrat().setOpponentFriend(false);
                p3.getMainStrat().setOpponentFriend(false);
                p1.getMainStrat().setAlly(null);
                p2.getMainStrat().setAlly(null);
            }
            countRound++;
            game.setWinnings();
        } while(!game.checkEndGame());





        bufferedWriter.write("\n___________________________________________________________________________________________\n");
        bufferedWriter.write("\n            GIOCO CONCLUSO \n");
        bufferedWriter.write("\n Ha chiuso il gioco : \n");
        for(i=0;i<=lista.size()-1;i++){
            if(lista.get(i).isWinningFlag()){
                bufferedWriter.write(lista.get(i).getName() + "\n" + "Care: " + lista.get(i).getMainStrat().getCare() + " Punteggio: " + lista.get(i).getScore() + "\n");
            }
        }

        bufferedWriter.write(game.getWinningPlayers().size() + " Giocatori in totale hanno raggiunto almeno il punteggio di 9\n");

        bufferedWriter.write("\n LISTA PUNTEGGI FINALI: \n");

        for(i=0;i<=game.getAllPlayers().size()-1;i++){
            p=game.getAllPlayers().get(i);
            bufferedWriter.write("\n"+p.getName() + "\n");
            bufferedWriter.write("Care: " + p.getMainStrat().getCare() + "\n");
            bufferedWriter.write("Punteggio: " + p.getScore()+ "\n\n");
            bufferedWriter.write("_____________________________________________________________________________");
        }


        bufferedWriter.close();
        return lista;
    }

}

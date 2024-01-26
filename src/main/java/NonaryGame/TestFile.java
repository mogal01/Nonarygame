package NonaryGame;

import Players.Goal;
import Players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class TestFile {

    static String corvo;
    static String falco;
    static String aquila;
    static String gheppio;
    static String condor;
    static String gufo;
    static String sparviere;
    static String civetta;
    static String colomba;
    private final String directoryPath = "./logFiles";

    public static void main(String[] args) throws IOException {
        testFile();
    }


    public static ArrayList<Player> testFile() throws IOException{

        // Percorso della directory da esaminare
        String directoryPath = "logFiles";

        File directory = new File(directoryPath);
        int numberOfFiles=0;
        // Assicurati che il percorso specificato sia una directory
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                numberOfFiles = files.length;
                System.out.println("Numero di file nella directory: " + numberOfFiles);
            } else {
                System.out.println("La directory è vuota o si è verificato un errore.");
            }
        } else {
            System.out.println("Il percorso specificato non è una directory.");
        }


        ArrayList<Player> playerList = new ArrayList<Player>(){{
            add(new Player("Corvo",new Goal(0)));
            add(new Player("Falco", new Goal(1)));
            add(new Player("Aquila", new Goal(2)));
            add(new Player("Gheppio", new Goal(3)));
            add(new Player ("Condor", new Goal(4)));
            add(new Player ("Gufo", new Goal (4)));
            add(new Player("Sparviere", new Goal(4)));
            add(new Player ("Civetta", new Goal(4)));
            add(new Player("Colomba", new Goal(4)));}
        };
        String fileName = directoryPath + "/simulation_" + numberOfFiles + ".csv";
        PrintWriter bufferedWriter = new PrintWriter(new FileWriter(fileName, true));

        //bufferedWriter.newLine();
        //bufferedWriter.write("__\n");

        NonaryGame game = NonaryGame.getInstance();
        game.reset();
        Random random=new Random();
        Player p;
        Goal g;
        int i;

        game.setActivePlayers(playerList);
        game.setAllPlayers(new ArrayList<>(playerList));

        ArrayList<ArrayList<Player>> alliances=new ArrayList<>();
        ArrayList<Player> appoggio2=new ArrayList<>(playerList);

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
                    if ((GenerateNumber < 21)&&((appoggio2.size()-1)>=1)) {
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

        /*for(i=0;i<=alliances.size()-1;i++){
            bufferedWriter.write("\n Alleanza numero: "+ i + " Membri: \n");
            ArrayList<Player> alliance=alliances.get(i);
            for(int j=0; j<=alliance.size()-1; j++){
                bufferedWriter.write("\n " + alliance.get(j).getName() + " \n");
            }
        }*/

        /*bufferedWriter.write("\n ELENCO PARTECIPANTI E VALORI INIZIALI:\n");

        for(i=0;i<=playerList.size()-1;i++){
            p=playerList.get(i);
            bufferedWriter.write("\n"+p.getName() + "\n");
            bufferedWriter.write("Care: " + p.getMainStrat().getCare() + "\n");
            bufferedWriter.write("Punteggio: " + p.getScore()+ "\n\n");
            bufferedWriter.write("_____________________________________________________________________________");
        }*/


        int countRound=0;
         corvo="Corvo;"+playerList.get(0).getMainStrat().getCare()+";"+playerList.get(0).getMainStrat().getTrust()+";";
         falco="Falco;"+playerList.get(1).getMainStrat().getCare()+";"+playerList.get(1).getMainStrat().getTrust()+";";
         aquila="Aquila;"+playerList.get(2).getMainStrat().getCare()+";"+playerList.get(2).getMainStrat().getTrust()+";";
         gheppio="Gheppio;"+playerList.get(3).getMainStrat().getCare()+";"+playerList.get(3).getMainStrat().getTrust()+";";
         condor="Condor;"+playerList.get(4).getMainStrat().getCare()+";"+playerList.get(4).getMainStrat().getTrust()+";";
         gufo="Gufo;"+playerList.get(5).getMainStrat().getCare()+";"+playerList.get(5).getMainStrat().getTrust()+";";
         sparviere="Sparviere;"+playerList.get(6).getMainStrat().getCare()+";"+playerList.get(6).getMainStrat().getTrust()+";";
         civetta="Civetta;"+playerList.get(7).getMainStrat().getCare()+";"+playerList.get(7).getMainStrat().getTrust()+";";
         colomba="Colomba;"+playerList.get(8).getMainStrat().getCare()+";"+playerList.get(8).getMainStrat().getTrust()+";";
        do {
            game.shuffle(playerList);
            int countGruppo=1;
            for(i=0;i<=playerList.size()-3;i=i+3) {

                Player p1=playerList.get(i);
                Player p2=playerList.get(i+1);
                Player p3=playerList.get(i+2);

                if(p1.getAllies().contains(p3)) {
                    p1.getMainStrat().setOpponentFriend(true);
                    p3.getMainStrat().setOpponentFriend(true);
                }


                Round round = new Round(p1,p2,p3);

                game.setCurrentRound(round);
                String picks = game.playRound();

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



        fillString(playerList);
        String header="Player;Care;Trust;";
        for(i=1;i<=countRound;i++){
            header=header+"R"+i+";";
        }
        header=header+"Win;" + "FinalScore;";

        bufferedWriter.write(header);
        bufferedWriter.write("\n"+corvo);
        bufferedWriter.write("\n"+falco);
        bufferedWriter.write("\n"+aquila);
        bufferedWriter.write("\n"+gheppio);
        bufferedWriter.write("\n"+condor);
        bufferedWriter.write("\n"+gufo);
        bufferedWriter.write("\n"+sparviere);
        bufferedWriter.write("\n"+civetta);
        bufferedWriter.write("\n"+colomba);
        /*bufferedWriter.write("\n___________________________________________________________________________________________\n");
        bufferedWriter.write("\n            GIOCO CONCLUSO \n");
        bufferedWriter.write("\n Ha chiuso il gioco : \n");
        for(i=0;i<=playerList.size()-1;i++){
            if(playerList.get(i).isWinningFlag()){
                bufferedWriter.write(playerList.get(i).getName() + "\n" + "Care: " + playerList.get(i).getMainStrat().getCare() + " Punteggio: " + playerList.get(i).getScore() + "\n");
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
        }*/


        bufferedWriter.close();
        return playerList;
    }


    public static void fillString(ArrayList<Player> playerList){
        int i;

        for(i=0;i<9;i++){
            Player temp=playerList.get(i);
            if(temp.getName().equals("Corvo")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    corvo=corvo+t+";";
                }
                if(temp.isWinningFlag())
                    corvo=corvo+"Y;";
                else
                    corvo=corvo+"N;";
                corvo=corvo + temp.getScore() +";";
            }
            if(temp.getName().equals("Falco")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    falco=falco+t+";";
                }
                if(temp.isWinningFlag())
                    falco=falco+"Y;";
                else
                    falco=falco+"N;";
                falco=falco+temp.getScore() +";";
            }
            if(temp.getName().equals("Aquila")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    aquila=aquila+t+";";
                }
                if(temp.isWinningFlag())
                    aquila=aquila+"Y;";
                else
                    aquila=aquila+"N;";
                aquila=aquila+temp.getScore()+";";
            }
            if(temp.getName().equals("Gheppio")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    gheppio=gheppio+t+";";
                }
                if(temp.isWinningFlag())
                    gheppio=gheppio+"Y;";
                else
                    gheppio=gheppio+"N;";
                gheppio=gheppio+temp.getScore()+";";
            }
            if(temp.getName().equals("Condor")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    condor=condor+t+";";
                }
                if(temp.isWinningFlag())
                    condor=condor+"Y;";
                else
                    condor=condor+"N;";
                condor=condor+temp.getScore()+";";
            }
            if(temp.getName().equals("Gufo")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    gufo=gufo+t+";";
                }
                if(temp.isWinningFlag())
                    gufo=gufo+"Y;";
                else
                    gufo=gufo+"N;";
                gufo=gufo+temp.getScore()+";";
            }
            if(temp.getName().equals("Sparviere")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    sparviere=sparviere+t+";";
                }
                if(temp.isWinningFlag())
                    sparviere=sparviere+"Y;";
                else
                    sparviere=sparviere+"N;";
                sparviere=sparviere+temp.getScore()+";";
            }
            if(temp.getName().equals("Civetta")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    civetta=civetta+t+";";
                }
                if(temp.isWinningFlag())
                    civetta=civetta+"Y;";
                else
                    civetta=civetta+"N;";
                civetta=civetta+temp.getScore()+";";
            }
            if(temp.getName().equals("Colomba")){
                for(int j=0;j<temp.getMainStrat().getPlayerChoiceHistory().size();j++){
                    int t=temp.getMainStrat().getPlayerChoiceHistory().get(j) ? 1:0;
                    colomba=colomba+t+";";
                }
                if(temp.isWinningFlag())
                    colomba=colomba+"Y;";
                else
                    colomba=colomba+"N;";
                colomba=colomba+temp.getScore()+";";
            }
        }


    }

}

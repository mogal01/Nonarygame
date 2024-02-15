package NonaryGame;

import Players.GeneticStrategy;
import Players.SoloStrategy;
import Players.Player;
import Players.evolvedStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static ArrayList<Player> playerList=playerList = new ArrayList<Player>();

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
        //double l=((double) 45 /417)+((double) 60 /439)+((double) 119 /408)+((double) 11 /457)+((double) 15 /455)+((double) 23 /455)+((double) 9 /414)+((double) 17 /461);
        //System.out.println(l/8);
        /*double betrysinAlwaysAlly=299/3110;
        System.out.println("Alwyas ally: " +  betrysinAlwaysAlly);
        double betrysinAlwaysBetray=2286/3256;
        System.out.println("Alwyas betray: " +  betrysinAlwaysBetray);
        double P_max = 0.70;
        double P_min = 0.096;
        double n = 1573;
        System.out.println("beta è uguale a : " +  (P_max - P_min) / n);*/

        playerList= new ArrayList<Player>(){{
            add(new Player("Corvo",new SoloStrategy(0,"Corvo",1)));
            add(new Player("Falco", new SoloStrategy(0,"Falco",1)));
            add(new Player("Aquila", new GeneticStrategy(2,"Aquila")));
            add(new Player("Gheppio", new GeneticStrategy(2,"Gheppio")));
            add(new Player ("Condor", new GeneticStrategy(3,"Condor")));
            add(new Player ("Gufo", new GeneticStrategy(3,"Gufo")));
            add(new Player("Sparviere", new GeneticStrategy(4,"Sparviere")));
            add(new Player ("Civetta", new GeneticStrategy(4,"Civetta")));
            add(new Player("Colomba", new GeneticStrategy(4,"Colomba")));}
        };



        for (Player player : playerList) {
            String playerName = player.getName();
            int[] trustValues = new int[9]; // Inizializza un array per i valori di trust

            // Percorso completo del file di trust per il giocatore
            String trustFilePath = "TrustFiles/" + playerName + "Trust.txt";
            File trustFile = new File(trustFilePath);

            try (BufferedReader reader = new BufferedReader(new FileReader(trustFilePath))) {
                String line;
                int index = 0;

                // Leggi i valori di trust dal file
                while ((line = reader.readLine()) != null) {
                    //trustValues.add(index++,Integer.parseInt(line));
                    trustValues[index++]=Integer.parseInt(line);
                }

                // Imposta i valori di trust per il giocatore
                player.getMainStrat().setTrust(trustValues);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //player.getMainStrat().createInitialSequence();
            player.getMainStrat().startingPopulation(player.getName());
        }


        String fileName = directoryPath + "/simulation_" + numberOfFiles + ".csv";
        PrintWriter bufferedWriter = new PrintWriter(new FileWriter(fileName, true));

        //bufferedWriter.newLine();
        //bufferedWriter.write("__\n");

        NonaryGame game = NonaryGame.getInstance();
        game.reset();
        Random random=new Random();
        Player p;
        GeneticStrategy g;
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
         corvo="Corvo;"+playerList.get(0).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(0).getMainStrat().getTrust()) +";";
         falco="Falco;"+playerList.get(1).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(1).getMainStrat().getTrust()) +";";
         aquila="Aquila;"+playerList.get(2).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(2).getMainStrat().getTrust()) +";";
         gheppio="Gheppio;"+playerList.get(3).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(3).getMainStrat().getTrust()) +";";
         condor="Condor;"+playerList.get(4).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(4).getMainStrat().getTrust()) +";";
         gufo="Gufo;"+playerList.get(5).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(5).getMainStrat().getTrust()) +";";
         sparviere="Sparviere;"+playerList.get(6).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(6).getMainStrat().getTrust()) +";";
         civetta="Civetta;"+playerList.get(7).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(7).getMainStrat().getTrust()) +";";
         colomba="Colomba;"+playerList.get(8).getMainStrat().getCare()+";"+ Arrays.toString(playerList.get(8).getMainStrat().getTrust()) +";";

        ArrayList<Player> tmpList=new ArrayList<>(playerList);

        do {

            game.shuffle(tmpList);
            int countGruppo=1;
            for(i=0;i<=tmpList.size()-3;i=i+3) {

                Player p1=tmpList.get(i);
                Player p2=tmpList.get(i+1);
                Player p3=tmpList.get(i+2);

                if(p1.getAllies().contains(p3)) {
                    p1.getMainStrat().setOpponentFriend(true);
                    p3.getMainStrat().setOpponentFriend(true);
                }


                Round round = new Round(p1,p2,p3);

                game.setCurrentRound(round);
                String picks = game.playRound();
                updateStrings(p1);
                updateStrings(p2);
                updateStrings(p3);
                countGruppo++;

                game.setAllSequenceSize();
                p1.getMainStrat().setOpponentFriend(false);
                p2.getMainStrat().setOpponentFriend(false);
                p3.getMainStrat().setOpponentFriend(false);
                p1.getMainStrat().setAlly(null);
                p2.getMainStrat().setAlly(null);
            }
            countRound++;
            /*System.out.println(playerList.get(1).getName());
            System.out.println(playerList.get(1).getScore());*/
            game.setWinnings();
        } while(!game.checkEndGame()&&countRound<20);



        fillString(playerList);
        String header="Player;Care;Trust;";
        for(i=1;i<=countRound;i++){
            header=header+"R"+i+";";
        }
        header=header+"Win;" + "FinalScore;"+"FinalTrustedPlayers";

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


        for (Player player : playerList) {
            String playerName = player.getName();
            int[] trustValues = player.getMainStrat().getTrust(); // Ottieni l'array di trust per il giocatore

            // Percorso completo del file di trust per il giocatore
            String trustFilePath = "trustFiles/" + playerName + "Trust.txt";

            try (PrintWriter writer = new PrintWriter(trustFilePath)) {
                // Scrivi i valori di trust nel file
                for (int j = 0; j < 9; j++) {
                    writer.println(trustValues[j]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        bufferedWriter.close();
        return playerList;
    }


    public static void fillString(ArrayList<Player> playerList){
        int i;

        for(i=0;i<9;i++){
            Player temp=playerList.get(i);
            if(temp.getName().equals("Corvo")){
                if(temp.isWinningFlag())
                    corvo=corvo+"Y;";
                else
                    corvo=corvo+"N;";
                corvo=corvo + temp.getScore() +";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    corvo=corvo + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Falco")){
                if(temp.isWinningFlag())
                    falco=falco+"Y;";
                else
                    falco=falco+"N;";
                falco=falco+temp.getScore() +";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    falco=falco + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Aquila")){
                if(temp.isWinningFlag())
                    aquila=aquila+"Y;";
                else
                    aquila=aquila+"N;";
                aquila=aquila+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    aquila=aquila + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Gheppio")){
                if(temp.isWinningFlag())
                    gheppio=gheppio+"Y;";
                else
                    gheppio=gheppio+"N;";
                gheppio=gheppio+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    gheppio=gheppio + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Condor")){
                if(temp.isWinningFlag())
                    condor=condor+"Y;";
                else
                    condor=condor+"N;";
                condor=condor+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    condor=condor + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Gufo")){
                if(temp.isWinningFlag())
                    gufo=gufo+"Y;";
                else
                    gufo=gufo+"N;";
                gufo=gufo+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    gufo=gufo + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Sparviere")){
                if(temp.isWinningFlag())
                    sparviere=sparviere+"Y;";
                else
                    sparviere=sparviere+"N;";
                sparviere=sparviere+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    sparviere=sparviere + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Civetta")){
                if(temp.isWinningFlag())
                    civetta=civetta+"Y;";
                else
                    civetta=civetta+"N;";
                civetta=civetta+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    civetta=civetta + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
            if(temp.getName().equals("Colomba")){
                if(temp.isWinningFlag())
                    colomba=colomba+"Y;";
                else
                    colomba=colomba+"N;";
                colomba=colomba+temp.getScore()+";";
                for(int k=0;k<temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().size();k++)
                    colomba=colomba + temp.getMainStrat().getPlayerChoicePath().getTrustedPlayers().get(k).getName()+",";
            }
        }

    }

    public static void updateStrings(Player p1){
        switch(p1.getName()){
            case "Corvo": {
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                corvo=corvo+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    corvo=corvo+"&"+s2;
                }
                corvo=corvo+";";
                break;
            }
            case "Falco":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                falco=falco+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    falco=falco+"&"+s2;
                }
                falco=falco+";";
                break;
            }
            case "Aquila":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                aquila=aquila+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    aquila=aquila+"&"+s2;
                }
                aquila=aquila+";";
                break;
            }
            case "Gheppio":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                gheppio=gheppio+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    gheppio=gheppio+"&"+s2;
                }
                gheppio=gheppio+";";
                break;
            }
            case "Condor":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                condor=condor+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    condor=condor+"&"+s2;
                }
                condor=condor+";";
                break;
            }
            case "Gufo":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                gufo=gufo+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    gufo=gufo+"&"+s2;
                }
                gufo=gufo+";";
                break;
            }
            case "Sparviere":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                sparviere=sparviere+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    sparviere=sparviere+"&"+s2;
                }
                sparviere=sparviere+";";
                break;
            }
            case "Civetta":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                civetta=civetta+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    civetta=civetta+"&"+s2;
                }
                civetta=civetta+";";
                break;
            }
            case "Colomba":{
                int t=p1.getMainStrat().getPlayerChoiceHistory().get(p1.getMainStrat().getPlayerChoiceHistory().size()-1) ? 1:0;
                String s1=p1.getMainStrat().getOpponent().getName();
                String s2="";
                colomba=colomba+t+"-"+s1;
                if(p1.getMainStrat().getOpponent().getMainStrat().getAlly()!=null) {
                    s2 = p1.getMainStrat().getOpponent().getMainStrat().getAlly().getName();
                    colomba=colomba+"&"+s2;
                }
                colomba=colomba+";";
                break;
            }
        }
    }

}

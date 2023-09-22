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


        ArrayList<String> nomi = new ArrayList<String>(){{
            add("Angelo");
            add("Federico");
            add("Gianmarco");
            add("Fele");
            add("Peppe");
            add("Leo");
            add("Demetrio");
            add("Stefano");
            add("Laby");}
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

        bufferedWriter.write("ELENCO PARTECIPANTI E VALORI INIZIALI:\n");

        for(i=0;i<=lista.size()-1;i++){
            p=lista.get(i);
            bufferedWriter.write("\n"+p.getName() + "\n");
            bufferedWriter.write("Care: " + p.getMainStrat().getCare() + "\n");
            bufferedWriter.write("Punteggio: " + p.getScore()+ "\n\n");
            bufferedWriter.write("_____________________________________________________________________________");
        }


        int countRound=1;

        do {
            System.out.println("Sono entrato nel do");
            bufferedWriter.write("_____________________________________________________________________________");
            bufferedWriter.write("\n      INIZIO ROUND NUMERO: " + countRound + "\n");
            System.out.println("Sto per fare lo shuffle");
            game.shuffle(lista);
            System.out.println("shuffle finito");
            int countGruppo=1;
            for(i=0;i<=lista.size()-3;i=i+3) {

                Player p1=lista.get(i);
                Player p2=lista.get(i+1);
                Player p3=lista.get(i+2);
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

    }

}

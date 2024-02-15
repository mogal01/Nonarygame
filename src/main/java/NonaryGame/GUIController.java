package NonaryGame;

import Players.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIController extends Application {

    private List<Text> playerTexts = new ArrayList<>();
    ArrayList<Player> risultati=new ArrayList<>();
    ArrayList<VBox> rettangoli=new ArrayList<>();

    Text additionalText = new Text();
    int teamCounter=0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        teamCounter=0;
        String alliances="";

        risultati=TestFile.testFile();

        // Layout principale
        BorderPane borderPane = new BorderPane();


        VBox mainLayout = new VBox(10); // Spazio di 10 pixel tra gli elementi
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3); // 3 colonne
        tilePane=createRectangle(risultati);


        //Creazione testo riferito alle alleanze
        ArrayList<Player> copy=new ArrayList<>(risultati);
        for(int j=0;j<copy.size();j++) {
            if(!copy.get(j).getAllies().isEmpty()){
                teamCounter++;
                alliances=alliances + "Team " + teamCounter +":\n";
                for(int z=0;z<risultati.get(j).getAllies().size();z++) {
                    //System.out.println("96 + " + alliances);
                    alliances = alliances + " " + risultati.get(j).getAllies().get(z).getName();
                   // System.out.println("98 + " + alliances);
                    copy.remove(risultati.get(j).getAllies().get(z));
                }
                alliances=alliances + ":\n";
            }
        }





        // Testo nella parte inferiore della finestra
        additionalText = new Text(alliances);
        additionalText.setFont(Font.font("Verdana", 20));
        additionalText.setFill(Color.DARKSLATEBLUE);
        additionalText.setTextAlignment(TextAlignment.CENTER);


        // Aggiunta di tilePane e additionalText al layout principale
        mainLayout.getChildren().addAll(tilePane, additionalText);

        borderPane.setCenter(mainLayout);

        // Pulsante nell'angolo in basso a destra
        Button btn = new Button("Nuova Simulazione");
        btn.setMinSize(200,50);
        TilePane finalTilePane = tilePane;
        btn.setOnAction(e -> {for(int i=0;i<999;i++) {
            try {
                risultati = TestFile.testFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            buttonPress();
        }
        });




        VBox bottomBox = new VBox(btn);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT); // Allinea il pulsante in basso a destra
        bottomBox.setPadding(new Insets(10)); // Spazio intorno al pulsante
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane, 1500, 750);
        primaryStage.setTitle("Nonary Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public TilePane createRectangle(ArrayList<Player> risultati){

        VBox mainLayout = new VBox(10); // Spazio di 10 pixel tra gli elementi
        TilePane tilePane = new TilePane();
        // Creare 9 rettangoli con i nomi
        for (int i = 0; i < 9; i++) {

            LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.DARKORANGE), new Stop(1, Color.CORAL));

            Rectangle rect = new Rectangle(165, 350);
            rect.setFill(gradient);
            rect.setArcWidth(20);
            rect.setArcHeight(20);

            String testo=i + ": "+ risultati.get(i).getName() +"\nScore: " + risultati.get(i).getScore() + "\nCare: " +
                    risultati.get(i).getMainStrat().getCare();


            Text playerText = new Text(testo);
            playerText.setFont(Font.font("Arial", 18));
            playerText.setFill(Color.BLACK);
            playerText.setTextAlignment(TextAlignment.CENTER);

            DropShadow shadow = new DropShadow();
            shadow.setOffsetY(3.0f);
            shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
            rect.setEffect(shadow);


            VBox vbox = new VBox(); // VBox per impilare la "X" e il nome del giocatore
            vbox.setAlignment(Pos.CENTER); // Centra gli elementi nel VBox

            // Aggiunta della "X" sotto una certa condizione
            if (risultati.get(i).getScore()<=0) { // Condizione casuale, sostituire con la condizione reale
                Text symbol = new Text("X");
                symbol.setFont(Font.font("Verdana", 24));
                symbol.setFill(Color.BLACK);
                symbol.setTextAlignment(TextAlignment.JUSTIFY);
                vbox.getChildren().add(symbol);
            }

            // Aggiunta della stella sotto un'altra condizione
            if (risultati.get(i).isWinningFlag()) { // Un'altra condizione casuale, sostituire con la condizione reale
                Text symbolStar = new Text("★");
                symbolStar.setFont(Font.font("Verdana", 24));
                symbolStar.setFill(Color.GOLD);
                vbox.getChildren().add(symbolStar);
            }

            // Aggiunta delle frecce sotto un'altra condizione
            if (risultati.get(i).getScore()>=9) { // Un'altra condizione casuale, sostituire con la condizione reale
                Text symbolStar = new Text("↑↑");
                symbolStar.setFont(Font.font("Verdana", 24));
                symbolStar.setFill(Color.GREEN);
                vbox.getChildren().add(symbolStar);
            }

            vbox.getChildren().add(playerText);

            StackPane stackPane = new StackPane(); // StackPane per sovrapporre VBox e rettangolo
            stackPane.getChildren().addAll(rect, vbox);

            tilePane.getChildren().add(stackPane);

            playerTexts.add(playerText);
            rettangoli.add(vbox);


        }
        return tilePane;
    }

    public void buttonPress() {

        teamCounter=0;
        String alliances="";
        ArrayList<Player> copy=new ArrayList<>(risultati);
        for(int i=0;i<9;i++){
            String testo=i + ": "+ risultati.get(i).getName() +"\nScore: " + risultati.get(i).getScore() + "\nCare: " +
                    risultati.get(i).getMainStrat().getCare();


            if((i<=copy.size()-1)&&(!copy.get(i).getAllies().isEmpty())){
                teamCounter++;
                alliances=alliances + "Team " + teamCounter +":\n";
                for(int z=0;z<risultati.get(i).getAllies().size();z++) {
                    alliances = alliances + " " + risultati.get(i).getAllies().get(z).getName();
                    copy.remove(risultati.get(i).getAllies().get(z));
                }
                alliances=alliances + ":\n";
            }
            playerTexts.get(i).setText(testo);
            additionalText.setText(alliances);
            VBox rect=rettangoli.get(i);
            rect.getChildren().clear();
            // Aggiunta della "X" sotto una certa condizione

            if (risultati.get(i).getScore()<=0) { // Condizione casuale, sostituire con la condizione reale

                Text symbol = new Text("X");
                symbol.setFont(Font.font("Verdana", 24));
                symbol.setFill(Color.BLACK);
                symbol.setTextAlignment(TextAlignment.JUSTIFY);
                rect.getChildren().add(symbol);
            }

            // Aggiunta della stella sotto un'altra condizione
            if (risultati.get(i).isWinningFlag()) { // Un'altra condizione casuale, sostituire con la condizione reale

                Text symbolStar = new Text("★");
                symbolStar.setFont(Font.font("Verdana", 24));
                symbolStar.setFill(Color.GOLD);
                rect.getChildren().add(symbolStar);
            }

            // Aggiunta delle frecce sotto un'altra condizione
            if (risultati.get(i).getScore()>=9) { // Un'altra condizione casuale, sostituire con la condizione reale

                Text symbolStar = new Text("↑↑");
                symbolStar.setFont(Font.font("Verdana", 24));
                symbolStar.setFill(Color.GREEN);
                rect.getChildren().add(symbolStar);
            }

            rect.getChildren().add(playerTexts.get(i));

        }
    }

}

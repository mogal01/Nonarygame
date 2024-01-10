package NonaryGame;

import Players.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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

public class GUIController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Nonary Game");
        primaryStage.setScene(new Scene(root,1000,950));
        primaryStage.show();

    }*/

    @Override
    public void start(Stage primaryStage) throws IOException {
        int teamCounter=1;
        TilePane tilePane = new TilePane();
        String alliances="";
        tilePane.setPrefColumns(3); // 3 colonne

        ArrayList<Player> risultati=TestTeam.test();

        // Layout principale
        VBox mainLayout = new VBox(10); // Spazio di 10 pixel tra gli elementi


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


            Text text = new Text(testo);
            text.setFont(Font.font("Arial", 18));
            text.setFill(Color.BLACK);
            text.setTextAlignment(TextAlignment.CENTER);

            DropShadow shadow = new DropShadow();
            shadow.setOffsetY(3.0f);
            shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
            rect.setEffect(shadow);

            /*VBox box = new VBox(text); // Aggiunge solo il nome del giocatore al rettangolo

            box.setAlignment(Pos.CENTER); // Centra il testo nel rettangolo
            box.getChildren().add(rect);
            tilePane.getChildren().add(box);*/

            StackPane stackPane = new StackPane(); // StackPane per sovrapporre testo e rettangolo
            stackPane.getChildren().addAll(rect, text);
            tilePane.getChildren().add(stackPane);

        }

        ArrayList<Player> copy=new ArrayList<>(risultati);
        for(int j=0;j<copy.size();j++) {
            if(!copy.get(j).getAllies().isEmpty()){
                teamCounter++;
                alliances=alliances + "Team " + teamCounter +":\n";
                for(int z=0;z<risultati.get(j).getAllies().size();z++) {
                    System.out.println("96 + " + alliances);
                    alliances = alliances + " " + risultati.get(j).getAllies().get(z).getName();
                    System.out.println("98 + " + alliances);
                    copy.remove(risultati.get(j).getAllies().get(z));
                }
                alliances=alliances + ":\n";
            }
        }
        // Testo nella parte inferiore della finestra
        Text additionalText = new Text(alliances);
        additionalText.setFont(Font.font("Verdana", 20));
        additionalText.setFill(Color.DARKSLATEBLUE);
        additionalText.setTextAlignment(TextAlignment.CENTER);

        // Aggiunta di tilePane e additionalText al layout principale
        mainLayout.getChildren().addAll(tilePane, additionalText);

        Scene scene = new Scene(mainLayout, 1500, 750);
        primaryStage.setTitle("Nonary Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}

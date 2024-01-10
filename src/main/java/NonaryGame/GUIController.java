package NonaryGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public void start(Stage stage) {
        Pane root = new Pane(); // Scegli un layout adeguato
        Scene scene = new Scene(root, 800, 600); // Imposta dimensioni adeguate

        stage.setTitle("Gioco IA");
        stage.setScene(scene);
        stage.show();


        for (int i = 0; i < 9; i++) {
            Button playerButton = new Button("Giocatore " + (i + 1));
            // Imposta posizione e dimensione
            root.getChildren().add(playerButton);
        }

        /*VBox group1 = new VBox(10); // Spaziatura di 10
        group1.getChildren().addAll(player1, player2, player3); // Aggiungi giocatoriù*/

        // Qui aggiungerai i componenti dell'interfaccia
    }

}

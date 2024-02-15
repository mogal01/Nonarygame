package Players;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class LastMoveManager {
    private static final String DIRECTORY_NAME = "lastMoves";
    private static final String FILE_EXTENSION = "LastMove.txt";

    public static void saveLastMove(String playerName, String opponentName, boolean lastMove) {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir(); // Crea la directory se non esiste
        }

        String fileName = playerName + FILE_EXTENSION;
        File file = new File(directory.getPath() + File.separator + fileName);
        try {
            StringBuilder content = new StringBuilder();
            if (file.exists()) {
                // Leggi il contenuto del file
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    while (line != null) {
                        String[] parts = line.split(":");
                        if (parts.length == 2 && parts[0].equals(opponentName)) {
                            // Il giocatore è già presente, aggiorna la sua mossa
                            //System.out.println("Sono: " + playerName + " E ho trovato nel mio file il nome di : " + opponentName);
                            line = opponentName + ":" + lastMove;
                        }
                        content.append(line).append(System.lineSeparator());
                        line = reader.readLine();
                    }
                }
            }
            // Se il giocatore non era già presente, aggiungi una nuova riga
            if (!content.toString().contains(opponentName)) {
                //System.out.println("Sono: " + playerName + " E NON ho trovato nel mio file il nome di: " + opponentName);
                content.append(opponentName).append(":").append(lastMove).append(System.lineSeparator());
            }

            // Scrivi il contenuto aggiornato nel file
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.print(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getLastMove(String playerName, String opponentName) {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            return false; // La directory non esiste, quindi non ci sono file da leggere
        }

        String fileName = playerName + FILE_EXTENSION;
        File file = new File(directory.getPath() + File.separator + fileName);
        if (!file.exists()) {
            return false; // Il file non esiste, quindi non c'è nessuna mossa precedente da leggere
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(opponentName)) {
                    return Boolean.parseBoolean(parts[1]);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Ritorna un valore predefinito nel caso di errore di lettura
    }

    public static void savePbetrayToFile(double pBetray) {
        String directoryPath = "probability";
        String fileName = "pBetray.txt";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea la directory se non esiste
        }

        File file = new File(directoryPath + File.separator + fileName);
        try {
            if (file.createNewFile() || file.exists()) { // Crea il file se non esiste, procede se esiste già
                try (PrintWriter out = new PrintWriter(file)) {
                    out.println(pBetray);
                    //System.out.println("Valore di pBetray salvato con successo.");
                } catch (IOException e) {
                    System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Impossibile creare il file: " + e.getMessage());
        }
    }


    private static final String DIRECTORY_PATH = "probability";
    private static final String FILE_NAME = "pBetray.txt";

    public static double readPbetrayFromFile() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea la directory se non esiste
        }

        File file = new File(DIRECTORY_PATH + File.separator + FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Crea il file se non esiste
                // Inizializza il file con un valore di default se necessario
                try (PrintWriter out = new PrintWriter(file)) {
                    double defaultPbetray = 0.096; // Valore di default
                    out.println(defaultPbetray);
                }
            } catch (IOException e) {
                System.err.println("Impossibile creare il file: " + e.getMessage());
                return -1; // Valore di errore
            }
        }

        // Tentativo di leggere il valore da file
        try (Scanner scanner = new Scanner(file)) {
            scanner.useLocale(Locale.US);
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                throw new IllegalArgumentException("Il file non contiene un double valido.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + e.getMessage());
            return -1; // Valore di errore
        }
    }


    public static void saveAlphaToFile(double alpha) {
        String directoryPath = "probability";
        String fileName = "alpha.txt";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea la directory se non esiste
        }

        File file = new File(directoryPath + File.separator + fileName);
        try {
            if (file.createNewFile() || file.exists()) { // Crea il file se non esiste, procede se esiste già
                try (PrintWriter out = new PrintWriter(file)) {
                    out.println(alpha);
                    //System.out.println("Valore di pBetray salvato con successo.");
                } catch (IOException e) {
                    System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Impossibile creare il file: " + e.getMessage());
        }
    }


    public static double readAlphaFromAFile() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea la directory se non esiste
        }

        File file = new File(DIRECTORY_PATH + File.separator + "alpha.txt");
        if (!file.exists()) {
            try {
                file.createNewFile(); // Crea il file se non esiste
                // Inizializza il file con un valore di default se necessario
                try (PrintWriter out = new PrintWriter(file)) {
                    double defaultPbetray = 0.01; // Valore di default
                    out.println(defaultPbetray);
                }
            } catch (IOException e) {
                System.err.println("Impossibile creare il file: " + e.getMessage());
                return -1; // Valore di errore
            }
        }

        // Tentativo di leggere il valore da file
        try (Scanner scanner = new Scanner(file)) {
            scanner.useLocale(Locale.US);
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                throw new IllegalArgumentException("Il file non contiene un double valido.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + e.getMessage());
            return -1; // Valore di errore
        }
    }

}

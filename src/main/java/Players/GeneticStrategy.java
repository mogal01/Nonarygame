package Players;

import NonaryGame.TestFile;

import java.util.*;

public class GeneticStrategy extends Strategy{

    private int care;

    private String OwnName;
    private int[] trust;

    private ArrayList<Individual> population;

    private static int sequenceSize;



    public GeneticStrategy(int c, String n){
        super();
        Random Random=new Random();
        care=c;
        //createInitialSequence();
        int[] trust = new int[9];
        for(int i=0;i<9;i++)
         trust[i]=50;
        sequenceSize=9;
        //startingPopulation(n);
        this.trust=trust;
        OwnName=n;
    }
    public GeneticStrategy(String nome){
        super();
        Random Random=new Random();
        //createInitialSequence();
        int[] trust = new int[9];
        for(int i=0;i<9;i++)
            trust[i]=50;
        sequenceSize=9;
        this.trust=trust;
        //startingPopulation(nome);
        OwnName=nome;
    }

    @Override
    public int getCare() {
        return care;
    }

    @Override
    public int[] getTrust() {
        return trust;
    }

    public void changeTrust(int variation, int indexPlayer){
        /*if(variation<0)
            variation=0;*/
        //trust.add(indexPlayer,newValue);
        //System.out.println(variation);
        if(trust[indexPlayer]+variation>0)
         trust[indexPlayer]=trust[indexPlayer]+variation;
    }

    public void setTrust(int[] trust) {
        this.trust = trust;
    }


    public static void setSequenceSize(int s){
        sequenceSize=s;
    }

    /*public void updateSequences() {
        int popsize= (int) Math.pow(2,sequenceSize);
        for (int i = 0; i < popsize; i++) {
            Queue<Boolean> individual = population.get(i);
            individual.add(Math.random() <= ((double) care /10));
            individual.remove();
        }
    }*/

    /*public static void refreshSequence(Queue<Boolean> sequence) {
        Random random=new Random();
        int i,n;

        for (i = 0; i <= sequenceSize; i++) {
            n = random.nextInt(10);
            if (n <= 4)
                sequence.add(true);
            else sequence.add(false);
        }
    }*/



    public Individual createInitialSequence() {
        Random random=new Random();
        boolean choice;
        int n;

        n = random.nextInt(10);
        if (n <= 4)
            choice=true;
        else choice=false;

        Individual start=new Individual(choice);

        for(int i=0;i<care;i++){
            n = random.nextInt(9);
            start.addTrustedPlayer(TestFile.playerList.get(n));
        }
        setPlayerChoicePath(start);
        return start;
    }

    public void startingPopulation(String nomePlayer){
        ArrayList<Integer> sortedIndices=sortPlayersByTrust();
        Random random=new Random();
        population = new ArrayList<>();
        //int popsize= (int) Math.pow(2,sequenceSize);

        int popsize=2;
        int careTemp=care;
        if(care>=4)
            careTemp=3;

        for(int i=0;i<careTemp;i++){
            popsize=popsize*(9-i);
        }
        popsize=popsize/2;

        int mediaTrust=0;
        for(int t:trust){
            mediaTrust=mediaTrust+t;
        }
        mediaTrust=mediaTrust/9;
        int backupTrust=mediaTrust;
        while(population.size()<popsize) {
            Individual individual = new Individual();
            boolean gene = Math.random() < ((double) care /10); //aiuta a generare la popolazione inclinandosi sul valore care, cioè generando piu' frequentemente allys
            individual.setNextChoice(gene);
            for(int j=0;j<care;j++) {
                mediaTrust=backupTrust;
                boolean inserito=false;
                int insertedCount=0;
                do {
                    int k = random.nextInt(9);
                    insertedCount++;
                    inserito = false;
                    if ((!individual.getTrustedPlayers().contains(TestFile.playerList.get(k)))&&(!TestFile.playerList.get(k).getName().equals(nomePlayer))&&(trust[k]>=mediaTrust)) {
                        individual.addTrustedPlayer(TestFile.playerList.get(k));
                        inserito = true;
                    }
                    if(insertedCount>8)
                        mediaTrust=mediaTrust/2;
                } while (!inserito);
            }
            //System.out.println("La grandezza dell'array è: " + individual.getTrustedPlayers().size());

            population.add(individual);
            //removeDuplicates();
        }

    }

    /*public void startingPopulation(String nomePlayer){
        ArrayList<Integer> sortedIndices=sortPlayersByTrust();
        Random random=new Random();
        population = new ArrayList<>();
        //int popsize= (int) Math.pow(2,sequenceSize);

        int popsize=2;

        while(population.size()<popsize) {
            Individual individual = new Individual();
            boolean gene = Math.random() < ((double) care /10); //aiuta a generare la popolazione inclinandosi sul valore care, cioè generando piu' frequentemente allys
            individual.setNextChoice(gene);
            int tempCare=care;
            for(int i=0; i<tempCare;i++){
                if(sortedIndices.get(i)==FitnessFunction.findTrueIndex(OwnName)) {
                    i++;
                    tempCare++;
                }
                individual.addTrustedPlayer(TestFile.playerList.get(sortedIndices.get(i)));

            }
        }

        int mediaTrust=0;
        for(int t:trust){
            mediaTrust=mediaTrust+t;
        }
        mediaTrust=mediaTrust/9;
        int backupTrust=mediaTrust;
        while(population.size()<popsize) {
            Individual individual = new Individual();
            boolean gene = Math.random() < ((double) care /10); //aiuta a generare la popolazione inclinandosi sul valore care, cioè generando piu' frequentemente allys
            individual.setNextChoice(gene);
            for(int j=0;j<care;j++) {
                mediaTrust=backupTrust;
                boolean inserito=false;
                int insertedCount=0;
                do {
                    int k = random.nextInt(9);
                    insertedCount++;
                    inserito = false;
                    if ((!individual.getTrustedPlayers().contains(TestFile.playerList.get(k)))&&(!TestFile.playerList.get(k).getName().equals(nomePlayer))&&(trust[k]>=mediaTrust)) {
                        individual.addTrustedPlayer(TestFile.playerList.get(k));
                        inserito = true;
                    }
                    if(insertedCount>8)
                        mediaTrust=mediaTrust/2;
                } while (!inserito);
            }
            //System.out.println("La grandezza dell'array è: " + individual.getTrustedPlayers().size());

            population.add(individual);
            //removeDuplicates();
        }

    }*/


    private ArrayList<Integer> sortPlayersByTrust() {
        ArrayList<Integer> grades=new ArrayList<>();

        for (int i = 0; i < trust.length; i++) {
            grades.add(trust[i]);
        }
        //System.out.println("La media iniziale è: " + mF);

        // Creare una lista di indici per ordinare la popolazione
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < grades.size(); i++) {
            indices.add(i);
        }

        // Ordina gli indici in base alle valutazioni (grades)
        Collections.sort(indices, (i1, i2) -> Double.compare(grades.get(i2), grades.get(i1)));
        return indices;
    }

    @Override
    public boolean pickChoice(){

            Individual path = geneticSelection();
            setPlayerChoicePath(path);

        /*if(population.contains(path))
            population.remove(path);*/
        boolean pick=getPlayerChoicePath().getNextChoice();
        addPlayerChoiceHistory(pick);
        if((getOpponent().getName().equals("Corvo"))||(getOpponent().getName().equals("Falco"))) {
            LastMoveManager.saveLastMove(OwnName, getOpponent().getName(), pick);
        }
        String nomeAvversario;
        if(getOpponent().getMainStrat().getAlly()!=null)
            nomeAvversario=getOpponent().getMainStrat().getAlly().getName();
        else
            nomeAvversario="test";
        if((nomeAvversario.equals("Corvo"))||(nomeAvversario.equals("Falco"))) {
            LastMoveManager.saveLastMove(OwnName, nomeAvversario, pick);
        }

        //updateSequences();

        //System.out.println("Io sono: " + OwnName + " E sono contro: " + getOpponent().getName());
        /*if(getOpponent().getName().equals("Corvo")){
            System.out.println("Io sono: " + OwnName);
            System.out.println("la scelta presa contro Corvo è : " + pick);
            System.out.println("Gli alleati che ho deciso sono : ");
            for(Player p:path.getTrustedPlayers())
                System.out.println(p.getName()+ " , ");
        }

        if(getOpponent().getMainStrat().getAlly().getName().equals("Corvo"))
        if(getOpponent().getMainStrat().getAlly().getName().equals("Corvo")){
            System.out.println("******ho contro Corvo come Ally");
            System.out.println("Io sono: " + OwnName);
            System.out.println("la scelta presa contro Corvo è : " + pick);
            System.out.println("Gli alleati che ho deciso sono : ");
            for(Player p:path.getTrustedPlayers())
                System.out.println(p.getName()+ " , ");
        }*/


        return pick;
    }

    private Individual changeChoice(Individual candidate){
        Individual temp1=new Individual(candidate.getNextChoice(),candidate.getTrustedPlayers());
        Individual temp2=new Individual(!candidate.getNextChoice(),candidate.getTrustedPlayers());

        double[] m1= FitnessFunction.evaluateStrategy(temp1, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
        double[] m2= FitnessFunction.evaluateStrategy(temp2, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());

        if(confrontaFitness(m1,m2))
            return temp1;
        else
            return temp2;
    }

    public  Individual geneticSelection(){

        Random random=new Random();
        //System.out.println("Sono: " + OwnName + " e la mia popsize è di : " + population.size());
        checkPopulation();
        removeDuplicates();
        //System.out.println("Ora è di: " + population.size());
        ArrayList<Individual> newPopulation=new ArrayList<>();

        if(care==0){
            Individual c1=new Individual(true);
            Individual c2=new Individual(false);
            double[] val1 = FitnessFunction.evaluateStrategy(c1, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            double[] val2 = FitnessFunction.evaluateStrategy(c2, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            if(confrontaFitness(val1,val2))
                return c1;
                else
                    return c2;
        }

            ArrayList<double[]> grades=new ArrayList<>();
            ArrayList<Individual> elitistPopulation=new ArrayList<>();
            double mF=0;
            int count=0;
            double newMf=0;

            for (int i = 0; i < population.size(); i++) {
                double[] f=FitnessFunction.evaluateStrategy(population.get(i), care, trust, super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
                grades.add(f);
                for(int j=0;j<f.length;j++)
                    mF=mF+f[j];
                count++;
            }
            mF=mF/count;
            //System.out.println("La media iniziale è: " + mF);

            // Creare una lista di indici per ordinare la popolazione
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < grades.size(); i++) {
                indices.add(i);
            }

            count=0;
            // Ordina gli indici in base alle valutazioni (grades)
            Collections.sort(indices, (i1, i2) -> Double.compare(grades.get(i2)[0], grades.get(i1)[0]));
            for(int i=0; i<care;i++) {
                if((i<population.size()) && (i<indices.size())) {
                    elitistPopulation.add(population.get(indices.get(i)));
                    newPopulation.add(population.get(indices.get(i)));
                    for (int j = 0; j < grades.get(i).length; j++) {
                        newMf = newMf + grades.get(i)[0];
                        count++;
                    }
                }
            }
            double tempMf=0;
            int iterationCount=0;
            do {
                ArrayList<Individual> selectedPopulation = KTournamentSelection(population.size());
                Individual crossoverResults = onePointCrossover(selectedPopulation.get(0), selectedPopulation.get(1));
                Individual bitFlipResult = bitFlipMutation(crossoverResults);
                newPopulation.add(selectedPopulation.get(0));
                newPopulation.add(selectedPopulation.get(1));
                newPopulation.add(crossoverResults);
                newPopulation.add(bitFlipResult);
                double[] val = FitnessFunction.evaluateStrategy(selectedPopulation.get(0), care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
                for (double v : val) newMf = newMf + v;
                val = FitnessFunction.evaluateStrategy(selectedPopulation.get(1), care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
                for (double v : val) newMf = newMf + v;
                val = FitnessFunction.evaluateStrategy(crossoverResults, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
                for (double v : val) newMf = newMf + v;
                val = FitnessFunction.evaluateStrategy(bitFlipResult, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
                for (double v : val) newMf = newMf + v;
                count=count+4;
                tempMf=newMf/count;
                iterationCount++;
            }while(((tempMf<mF)||(iterationCount<50)||(newPopulation.size()<population.size())) && (iterationCount<100)); //((iterationCount < 20 || iterationCount < 50) && (tempMf < mF));

        //System.out.println("Sono state fatte: " + iterationCount + " iterazioni");
        //System.out.println("La media finale è: " + tempMf);

        /*if(getOpponent().getName().equals("Corvo")){
            System.out.println("Io sono: " + OwnName);
            System.out.println("newPath è: " + newPath.getNextChoice());
            for(Player p:newPath.getTrustedPlayers()){
                System.out.println(p.getName());
            }
            System.out.println("e ha il punteggio di: " + m1);

            System.out.println("tempBest è: " + tempBest.getNextChoice());
            for(Player p:tempBest.getTrustedPlayers()){
                System.out.println(p.getName());
            }
            System.out.println("e ha il punteggio di: " + m2);
        }*/
        population=newPopulation;
        removeDuplicates();
        //System.out.println("è diventata: " + population.size());
        ArrayList<double[]> newGrades=new ArrayList<>();
        if(population.size()==1)
            population.add(new Individual(!population.get(0).getNextChoice(),population.get(0).getTrustedPlayers()));
        for (int i = 0; i < population.size(); i++) {
            double[] f = FitnessFunction.evaluateStrategy(population.get(i), care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            newGrades.add(f);
        }
        List<Integer> newIndices = new ArrayList<>();
        for (int i = 0; i < newGrades.size(); i++) {
            newIndices.add(i);
        }
        Collections.sort(newIndices, (i1, i2) -> Double.compare(newGrades.get(i2)[0], newGrades.get(i1)[0]));
        Individual bestOne=population.get(newIndices.get(0));
        Individual bestSecond=population.get(newIndices.get(1));

        double m1[],m2[];
        m1=FitnessFunction.evaluateStrategy(bestOne, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
        m2=FitnessFunction.evaluateStrategy(bestSecond, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
        if(Arrays.equals(m1, m2)){
            int n=random.nextInt(10);
            if(n>4)
                bestOne.setNextChoice(!bestOne.getNextChoice());
            else
                bestSecond.setNextChoice(!bestSecond.getNextChoice());
        }
        //IN QUESTO MOMENTO RACCOGLIE I DATI DELLE FITNESS, CHE SONO ARRAY DI DOUBLE, BISOGNA SVILUPPARE LA VALUTAZIONE DELLE SINGOLE CELLE E IN CASO FARE SCAMBI
        String nomeAvversario;
        if(getOpponent().getMainStrat().getAlly()!=null)
          nomeAvversario=getOpponent().getMainStrat().getAlly().getName();
        else
            nomeAvversario="test";
        /*if((OwnName.equals("Civetta"))&&((getOpponent().getName().equals("Corvo"))||(nomeAvversario.equals("Corvo")))){
            System.out.println("Sono contro: " + getOpponent().getName());
            System.out.println("La mia nuova popolazione è la seguente, e la size è : " + population.size());
            for(int i=0;i<population.size();i++){
                System.out.println("\n SCELTA DA PRENDERE:");
                System.out.println(population.get(i).getNextChoice());
                System.out.println("ALLEATI SCELTI:");
                for(int j=0;j<population.get(i).getTrustedPlayers().size();j++){
                    System.out.println(population.get(i).getTrustedPlayers().get(j).getName());
                }
            }

            System.out.println("Le migliori due scelte sono: ");
            System.out.println("BEST ONE:");
            System.out.println(bestOne.getNextChoice());
            for(int j=0;j<bestOne.getTrustedPlayers().size();j++){
                System.out.println(bestOne.getTrustedPlayers().get(j).getName());
            }
            System.out.println("BEST SECOND:");
            System.out.println(bestSecond.getNextChoice());
            for(int j=0;j<bestSecond.getTrustedPlayers().size();j++){
                System.out.println(bestSecond.getTrustedPlayers().get(j).getName());
            }

        }

        if((getOpponent().getName().equals("Corvo"))||(nomeAvversario.equals("Corvo"))){
            System.out.println("Il mio avversario è: " + getOpponent().getName());
            ArrayList<Player> acaso=new ArrayList<>(population.get(random.nextInt(population.size())).getTrustedPlayers());
            Individual test1=new Individual(true,acaso);
            Individual test2=new Individual(false,acaso);
            double[] t1=FitnessFunction.evaluateStrategy(test1, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            double[] t2=FitnessFunction.evaluateStrategy(test2, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());

            System.out.println("Fitness per scegliere true: " +  t1[0]);
            System.out.println("Fitness per scegliere false: " +  t2[0]);
            if(t1[0]>t2[0]){
                System.out.println("Capiamo:");
                System.out.println("Test1:");
                for(int j=0;j<test1.getTrustedPlayers().size();j++){
                    System.out.println(test1.getTrustedPlayers().get(j).getName());
                }
            }
        }*/
        return bestOne;

    }

    /*public Individual steadyStateGenetic(){

        if(care==0){
            Individual c1=new Individual(true);
            Individual c2=new Individual(false);
            double[] val1 = FitnessFunction.evaluateStrategy(c1, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            double[] val2 = FitnessFunction.evaluateStrategy(c2, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            if(confrontaFitness(val1,val2))
                return c1;
            else
                return c2;
        }

        ArrayList<Individual> newPopulation=new ArrayList<>();
        double tempMf=0;
        int iterationCount=0;
        double mF=0;
        int count=0;
        double newMf=0;
        do {
            Individual crossoverResults = onePointCrossover(population.get(0), population.get(1));
            Individual bitFlipResult = bitFlipMutation(crossoverResults);
            newPopulation.add(population.get(0));
            newPopulation.add(population.get(1));
            newPopulation.add(crossoverResults);
            newPopulation.add(bitFlipResult);
            double[] val = FitnessFunction.evaluateStrategy(population.get(0), care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            for (double v : val) newMf = newMf + v;
            val = FitnessFunction.evaluateStrategy(population.get(1), care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            for (double v : val) newMf = newMf + v;
            val = FitnessFunction.evaluateStrategy(crossoverResults, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            for (double v : val) newMf = newMf + v;
            val = FitnessFunction.evaluateStrategy(bitFlipResult, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            for (double v : val) newMf = newMf + v;
            count=count+4;
            tempMf=newMf/count;
            iterationCount++;
        }while(((tempMf<mF)||(iterationCount<40)) && (iterationCount<100)); //((iterationCount < 20 || iterationCount < 50) && (tempMf < mF));
        return null;
    }*/

    public Individual geneticCrossover(ArrayList<Individual> selected){
        Individual p1=selected.get(0);
        Individual p2=selected.get(1);
        /*System.out.println("Valore array p1: " + p1.getTrustedPlayers().size());
        System.out.println("Valore array p2: " + p2.getTrustedPlayers().size());*/

        Individual crossovered;
        crossovered = onePointCrossover(p1, p2);
        Individual bitflipped;
        bitflipped = bitFlipMutation(crossovered);



       double[] val1= FitnessFunction.evaluateStrategy(p1, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
       double[] val2= FitnessFunction.evaluateStrategy(crossovered, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(),super.getOpponent() );
       double[] val3= FitnessFunction.evaluateStrategy(bitflipped, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(),super.getOpponent() );
       double[] val4= FitnessFunction.evaluateStrategy(p2, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(),super.getOpponent() );

       /*((val1>=val2)&&(val1>=val3)&&(val1>=val4))
           return p1;

        if((val2>=val1)&&(val2>=val3)&&(val2>=val4))
            return crossovered;

        if((val3>=val2)&&(val3>=val1)&&(val3>=val4))
            return bitflipped;*/

        return p2;



    }

    public ArrayList<Individual> KTournamentSelection(int k) {
        Random random = new Random();
        ArrayList<Individual> parents = new ArrayList<>();

        for (int i = 0; i < 2; i++) { // Seleziona due genitori
            ArrayList<Individual> tournamentParticipants = new ArrayList<>();

            // Selezione casuale di k partecipanti al torneo
            for (int j = 0; j < k; j++) {
                int randomIndex = random.nextInt(population.size());
                tournamentParticipants.add(population.get(randomIndex));
            }

            // Ordina i partecipanti in base al primo valore nell'array di double della fitness (in ordine decrescente)
            Collections.sort(tournamentParticipants, (a, b) -> {
                double fitnessA = FitnessFunction.evaluateStrategy(a, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent())[0];
                double fitnessB = FitnessFunction.evaluateStrategy(b, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent())[0];
                return Double.compare(fitnessB, fitnessA);
            });

            // Aggiungi il partecipante con la fitness più alta come genitore
            parents.add(tournamentParticipants.get(0));
        }

        return parents;
    }


    public Individual onePointCrossover(Individual parent1, Individual parent2) {

        double[] valueChild1;
        double[] valueChild2;

        Random random = new Random();
        Individual copy1 = new Individual(parent1.getNextChoice(),new ArrayList<>(parent1.getTrustedPlayers()));
        Individual copy2 = new Individual(parent2.getNextChoice(),new ArrayList<>(parent2.getTrustedPlayers()));


        if(care==0){
            return new Individual(parent2.getNextChoice(),new ArrayList<>(parent1.getTrustedPlayers()));
        }

        if(care==1){
            Individual temp=new Individual(true,copy1.getTrustedPlayers());
            copy1.setTrustedPlayers(copy2.getTrustedPlayers());
            copy2.setTrustedPlayers(temp.getTrustedPlayers());
            valueChild1=FitnessFunction.evaluateStrategy(copy1, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
            valueChild2=FitnessFunction.evaluateStrategy(copy2, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
            if(confrontaFitness(valueChild1,valueChild2))
                return copy1;
            else
                return copy2;

        }


        int crossoverPoint = random.nextInt(care); // Punto di taglio casuale

        Individual child=new Individual();
        // Copia gli elementi dall'inizio dell'array di parent1 fino al punto di taglio nell'individuo figlio
        for (int i = 0; i < crossoverPoint; i++) {
            Player gene = parent1.getTrustedPlayers().get(i);
            child.addTrustedPlayer(gene);
        }

        // Copia gli elementi dall'inizio dell'array di parent2 a partire dal punto di taglio nell'individuo figlio
        for (int i = crossoverPoint; i < parent2.getTrustedPlayers().size(); i++) {
            Player gene = parent2.getTrustedPlayers().get(i);
            child.addTrustedPlayer(gene);
        }

        /*System.out.println("numero uscito dal random: " + point1);
        System.out.println("care: " + care);
        System.out.println("Grandezza array: " + copy1.getTrustedPlayers().size());
        Player tempP=copy1.getTrustedPlayers().remove(point1);
        if((point2==0)) {
            copy1.addTrustedPlayer(copy2.getTrustedPlayers().get(point2));
        }if((point2!=0))
            copy1.addTrustedPlayer(copy2.getTrustedPlayers().get(point2-1));

        copy2.getTrustedPlayers().remove(point2);
        copy2.addTrustedPlayer(tempP);

        if(checkIndividual(copy1)) {
            population.add(copy1);
            valueChild1=FitnessFunction.evaluateStrategy(copy1, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
        }

        if(checkIndividual(copy2)) {
            population.add(copy2);
            valueChild2=FitnessFunction.evaluateStrategy(copy2, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
        }*/


        /*if((valueChild1==0)&&(valueChild2!=0))
            return copy2;
        if((valueChild1!=0)&&(valueChild2==0))
            return copy1;
        if((valueChild1==0)&&(valueChild2==0))
            return parent1;*/
        valueChild1=FitnessFunction.evaluateStrategy(copy1, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());
        valueChild2=FitnessFunction.evaluateStrategy(copy2, care,trust,super.getScore(), super.isOpponentFriend(),super.getAlly(), super.getOpponent());

        if(confrontaFitness(valueChild1,valueChild2))
            return copy1;
        else
            return copy2;

    }

    public Individual bitFlipMutation(Individual individual) {
        Individual mutatedIndividual = new Individual(individual.getNextChoice(),new ArrayList<>(individual.getTrustedPlayers()));

        Random random = new Random();

        // Scegli casualmente l'indice dell'elemento da invertire (tra 0 e 4 inclusi)
        int indexToFlip = random.nextInt(care);

       // System.out.println("care riga 252: " + care);
        if(indexToFlip>0) {
            mutatedIndividual.getTrustedPlayers().remove(indexToFlip);
            double[] values= new double[9];
            ArrayList<Integer> indices=new ArrayList<>();
            for(int i=0;i<9;i++){
                values[i]=FitnessFunction.evaluateTrustedPlayer(TestFile.playerList.get(i),care,trust,super.getScore());
                indices.add(i);
                // Ordina gli indici in base alle valutazioni (grades)
                Collections.sort(indices, (i1, i2) -> Double.compare(values[i2], values[i1]));
            }
            Player p;
            do {
                 p=TestFile.playerList.get(indices.get(random.nextInt(4+care)));
            }while(indices.get(random.nextInt(3))==FitnessFunction.findTrueIndex(OwnName));
            mutatedIndividual.addTrustedPlayer(p);
            //mutatedIndividual.addTrustedPlayer(TestFile.playerList.get(random.nextInt(9)));
        }

        mutatedIndividual=changeChoice(mutatedIndividual);

        if(checkIndividual(mutatedIndividual)) {
            return mutatedIndividual;
        }else {
            Individual alteredIndividual = new Individual(individual.getNextChoice(),new ArrayList<>(individual.getTrustedPlayers()));
            alteredIndividual=changeChoice(alteredIndividual);
            return alteredIndividual;
        }
    }


    public boolean checkIndividual(Individual ind) {
        ArrayList<Player> trustedPlayers = ind.getTrustedPlayers();

        HashSet<String> playerNames = new HashSet<>();

        for (Player player : trustedPlayers) {
            String playerName = player.getName();
            if (playerNames.contains(playerName)||playerName.equals(OwnName)) {
                // Nome ripetuto trovato
                return false;
            } else {
                playerNames.add(playerName);
            }
        }

        // Nessun nome ripetuto trovato
        return true;
    }

    public void checkPopulation(){
        for(Individual i:population){
            if (!checkIndividual(i))
                population.remove(i);
        }
    }

    public void removeDuplicates() {
        Map<Double, Individual> fitnessMap = new HashMap<>();

        // Scansione della popolazione per memorizzare gli individui con la fitness
        for (Individual individual : population) {
            double[] fitness =FitnessFunction.evaluateStrategy(individual, care, trust, super.getScore(), super.isOpponentFriend(), super.getAlly(), super.getOpponent());
            double sum=0;
            for(double d:fitness)
                sum=sum+d;
            // Calcola la fitness dell'individuo
            // Aggiungi l'individuo alla mappa solo se non c'è già una fitness identica o migliore
            if (!fitnessMap.containsKey(sum)) {
                fitnessMap.put(sum, individual);
            }
        }

        // Costruzione della nuova popolazione utilizzando gli individui unici
        population= new ArrayList<>(fitnessMap.values());
    }

    public boolean confrontaFitness(double[] f1,double[] f2){
          int count1=0, count2=0;
          double val1=0,val2=0;

          if(f1[0]>f2[0])
              count1++;
          else
              count2++;

          for(int i=0;i<care;i++){
              val1=val1+f1[i];
              val2=val2+f2[i];
              if(f1[i]>f2[i])
                  count1++;
              else
                  count2++;
          }

          if(count1==count2){
            return val1>val2;
          }

          return count1 > count2;

    }

    // Funzione per controllare se due ArrayList contengono gli stessi oggetti (giocatori)
    public static boolean areIndividualListsEqual(ArrayList<Player> list1, ArrayList<Player> list2) {
        if (list1.size() != list2.size()) {
            return false; // Hanno lunghezze diverse, quindi sicuramente diversi
        }

        // Verifica se ogni elemento di list1 è presente in list2
        for (Player player : list1) {
            if (!list2.contains(player)) {
                return false; // Un elemento di list1 non è presente in list2
            }
        }

        // Verifica se ogni elemento di list2 è presente in list1 (opzionale, a seconda delle tue esigenze)
        for (Player player : list2) {
            if (!list1.contains(player)) {
                return false; // Un elemento di list2 non è presente in list1
            }
        }

        return true; // Entrambi gli ArrayList contengono gli stessi giocatori
    }

}

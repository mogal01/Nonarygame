package Players;

import java.util.*;

public class Goal extends Strategy{

    private int care;

    private int trust;

    private ArrayList<Queue<Boolean>> population;

    private static int sequenceSize;

    public Goal(int c){
        super(c);
        Random Random=new Random();
        care=c;
        createInitialSequence();
        trust=Random.nextInt(9);
        sequenceSize=10;
    }

    public static void setSequenceSize(int s){
        sequenceSize=s;
    }

    public void updateSequences() {
        int popsize= (int) Math.pow(2,sequenceSize);
        for (int i = 0; i < popsize; i++) {
            Queue<Boolean> individual = population.get(i);
            individual.remove();
        }
    }

    public static void refreshSequence(Queue<Boolean> sequence) {
        Random random=new Random();
        int i,n;


        for (i = 0; i <= sequenceSize; i++) {
            n = random.nextInt(10);
            if (n <= 4)
                sequence.add(true);
            else sequence.add(false);
        }
    }

    @Override
    public Queue<Boolean> createInitialSequence() {
        Random random=new Random();
        Queue<Boolean> sequence=new LinkedList<>();
        int i,n;

        if (care==0){
            for(i=0;i<=10;i++)
            sequence.add(false);
        }

        else {
            for (i = 0; i <= 10; i++) {
                n = random.nextInt(10);
                if (n <= 4)
                    sequence.add(true);
                else sequence.add(false);
            }

        }


        setPlayerChoicePath(sequence);
        return sequence;
    }

    public void startingPopulation(){
        ArrayList<Queue<Boolean>> startingPop = new ArrayList<>();
        int popsize= (int) Math.pow(2,sequenceSize);

        for (int i = 0; i < popsize; i++) {
            Queue<Boolean> individual = new LinkedList<>();


            for (int j = 0; j < sequenceSize; j++) {
                boolean gene = Math.random() < ((double) care /10); //aiuta a generare la popolazione inclinandosi sul valore care, cioè generando piu' frequentemente allys
                individual.add(gene);
            }


            startingPop.add(individual);

            population=startingPop;
        }


    }

    public  ArrayList<Queue<Boolean>> geneticSelection(){
        ArrayList<Double> grades=new ArrayList<>();
        for(int i=0;i<population.size();i++){
          grades.add(FitnessFunction.evaluateStrategy(population.get(i),super.getOpponentChoiceHistory(),care,trust,super.getScore(),super.getOpponentScore()));
        }

        // Creare una lista di indici per ordinare la popolazione
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < grades.size(); i++) {
            indices.add(i);
        }

        // Ordina gli indici in base alle valutazioni (grades)
        Collections.sort(indices, (i1, i2) -> Double.compare(grades.get(i2), grades.get(i1)));


        ArrayList<Queue<Boolean>> selectedPopulation = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            selectedPopulation.add(population.get(indices.get(i)));
        }

        return selectedPopulation;

    }

    public void geneticCrossover(ArrayList<Queue<Boolean>> selected){
        Queue<Boolean> p1=selected.get(0);
        Queue<Boolean> p2=selected.get(1);

        twoPointCrossover(p1,p2);
    }

    public Queue<Boolean> twoPointCrossover(Queue<Boolean> parent1, Queue<Boolean> parent2) {
        int size = parent1.size();
        Queue<Boolean> child1 = new LinkedList<>(parent1);
        Queue<Boolean> child2 = new LinkedList<>(parent2);

        // Scegli due punti casuali
        int point1 = (int) (Math.random() * size);
        int point2 = (int) (Math.random() * size);

        // Assicurati che point1 sia minore di point2
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        // Crossover: scambia i geni tra i punti di crossover
        for (int i = point1; i < point2; i++) {
            boolean temp = child1.poll();
            child1.offer(child2.poll());
            child2.offer(temp);
        }

        double valueChild1=FitnessFunction.evaluateStrategy(child1,super.getOpponentChoiceHistory(),care,trust,super.getScore(),super.getOpponentScore());
        double valueChild2=FitnessFunction.evaluateStrategy(child2,super.getOpponentChoiceHistory(),care,trust,super.getScore(),super.getOpponentScore());

        return valueChild1>valueChild2 ? child1:child2;
    }


    public static Queue<Boolean> bitFlipMutation(Queue<Boolean> individual) {
        Queue<Boolean> mutatedIndividual = new LinkedList<>(individual);

        Random random = new Random();

        // Scegli casualmente l'indice dell'elemento da invertire (tra 0 e 4 inclusi)
        int indexToFlip = random.nextInt(5);

        // Inverti l'elemento scelto
        boolean oldValue = mutatedIndividual.poll();
        mutatedIndividual.offer(!oldValue);

        return mutatedIndividual;
    }





}

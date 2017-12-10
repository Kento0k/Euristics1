package Euristics;
import java.util.*;
public class PackageTask {
    public class GeneticComparator implements Comparator<Chromosome> {
        @Override
        public int compare(Chromosome o1, Chromosome o2) {
            return o2.rezCost - o1.rezCost;
        }
    }
    public class GreedyComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            if ((double)o2.cost / (double)o2.weight > (double)o1.cost / (double)o1.weight)
                return 1;
            else
                return -1;
        }
    }
    private List<Chromosome> newPopulation(int size){
        List<Chromosome> newChromosomes= new ArrayList<>();
        int popSize= 2*size;
        while(newChromosomes.size()!= popSize){
            List<Integer> set= new ArrayList<>();
            for(int i=0; i<size; i++){
                Random rnd= new Random();
                set.add(rnd.nextInt(2));
            }
            for(Chromosome i: newChromosomes){
                if (i.genes.equals(set)) {
                    set = null;
                    break;
                }
            }
            if (set!= null)
                newChromosomes.add(new Chromosome(set));
        }
        return newChromosomes;
    }
    public List<Chromosome> chooseBest(List<Chromosome> population, List<Item> items, int capacity){
        for(int i=0; i<population.size(); i++){
            int cost= 0;
            int weight=0;
            for(int j=0; j<population.get(i).genes.size(); j++){
                if(population.get(i).genes.get(j)==1){
                    cost+= items.get(j).cost;
                    weight+= items.get(j).weight;
                }
            }
            if (weight > capacity)
                cost = capacity - weight;
            population.get(i).rezCost = cost;
        }
        Collections.sort(population, new GeneticComparator());
        return population.subList(0, population.size() / 2);
    }
     public List<Chromosome> crossing(List<Chromosome> population){
        List<Chromosome> childPopulation= new ArrayList<>();
        int populationSize=population.size();
        for (int i=0; i<populationSize; i++){
            Random rnd= new Random();
            Chromosome firstChromosome = population.get(Math.abs(rnd.nextInt(populationSize) % populationSize));
            Chromosome secondChromosome = population.get(Math.abs(rnd.nextInt(populationSize) % populationSize));
            Chromosome childChromosome = firstChromosome.pairing(secondChromosome);
            childPopulation.add(childChromosome);
        }
        return childPopulation;
    }
    public List<Chromosome> mutation(List<Chromosome> population){
        List<Chromosome> mutants= new ArrayList<>();
        for(int i=0; i<population.size()/10; i++){
            Random rnd= new Random();
            Chromosome mutationChromosome = population.get(rnd.nextInt(population.size()));
            mutationChromosome.mutation();
            mutants.add(mutationChromosome);
        }
        return mutants;
    }
    public  Chromosome genetic(int capacity, ArrayList<Item> items) {

        int itemsNumber = items.size();
        List<Chromosome> newGeneration = newPopulation(itemsNumber);
        for(int i = 0; i < 2 * itemsNumber; i++) {
            List<Chromosome> selectedPopulation = chooseBest(newGeneration, items, capacity);
            List<Chromosome> crossedPopulation = crossing(selectedPopulation);
            List<Chromosome> joint = new ArrayList<>(selectedPopulation);
            joint.addAll(crossedPopulation);
            newGeneration = mutation(joint);
        }
        List<Chromosome> bestWay= new ArrayList<>();
        bestWay = chooseBest(newGeneration, items, capacity);
        if (bestWay != null) {
            Chromosome bestChromosome = bestWay.get(0);
            return bestChromosome;
        }
        return null;
    }
    public int greedy(int capacity, ArrayList<Item> items) {
        List<Item> result = items;
        int rezCost=0;
        Collections.sort(result, new GreedyComparator());
        int resultCapacity = 0;
        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).weight + resultCapacity > capacity) {
                result.remove(i);
                i--;
                continue;
            }
            resultCapacity += result.get(i).weight;
            rezCost += result.get(i).cost;
        }
        return rezCost;
    }

}

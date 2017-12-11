package Euristics;
import java.util.*;
public class PackageTask {
    public static class GeneticComparator implements Comparator<Chromosome> {
        @Override
        public int compare(Chromosome o1, Chromosome o2) {
            return o2.rezCost - o1.rezCost;
        }
    }
    public static class GreedyComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            if ((double)o2.cost / (double)o2.weight > (double)o1.cost / (double)o1.weight)
                return 1;
            else
                return -1;
        }
    }
    private static List<Chromosome> newPopulation(int size){
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
    private static List<Chromosome> chooseBest(List<Chromosome> population, List<Item> items, int capacity){
        if (population.isEmpty())
            return null;
        for(Chromosome popCount:population){
            int cost= 0;
            int weight=0;
            for(int j=0; j<popCount.genes.size(); j++){
                if(popCount.genes.get(j)==1){
                    cost+= items.get(j).cost;
                    weight+= items.get(j).weight;
                }
            }
            if (weight > capacity)
                cost = capacity - weight;
            popCount.rezCost = cost;
        }
        population.sort(new GeneticComparator());
        return population.subList(0, population.size() / 2);
    }
     private static List<Chromosome> crossing(List<Chromosome> population){
        if (population.isEmpty())
            return null;
        List<Chromosome> childPopulation= new ArrayList<>();
        int populationSize=population.size()-population.size()%2;
        for (int i=0; i<populationSize; i++){
            Random rnd= new Random();
            Chromosome firstChromosome = population.get(Math.abs(rnd.nextInt(populationSize)));
            Chromosome secondChromosome = population.get(Math.abs(rnd.nextInt(populationSize)));
            Chromosome childChromosome = firstChromosome.pairing(secondChromosome);
            childPopulation.add(childChromosome);
        }
        return childPopulation;
    }
    private static List<Chromosome> mutation(List<Chromosome> population){
         if (population.isEmpty())
             return null;
        List<Chromosome> mutants= new ArrayList<>();
        mutants.addAll(population);
        for(int i=0; i<population.size()/10; i++){
            Random rnd= new Random();
            Chromosome mutationChromosome = mutants.get(Math.abs(rnd.nextInt(mutants.size())));
            mutationChromosome.mutation();
            mutants.add(mutationChromosome);
        }
        return mutants;
    }
    static  Chromosome genetic(int capacity, ArrayList<Item> items) {
        if(items.isEmpty())
            return null;
        int itemsNumber = items.size();
        List<Chromosome> newGeneration = newPopulation(itemsNumber);
        for(int i = 0; i < 2 * itemsNumber; i++) {
            List<Chromosome> selectedPopulation = chooseBest(newGeneration, items, capacity);
            List<Chromosome> crossedPopulation = crossing(selectedPopulation);
            List<Chromosome> joint = new ArrayList<>();
            joint.addAll(selectedPopulation);
            joint.addAll(crossedPopulation);
            newGeneration = mutation(joint);
        }
        List<Chromosome> bestWay;
        bestWay = chooseBest(newGeneration, items, capacity);
        if (bestWay != null) {
           return bestWay.get(0);
        }
        return null;
    }
    static List<Item> greedy(int capacity, ArrayList<Item> items) {
        if(items.isEmpty())
            return null;
        List<Item> result = new ArrayList<>();
        result.addAll(items);
        result.sort(new GreedyComparator());
        int resultCapacity = 0;
        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).weight + resultCapacity > capacity) {
                result.remove(i);
                i--;
                continue;
            }
            resultCapacity += result.get(i).weight;
        }
        return result;
    }

}

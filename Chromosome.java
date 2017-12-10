package Euristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {
    List<Integer> genes;
    int size;
    int rezCost;
    Chromosome(List<Integer> argGenes){
        genes= argGenes;
        size= argGenes.size();
    }
    public Chromosome pairing(Chromosome secondParent){
        List<Integer> secondGenes= new ArrayList<>();
        List<Integer> childGenes= new ArrayList<>();
        for(int i=0; i< size; i++){
            if(this.genes.get(i).equals(secondParent.genes.get(i)))
                childGenes.add(this.genes.get(i));
            else{
                Random rnd= new Random();
                childGenes.add(Math.abs(rnd.nextInt(1000)%2));
            }
        }
        return new Chromosome(childGenes);
    }
    public void mutation(){
        Random rnd = new Random();
        int numb;
        for (int i = 0; i < size / 5; i++) {
            numb = Math.abs(rnd.nextInt() % size);
            if ((genes.get(numb) == 0)) {
                genes.set(numb, 1);
            } else {
                genes.set(numb, 0);
            }
        }
    }
}

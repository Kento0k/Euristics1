package Euristics;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rnd= new Random();
        PackageTask qwerty = new PackageTask();
        int geneticCost;
        int greedyCost;
        int geneticWeight;
        int greedyWeight;
        for(int j=0; j<20; j++) {
            geneticCost=0;
            greedyCost=0;
            geneticWeight=0;
            greedyWeight=0;
            int numItems = 1 + Math.abs(rnd.nextInt(30));
            int capacity = 1 + Math.abs(rnd.nextInt(500));
            ArrayList<Item> items = new ArrayList<>();
            for (int i = 0; i < numItems; i++) {
                items.add(new Item(Math.abs(rnd.nextInt(50)), Math.abs(rnd.nextInt(50))));
            }
            Chromosome resultGenetic = qwerty.genetic(capacity, items);
            List<Item> resultGreedy= PackageTask.greedy(capacity, items);
            geneticCost=resultGenetic.rezCost;
            for(int k=0; k<resultGreedy.size();k++){
                greedyCost+=resultGreedy.get(k).cost;
                greedyWeight+=resultGreedy.get(k).weight;
            }
            for(int k=0; k<resultGenetic.genes.size(); k++){
                if (resultGenetic.genes.get(k) == 1) {
                    geneticWeight += items.get(k).weight;
                }
            }
            System.out.println("Capacity: "+ capacity);
            System.out.println("Genetic algorhytm: ");
            System.out.println("Weight= "+ geneticWeight+ "   Cost= "+ geneticCost);
            System.out.println("Greedy algorhytm: ");
            System.out.println("Weight= "+ greedyWeight+ "   Cost= "+ greedyCost);
            System.out.println("_________________________________");
        }

    }

}

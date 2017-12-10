package Euristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rnd= new Random();
        int numItems=1+Math.abs(rnd.nextInt(100));
        int capacity=1+Math.abs(rnd.nextInt(1000));
        ArrayList<Item> items= new ArrayList<>();
        for(int i=0; i<numItems; i++){
            Item item= new Item();
            items.add(item);
        }
        PackageTask qwerty = new PackageTask();
        Chromosome resultGenetic= qwerty.genetic(capacity, items);
        int resultGreedy= qwerty.greedy(capacity, items);
        System.out.println("Genetic result:"+ resultGenetic.rezCost);
        System.out.println("Genetic result:"+ resultGreedy);
    }

}

package Euristics;
import java.util.*;
public class Item {
    public int weight;
    public int cost;
    public Item(){
        Random rnd= new Random();
        weight= 1+rnd.nextInt(1000);
        cost= 1+rnd.nextInt(1000);
    }
}
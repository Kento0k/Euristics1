package Euristics;
import java.util.*;
public class Item {
    public int weight;
    public int cost;
    public Item(int Cost, int Weight){
        Random rnd= new Random();
        weight= Weight;
        cost= Cost;
    }
}

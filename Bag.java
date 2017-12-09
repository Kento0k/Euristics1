package Euristics;
import java.util.*;
public class Bag {
    public int MaxWeight;
    public Bag(){
        Random rnd= new Random();
        MaxWeight= 1+rnd.nextInt(1000);
    }
}

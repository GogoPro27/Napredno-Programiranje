package Napredno_Programiranje.FirstPartialExercises.MojDDV_15;

import java.util.ArrayList;
import java.util.List;

public class Smetka {
    private long id;
    int totalSum;
    List<Item>items;

    public Smetka(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        id = Long.parseLong(parts[0]);
        items = new ArrayList<>();
        totalSum = 0;
        for(int i=1;i<parts.length;i+=2){
            TYPE t ;
            if(parts[i+1].charAt(0)=='A')t=TYPE.A;
            else if(parts[i+1].charAt(0)=='B')t=TYPE.B;
            else t=TYPE.V;
            int cenaCurr=Integer.parseInt(parts[i]);
            totalSum+=cenaCurr;
            items.add(new Item(cenaCurr,t));
        }
        if (totalSum>30000)throw new AmountNotAllowedException(totalSum);
    }

    public long getId() {
        return id;
    }

    public int getTotalSum() {
        return totalSum;
    }
    public double totalTax(){
        return items.stream().mapToDouble(Item::dodadenaVrednost).sum()*0.15;
    }
    @Override
    public String toString() {
       return String.format("%d %d %.2f\n",id,totalSum,totalTax());
    }
}

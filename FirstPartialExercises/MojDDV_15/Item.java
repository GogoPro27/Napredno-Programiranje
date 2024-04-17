package Napredno_Programiranje.FirstPartialExercises.MojDDV_15;

public class Item {
    private int price;
    private TYPE tip;

    public Item(int price, TYPE tip) {
        this.price = price;
        this.tip = tip;
    }

    public int getPrice() {
        return price;
    }

    public TYPE getTip() {
        return tip;
    }
    public double dodadenaVrednost(){
        double multiply;
        if(tip==TYPE.A)multiply=0.18;
        else if(tip==TYPE.B)multiply=0.05;
        else multiply=0;
        return price*multiply;
    }
}

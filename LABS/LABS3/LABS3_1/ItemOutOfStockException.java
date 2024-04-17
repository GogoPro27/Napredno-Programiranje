package Napredno_Programiranje.LABS.LABS3.LABS3_1;

public class ItemOutOfStockException extends Exception{
    Item item;
    public ItemOutOfStockException(Item item) {
        this.item = item;
    }
    public void message(){
        System.out.println("nz");
    }
}

package Napredno_Programiranje.LABS.LABS3.LABS3_1;

public class Product {
    private final Item item;
    private int count ;

    public Product(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Item getItem() {
        return item;
    }
}

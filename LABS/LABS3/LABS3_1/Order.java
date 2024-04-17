package Napredno_Programiranje.LABS.LABS3.LABS3_1;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Product> products;
    private boolean isLocked;

    public Order() {
        products = new ArrayList<>();
        isLocked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(isLocked)throw new OrderLockedException("OrderLockedException");
        if (count > 10) throw new ItemOutOfStockException(item);
//        if (item instanceof PizzaItem){
//            items.addLast(item);
////           ((PizzaItem) item).setCount(count);
//        }else {
//            items.addLast(item);
////            ((ExtraItem) item).setCount(count);
//        } TOA BESE EDNA IDEJA AMA BOLJE E DA SWE NAPRAVI NOVA KLASA PRODUKT..,.
//        totalPrice+=item.getPrice()*count;
        for (Product p : products) {
            if (p.getItem().equals(item)) {
                p.setCount(count);
                return;
            }
        }
        Product product = new Product(item, count);
        products.add(product);

    }
    public int getPrice(){ //streams posle !!!
//        int price = 0;
//        for(Product p : products){
//            price += p.getItem().getPrice() * p.getCount();
//        }
//        return price;
        return products.stream().mapToInt(product -> product.getCount()*product.getItem().getPrice()).sum();
    }

    public void displayOrder(){
        StringBuilder sb = new StringBuilder();
//        for(int i =0;i<products.size();i++){  // nejasno pecatenjevo
//            sb.append(String.format("%3d.%-15sx%2d%5d$%n",i+1,products.get(i).getItem().getType(),products.get(i).getCount(), products.get(i).getCount() * products.get(i).getItem().getPrice()));
//        }
        products.stream().forEach(product -> sb.append(String.format("%3d.%-15sx%2d%5d$%n",products.indexOf(product)+1,product.getItem().getType(),product.getCount(),product.getCount()*product.getItem().getPrice())));
        sb.append(String.format("%-22s%5d$", "Total:", getPrice()));
        System.out.println(sb.toString());
    }
    public void removeItem(int idx) throws ArrayIndexOutOfBoundsException, OrderLockedException {
        if(idx >= products.size()) throw new ArrayIndexOutOfBoundsException(idx);
        if(isLocked)throw new OrderLockedException("OrderLockedException");
        products.remove(idx);
    }
    public void lock() throws EmptyOrder {
        if(products.isEmpty())throw new EmptyOrder("EmptyOrder");
        isLocked = true;
    }
}
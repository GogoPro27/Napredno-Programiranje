package Napredno_Programiranje.FirstPartialExercises.ShoppingCart_25;
//lesna kkako 23 ta isto bukv
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class WS_stavka extends Stavka{
    private int quantity;

    public WS_stavka(String productId, String productName, int price, int quantity) throws InvalidOperationException {
        super(productId, productName, price);
        if (quantity==0){
            String message = "The quantity of the product with id "+productId+" can not be 0.";
            throw new InvalidOperationException(message);
        }
        this.quantity = quantity;
    }

    @Override
    public double totalPrice() {
        return quantity * getPrice();
    }
}
class PS_stavka extends Stavka{
    private double quantity;

    public PS_stavka(String productId, String productName, int price, double quantity) throws InvalidOperationException {
        super(productId, productName, price);
        if (quantity==0){
            String message = "The quantity of the product with id "+productId+" can not be 0.";
            throw new InvalidOperationException(message);
        }
        this.quantity = quantity;
    }

    @Override
    public double totalPrice() {
        return quantity * (double) getPrice()/1000;
    }
}
abstract class Stavka implements Comparable<Stavka> {
    private String productId;
    private String productName;
    private int price;
    //private int quantity;


    public Stavka(String productId, String productName, int price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
    public abstract double totalPrice();

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
         return String.format("%s - %.2f", productId , totalPrice());
    }

    @Override
    public int compareTo(Stavka o) {
        return (int) (totalPrice() - o.totalPrice());
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class ShoppingCart{
    private List<Stavka> stavki;

    public ShoppingCart() {
        stavki = new ArrayList<>();
    }
    public void addItem(String itemData) throws InvalidOperationException {
        String [] parts = itemData.split(";");

        if(parts[0].equals("WS")){
            stavki.add(new WS_stavka(parts[1],parts[2],Integer.parseInt(parts[3]),Integer.parseInt(parts[4])));
        }else {
            stavki.add(new PS_stavka(parts[1],parts[2],Integer.parseInt(parts[3]),Double.parseDouble(parts[4])));
        }
    }
    public void printShoppingCart(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        stavki.stream().sorted(Comparator.reverseOrder()).forEach(pw::println);
        pw.flush();
        pw.close();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        if(discountItems.isEmpty())throw new InvalidOperationException("There are no products with discount.");
        for (int i = 0; i < stavki.size(); i++) {
            for (int i1 = 0; i1 < discountItems.size(); i1++) {
                if(stavki.get(i).getProductId().equals(discountItems.get(i1).toString())){
                    System.out.println(String.format("%s - %.2f",stavki.get(i).getProductId(),stavki.get(i).totalPrice()-stavki.get(i).totalPrice()*0.9));
                    stavki.get(i).setPrice((int) (stavki.get(i).getPrice()*0.9));
                }
            }
        }
    }
}


public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

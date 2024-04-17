package Napredno_Programiranje.SecondPartialExercises.kol_3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::print);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::print);
    }
}

// Vashiot kod ovde

class Discounts{
    private List<Store>stores;

    public Discounts() {
        stores = new ArrayList<>();
    }
    public int readStores(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        stores = br.lines()
                .map(Store::generateStoreFromString)
                .collect(Collectors.toList());
        return stores.size();
    }
    public List<Store> byAverageDiscount(){
        return stores.stream()
                .sorted(Store.STORE_COMPARATOR1)
                .limit(3)
                .collect(Collectors.toList());
    }
    public List<Store> byTotalDiscount(){
        return stores.stream()
                .sorted(Store.STORE_COMPARATOR2.reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
class Store{
    private String name;
    private Set<Product>products;
    public static final Comparator<Store> STORE_COMPARATOR1 =
            Comparator.comparing(Store::averageDiscountPercentage).reversed()
            .thenComparing(Store::getName);
    public static final Comparator<Store> STORE_COMPARATOR2 =
            Comparator.comparing(Store::sumOfAbsoluteDiscounts).reversed()
                    .thenComparing(Store::getName);

    public Store(String name, Set<Product> products) {
        this.name = name;
        this.products = products;
    }
    public static Store generateStoreFromString(String line){
        String[] parts = line.split("\\s+");
        String name = parts[0];
        TreeSet<Product> treeSet = Arrays.stream(parts)
                .skip(1)
                .map(Product::generateProductFromString)
                .collect(Collectors.toCollection(()->new TreeSet<Product>(Product.PRODUCT_COMPARATOR)));
        return new Store(name,treeSet);
    }
    public float averageDiscountPercentage(){
        return (float) products.stream()
                .mapToInt(Product::percentDiscount)
                .average().orElse(0.0);
    }
    public int sumOfAbsoluteDiscounts(){
        return  products.stream()
                .mapToInt(Product::absoluteDiscount)
                .sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n");
        stringBuilder.append(String.format("Average discount: %.1f%%\n",averageDiscountPercentage()));
        stringBuilder.append(String.format("Total discount: %d\n",sumOfAbsoluteDiscounts()));
        products.forEach(p->stringBuilder.append(p).append("\n"));
        return stringBuilder.toString();
    }
}
class Product{
    private int discountedPrice;
    private int price;
    public static final Comparator<Product> PRODUCT_COMPARATOR =
            Comparator.comparing(Product::percentDiscount)
            .thenComparing(Product::getDiscountedPrice).reversed();


    public Product(int discountedPrice, int price) {
        this.discountedPrice = discountedPrice;
        this.price = price;
    }
    public static Product generateProductFromString(String line){
        String [] parts = line.split(":");
        return new Product(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
    }
    public int percentDiscount(){
        return (int)(100-((float) discountedPrice/price*100));
    }
    public int absoluteDiscount(){
        return price-discountedPrice;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d",percentDiscount(),discountedPrice,price);
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }
}
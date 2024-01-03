package SecondPartialExcercises.kol_25;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}
class ComparatorGenerator{
    public static Comparator<Product> productComparator(COMPARATOR_TYPE type){
        Comparator<Product> comparator ;
        if (type == COMPARATOR_TYPE.OLDEST_FIRST){
            comparator = Comparator.comparing(Product::getCreatedAt);
        }else if (type == COMPARATOR_TYPE.NEWEST_FIRST){
            comparator = productComparator(COMPARATOR_TYPE.OLDEST_FIRST).reversed();
        }else if (type == COMPARATOR_TYPE.LOWEST_PRICE_FIRST){
            comparator = Comparator.comparing(Product::getPrice);
        }else if (type == COMPARATOR_TYPE.HIGHEST_PRICE_FIRST){
            comparator = productComparator(COMPARATOR_TYPE.LOWEST_PRICE_FIRST).reversed();
        }else if (type == COMPARATOR_TYPE.LEAST_SOLD_FIRST){
            comparator = Comparator.comparing(Product::getSoldQuantity);
        }else {
            comparator = productComparator(COMPARATOR_TYPE.LEAST_SOLD_FIRST).reversed();
        }
        return comparator;
    }
}


class Product {
    private String category;
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private double price;
    private int soldQuantity;

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        soldQuantity = 0;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }
    public void sellItems(int quantity){
        this.soldQuantity += quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + String.format("%.2f",price) +
                ", quantitySold=" + soldQuantity +
                '}';
    }
}


class OnlineShop {
private Map<String ,Product> idToProductMap;
private Map<String ,List<Product>> categoryToProductsMap;

    OnlineShop() {
        idToProductMap = new HashMap<>();
        categoryToProductsMap = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
        Product newProduct = new Product(category,id,name,createdAt,price);
        idToProductMap.put(id,newProduct);
        categoryToProductsMap.putIfAbsent(category,new ArrayList<>());
        categoryToProductsMap.get(category).add(newProduct);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{
        if (!idToProductMap.containsKey(id))
            throw new ProductNotFoundException("Product with id "+id+" does not exist in the online shop!");
        idToProductMap.get(id).sellItems(quantity);
        return idToProductMap.get(id).getPrice()*quantity;
        //return 0.0;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        AtomicInteger counter = new AtomicInteger();
        if (category!=null){
            categoryToProductsMap.get(category).stream()
                    .sorted(ComparatorGenerator.productComparator(comparatorType))
                    .forEach(product -> {
                            if (result.get(0).size()==pageSize){
                                result.add(new ArrayList<>());
                                counter.getAndIncrement();
                            }
                            result.get(counter.intValue()).add(product);
                    });
        }else {
            idToProductMap.values().stream()
                    .sorted(ComparatorGenerator.productComparator(comparatorType))
                    .forEach(product -> {
                        if (result.get(counter.intValue()).size()==pageSize){
                            result.add(new ArrayList<>());
                            counter.getAndIncrement();
                        }
                        result.get(counter.intValue()).add(product);
                    });
        }
        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}



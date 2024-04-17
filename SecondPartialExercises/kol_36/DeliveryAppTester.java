package Napredno_Programiranje.SecondPartialExercises.kol_36;

import java.util.*;

/*
YOUR CODE HERE
DO NOT MODIFY THE interfaces and classes below!!!
*/

interface Location {
    int getX();

    int getY();

    default int distance(Location other) {
        int xDiff = Math.abs(getX() - other.getX());
        int yDiff = Math.abs(getY() - other.getY());
        return xDiff + yDiff;
    }
}

class LocationCreator {
    public static Location create(int x, int y) {

        return new Location() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
    }
}

public class DeliveryAppTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String appName = sc.nextLine();
        DeliveryApp app = new DeliveryApp(appName);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("addUser")) {
                String id = parts[1];
                String name = parts[2];
                app.addUser(id, name);
            } else if (parts[0].equals("registerDeliveryPerson")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addRestaurant")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addRestaurant(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addAddress")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addAddress(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("orderFood")) {
                String userId = parts[1];
                String userAddressName = parts[2];
                String restaurantId = parts[3];
                float cost = Float.parseFloat(parts[4]);
                app.orderFood(userId, userAddressName, restaurantId, cost);
            } else if (parts[0].equals("printUsers")) {
                app.printUsers();
            } else if (parts[0].equals("printRestaurants")) {
                app.printRestaurants();
            } else {
                app.printDeliveryPeople();
            }

        }
    }
}
class DeliveryApp{
    String name;
    Map<String , UserPerson> users;
    Map<String , DeliveryPerson> deliveryPeople;
    Map<String , Restaurant> restaurants;


    public DeliveryApp(String name) {
        this.name = name;
        users = new HashMap<>();
        restaurants = new HashMap<>();
        deliveryPeople = new HashMap<>();
    }

    public void registerDeliveryPerson (String id, String name, Location currentLocation){
        deliveryPeople.put(id,new DeliveryPerson(id,name,currentLocation));
    }
    public void addRestaurant (String id, String name, Location location){
        restaurants.put(id,new Restaurant(id,name,location));
    }
    public void addUser (String id, String name){
        users.put(id,new UserPerson(id,name));
    }
    public void addAddress (String id, String addressName, Location location){
        users.get(id).addAddress(addressName,location);
    }
    public void orderFood(String userId, String userAddressName, String restaurantId, float cost){
        Location restaurantLocation = restaurants.get(restaurantId).location;
        Location userLocation = users.get(userId).addresses.get(userAddressName);
        Comparator<DeliveryPerson> comparator = Comparator.comparing(dp->dp.distance(restaurantLocation));
        DeliveryPerson the_one = deliveryPeople.values().stream()
                .sorted(comparator.thenComparing(DeliveryPerson::getNumOrders))
                .findFirst().orElseThrow(RuntimeException::new);
        the_one.addOrder(restaurantLocation,userLocation);
        restaurants.get(restaurantId).addOrder(cost);
        users.get(userId).addAmount(cost);
    }
    public void printUsers(){
        users.values().stream()
                .sorted(Comparator.comparing(UserPerson::totalSpent).thenComparing(up->up.name).reversed())
                .forEach(System.out::println);
    }
    public void printRestaurants(){
        restaurants.values().stream()
                .sorted(Comparator.comparing(Restaurant::getAvgPrice).thenComparing(up->up.name).reversed())
                .forEach(System.out::println);
    }
    public void printDeliveryPeople(){
        deliveryPeople.values().stream()
                .sorted(Comparator.comparing(DeliveryPerson::getSum).thenComparing(up->up.name).reversed())
                .forEach(i-> System.out.println(i));
    }
}
abstract class Obj{
    String id;
    String name;

    public Obj(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
class DeliveryPerson extends Obj{
    Location currentLocation;
    List<Double> orders;

    public DeliveryPerson(String id, String name, Location currentLocation) {
        super(id, name);
        this.currentLocation = currentLocation;
        orders = new ArrayList<>();
    }
    public void addOrder(Location rest , Location user){
        int distance = rest.distance(user);
        double profit = 90 + ((int)( distance / 10))*10;

        orders.add(profit);
        this.currentLocation = user;
    }
    public int distance(Location l){
        return l.distance(currentLocation);
    }

    public int getNumOrders() {
        return orders.size();
    }
    public double getSum(){
        return orders.stream().mapToDouble(i->i).sum();
    }
    public double getAvg(){
        return orders.stream().mapToDouble(i->i).average().orElse(0.0);
    }
    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total deliveries: %d Total delivery fee: %.2f Average delivery fee: %.2f",id,name,getNumOrders(),getSum(),getAvg());
    }
}
class UserPerson extends Obj{
    Map<String , Location> addresses;
    List<Double> expenses;

    public UserPerson(String id, String name) {
        super(id, name);
        addresses = new HashMap<>();
        expenses = new ArrayList<>();
    }
    public double totalSpent(){
        return expenses.stream().mapToDouble(i->i).average().orElse(0);
    }
    public void addAddress(String name,Location address) {
        addresses.put(name,address);
    }
    public void addAmount(double amount){
        expenses.add(amount);
    }
    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount spent: %.2f Average amount spent: %.2f",id,name,expenses.size(),expenses.stream().mapToDouble(i->i).sum(),totalSpent());
    }
}
class Restaurant extends Obj{
    Location location;
    List<Double> orders;
    public Restaurant(String id, String name, Location location) {
        super(id, name);
        this.location = location;
        orders = new ArrayList<>();
    }
    public double getAvgPrice(){
        return orders.stream().mapToDouble(i->i).average().orElse(0.0);
    }
    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount earned: %.2f Average amount earned: %.2f",id,name,orders.size(),orders.stream().mapToDouble(i->i).sum(),getAvgPrice());
    }
    public void addOrder(double money){
        orders.add(money);
    }
}














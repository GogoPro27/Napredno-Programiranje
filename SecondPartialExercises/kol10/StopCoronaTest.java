package Napredno_Programiranje.SecondPartialExercises.kol10;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

interface ILocation{
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}
class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
class StopCoronaApp{
    Map<String , User> users;
    public StopCoronaApp() {
        users = new HashMap<>();
    }
    public void addUser(String name, String id) throws UserAlreadyExistException {
        if (users.containsKey(id))throw new UserAlreadyExistException("Postoi");
        users.put(id,new User(name,id));
    }
    public void addLocations (String id, List<ILocation> iLocations){
        users.get(id).addLocations(iLocations);
    }
    public void detectNewCase (String id, LocalDateTime timestamp){
        users.get(id).registerSick(timestamp);
    }
    public  Map<User, Integer> getDirectContacts (User u){
        Map<User, Integer> newMap = new LinkedHashMap<>();
        Comparator<User> comparator = Comparator.comparing(user->user.closeContacts(u));
        comparator = comparator.reversed();
        users.values().stream()
                .filter(user->!user.equals(u))
                .sorted(comparator)
                .forEach(user->{
                    int contacts = u.closeContacts(user);
                    if (contacts != 0){
                        newMap.put(user,contacts);
                    }
                });

        return newMap;
    }
    public Collection<User> getIndirectContacts (User u){
        List<User> direct = getDirectContacts(u).keySet().stream().collect(Collectors.toList());
        return getDirectContacts(u).keySet()
                .stream().map(this::getDirectContacts)
                .flatMap(map->map.keySet().stream())
                .filter(user -> !direct.contains(user))
                .sorted(User.comparator.thenComparing(user -> user.id))
                .collect(Collectors.toList());
    }
    public void createReport(){
        users.values().stream()
                .sorted(Comparator.comparing(u->u.timestamp))
                .filter(user->user.timestamp!=LocalDateTime.MAX)
                .forEach(user->{
                    System.out.println(String.format("%s %s %s",user.name,user.id,user.timestamp));
                    System.out.println("Direct contacts:");
                    getDirectContacts(user).keySet().stream()
                            .forEach(u2-> System.out.println(String.format("%s %s %d",u2.name,u2.id.substring(0,6),u2.closeContacts(user))));
                    getIndirectContacts(user).stream()
                            .forEach(u3->System.out.println(String.format("%s %s",u3.name,u3.id.substring(0,6))));

                });
    }
}
class User{
    String name;
    String id;
    List<ILocation> iLocations;
    boolean isSick;
    LocalDateTime timestamp;
    static Comparator <User> comparator = Comparator.comparing(user -> user.name);

    public User(String name,String id) {
        this.name = name;
        this.id = id;
        iLocations = new ArrayList<>();
        isSick = false;
        timestamp = LocalDateTime.MAX;
    }
    public void addLocations(List<ILocation> iLocations){
        this.iLocations.addAll(iLocations);
    }
    public void registerSick(LocalDateTime localDateTime){
        timestamp = localDateTime;
        isSick  = true;
    }
    public int closeContacts(User other){
        int counter = 0;
        for (ILocation thisLocations : iLocations) {
            for (ILocation otherLocations : other.iLocations) {
                double dist = Math.sqrt(Math.pow(thisLocations.getLatitude() - otherLocations.getLatitude(),2)+Math.pow(thisLocations.getLongitude() - otherLocations.getLongitude(),2));
                Duration duration = Duration.between(thisLocations.getTimestamp(),otherLocations.getTimestamp());
                if (dist<=2 && duration.getSeconds()<300)counter++;
            }
        }
        return counter;
    }
}
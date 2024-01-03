package SecondPartialExcercises.kol_10SVEUPICKA;
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
class StopCoronaApp{
    private static Map<String,User> idToUserMap;
    private TreeSet<User> sickUsers;

    public StopCoronaApp() {
        idToUserMap = new HashMap<>();
        sickUsers = new TreeSet<>(Comparator.comparing(User::getLocalDateTimeRegistered));
    }
    public void addUser(String name, String id) throws UserAlreadyExistException {
        if (idToUserMap.containsKey(id))throw new UserAlreadyExistException("UserIdAlreadyExistsException");
        idToUserMap.put(id,new User(id,name));
    }
    public void addLocations (String id, List<ILocation> iLocations){
        idToUserMap.get(id).addLocations(iLocations);
    }
    public void detectNewCase (String id, LocalDateTime timestamp){
        idToUserMap.get(id).setLocalDateTimeRegistered(timestamp);
        sickUsers.add(idToUserMap.get(id));
    }
    public static Map<User, Integer> getDirectContacts(User u){
         return idToUserMap.values().stream()
                .filter(u::wasCloseEnough)
                 .collect(Collectors.toMap(
                            user -> user,
                            user -> user.wasCloseEnoughTimes(u)
                 ));
    }
    public static Collection<User> getIndirectContacts(User u){
        return getDirectContacts(u).keySet().stream()
                .map(StopCoronaApp::getDirectContacts)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .filter(user -> !getIndirectContacts(u).contains(user))
                .collect(Collectors.toList());
    }
    public void createReport (){
        for (User user : sickUsers) {
                    System.out.println(user);
                    System.out.println("Direct contacts:");
                    long countDirect = StopCoronaApp.getDirectContacts(user).size();

                    StopCoronaApp.getDirectContacts(user).entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEach(userIntegerEntry -> {
                                String name = userIntegerEntry.getKey().getName();
                                String firstFiveLettersId = userIntegerEntry.getKey().getId().substring(0,6);
                                int n = userIntegerEntry.getValue();
                                System.out.println(String.format("%s %s %d",name,firstFiveLettersId,n));
                            });
                    System.out.println(String.format("Count of direct contacts: %d",countDirect));

                    System.out.println("Indirect contacts:");
                    long countInDirect = StopCoronaApp.getIndirectContacts(user).size();

                    StopCoronaApp.getIndirectContacts(user).stream()
                            .sorted(Comparator.comparing(User::getName).thenComparing(User::getId))
                            .forEach(u -> {
                                String name = u.getName();
                                String firstFiveLettersId = u.getId() ;
                                System.out.println(String.format("%s %s",name,firstFiveLettersId));
                            });
                    System.out.println(String.format("Count of indirect contacts: %d",countInDirect));

        }
    }
}
class User{
    private String id;
    private String name;
    private boolean isSick;
    LocalDateTime localDateTimeRegistered;
    List<ILocation>locations;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        locations = new ArrayList<>();
        isSick = false;

    }

    public void addLocations(List<ILocation> locations) {
        this.locations.addAll(locations);
    }

    public void setLocalDateTimeRegistered(LocalDateTime localDateTimeRegistered) {
        this.localDateTimeRegistered = localDateTimeRegistered;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSick() {
        return isSick;
    }

    public LocalDateTime getLocalDateTimeRegistered() {
        return localDateTimeRegistered;
    }

    public List<ILocation> getLocations() {
        return locations;
    }
    public boolean wasCloseEnough(User other){
        if (wasCloseEnoughTimes(other)!=0)return true;
        return false;
    }
    public int wasCloseEnoughTimes(User other){
        int counter = 0;
        List<ILocation> locations = other.getLocations();
        for (int i = 0; i < this.locations.size(); i++) {
            for (int i1 = 0; i1 < locations.size(); i1++) {
                double q1 = this.locations.get(i).getLatitude();
                double q2 = this.locations.get(i).getLongitude();
                double p1 = locations.get(i1).getLatitude();
                double p2 = locations.get(i1).getLongitude();
                double euclideanDistance = Math.sqrt(Math.pow(q1 - p1, 2) + Math.pow(q2 - p2, 2));
                Duration duration = Duration.between(this.locations.get(i).getTimestamp(),locations.get(i1).getTimestamp());
                if (euclideanDistance<=2 && duration.getSeconds()<=5*60)counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",name,id,localDateTimeRegistered);
    }
}
class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

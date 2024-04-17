package SecondPartialExcercises.kol_15;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde
class Airports{
    public Map<String,Airport> codeToAirport;

    public Airports() {
        codeToAirport = new HashMap<>();
    }
    public void addAirport(String name, String country, String code, int passengers){
        Airport newAirport = new Airport(name,country,code,passengers);
        codeToAirport.put(code,newAirport);
    }

    public void addFlights(String from, String to, int time, int duration){
        Flight newFlight = new Flight(from,to,time,duration);
        codeToAirport.get(from).addDepartingFlight(newFlight);
        codeToAirport.get(to).addArrivingFlight(newFlight);
    }
    public void showFlightsFromAirport(String code){
        Airport curr = codeToAirport.get(code);
        System.out.println(curr);
        AtomicInteger ctr= new AtomicInteger(1);
        curr.getDepartingFlights().stream()
                .forEach(f-> System.out.println( ctr.getAndIncrement()+". "+f));
    }
    public void showDirectFlightsFromTo(String from, String to){

        Airport curr = codeToAirport.get(from);
        Optional<Flight> opt = curr.getDepartingFlights().stream()
                .filter(f->f.getTo().equals(to))
                .peek(System.out::println)
                .findAny();
        if (opt.isEmpty()){
            System.out.printf("No flights from %s to %s\n",from,to);
        }
    }
    public void showDirectFlightsTo(String to){
         codeToAirport.values().stream()
                .map(Airport::getArrivingFlights)
                 .flatMap(Collection::stream)
                 .filter(flight -> flight.getTo().equals(to))
                 .forEach(System.out::println);

    }
}
class Airport{
    private String name;
    private String country;
    private String code;
    private int passengers;
    private Set<Flight> departingFlights;
    private Set<Flight> arrivingFlights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        departingFlights = new TreeSet<>(Flight.FLIGHT_COMPARATOR);
        arrivingFlights = new TreeSet<>(Flight.FLIGHT_COMPARATOR);
    }
    public void addDepartingFlight(Flight flight){
        departingFlights.add(flight);
    }
    public void addArrivingFlight(Flight flight){
        arrivingFlights.add(flight);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public Set<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public Set<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d",name,code,country,passengers);
    }
}
class Flight{
    private String from;
    private String to;
    private LocalTime time;
    private int duration;
    public static final Comparator<Flight> FLIGHT_COMPARATOR =
            Comparator.comparing(Flight::getTo)
                    .thenComparing(Flight::getTime)
                    .thenComparing(Flight::getDuration);

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = LocalTime.of(0,0,0);
        setTime(time);
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    private void setTime(int time){
        this.time = this.time.plusMinutes(time);
    }

    @Override
    public String toString() {
        int h = duration/60;
        int m = duration - (h*60);
        String novden = "";
        if ((time.getHour()*60+time.getMinute()+duration)>=60*24){
            novden = "+1d ";
        }
        return String.format("%s-%s %s-%s %s%dh%02dm",
                from, to, time,time.plusMinutes(duration),novden, h,m );
    }
}

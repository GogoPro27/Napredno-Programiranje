package SecondPartialExcercises.kol_22;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

class WrongDateException extends Exception{
    public WrongDateException(String message) {
        super(message);
    }
}
public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            LocalDateTime date = LocalDateTime.parse(parts[2],dtf);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        String nl = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(nl,dtf);
//        LocalDateTime date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
class EventCalendar{
    private int year;
    Map<LocalDate, Set<Event>> dateTimeSetMap;
    Map<Integer, Integer> monthToNumEvents;


    public EventCalendar(int year) {
        this.year = year;
        dateTimeSetMap = new HashMap<>();
        monthToNumEvents = new TreeMap<>();
        IntStream.range(1,13)
                .forEach(i->monthToNumEvents.put(i,0));
    }

    public void addEvent(String name, String location, LocalDateTime date) throws WrongDateException {
        if (date.getYear()!=year)throw new WrongDateException(String.format("Wrong date: %s",date.format(DateTimeFormatter.ofPattern("eee MMM dd HH:mm:ss 'UTC' yyyy"))));
        Event newEvent = new Event(name,location,date);
        dateTimeSetMap.putIfAbsent(getDateFromLDT(date),new TreeSet<>());
        dateTimeSetMap.get(getDateFromLDT(date)).add(newEvent);
        monthToNumEvents.computeIfPresent(date.getMonth().getValue(),(k,v)->++v);
    }
    public void listEvents(LocalDateTime date){
        if (!dateTimeSetMap.containsKey(getDateFromLDT(date))){
            System.out.println("No events on this day!");
            return;
        }
       dateTimeSetMap.get(getDateFromLDT(date)).forEach(System.out::println);
    }
    public void listByMonth(){
        monthToNumEvents.entrySet()
                .forEach(integerIntegerEntry ->
                        System.out.printf("%d : %d%n",integerIntegerEntry.getKey(),integerIntegerEntry.getValue()));
    }
    public LocalDate getDateFromLDT(LocalDateTime LDT){
        return LocalDate.of(LDT.getYear(),LDT.getMonth(),LDT.getDayOfMonth());
    }
}
class Event implements Comparable<Event> {
    private String name;
    private String location;
    LocalDateTime localDateTime;
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, yyy HH:mm");

    public Event(String name, String location, LocalDateTime localDateTime) {
        this.name = name;
        this.location = location;
        this.localDateTime = localDateTime;
    }

    @Override
    public int compareTo(Event o) {
        return Comparator.comparing(Event::getLocalDateTime)
                .thenComparing(Event::getName).compare(this,o);
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s",localDateTime.format(dtf),location,name);
    }
}
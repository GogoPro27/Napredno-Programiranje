package SecondPartialExcercises.kol_1;

import java.util.*;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
class Audition{
    Map<String,TreeSet<Participant>> map;
    Map<String,List<String>> listMap;

    private static final Comparator<Participant> COMPARATOR =Comparator.comparing(Participant::getName)
            .thenComparing(Participant::getAge)
            .thenComparing(p->p.code);

    public Audition() {
        this.map = new HashMap<>();
        listMap = new HashMap<>();
    }
    public void addParticpant(String city, String code, String name, int age){
        Participant participant = new Participant(city, code, name, age);
        map.putIfAbsent(city,new TreeSet<>(COMPARATOR));
        listMap.putIfAbsent(city,new ArrayList<>());

        if (!listMap.get(city).contains(code))
            map.get(city).add(participant);

        listMap.get(city).add(code);
    }
    public void listByCity(String city){
        map.get(city).forEach(
                System.out::println
        );
    }

}
class Participant{
    String city;
    String code;
    String name;
    int age;


    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d",code,name,age);
    }
}
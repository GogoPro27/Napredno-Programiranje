package SecondPartialExcercises.kol27;


import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
class Names{
    Map<String, Integer> map;

    public Names() {
        map = new TreeMap<>();
    }

    public void addName (String name){
        map.computeIfPresent(name,(k,v) -> ++v);
        map.putIfAbsent(name,1);
    }
    public int uniqueLetters(String s){
        return (int) s.toLowerCase().chars().distinct().count();
    }
    public void printN(int n){
        map.entrySet().stream()
                .filter(es->es.getValue()>=n)
                .forEach(es->{
                    System.out.println(String.format("%s (%d) %d",es.getKey(),es.getValue(),uniqueLetters(es.getKey())));
                });
    }
    public String findName(int len, int x){
        List<String> list= map.keySet().stream()
                .filter(k->k.length()<len)
                .collect(Collectors.toList());
        return list.get(x%list.size());
    }
}
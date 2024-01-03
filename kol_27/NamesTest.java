package SecondPartialExcercises.kol_27;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
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
    private Map<String,Integer> namesMap;

    public Names() {
        namesMap = new TreeMap<>();
    }

    public void addName(String name){
        namesMap.putIfAbsent(name,0);
        namesMap.computeIfPresent(name,(k,v)->++v);
    }
    public void printN(int n){
        namesMap.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue()>=n)
                .forEach(stringIntegerEntry -> {
                    long uniqueLetters = stringIntegerEntry.getKey().chars()
                            .map(i->{
                                if (i<(int)'a')
                                    return i+32;
                                return i;
                            })
                            .distinct()
                            .count();
                    System.out.println(String.format("%s (%d) %d",
                            stringIntegerEntry.getKey(),
                            stringIntegerEntry.getValue(),
                            uniqueLetters));
                });
    }
    public String findName(int len, int x){
        List<String> uniqueNames = namesMap.keySet().stream().collect(Collectors.toList());
        uniqueNames = uniqueNames.stream().filter(uniqueName->uniqueName.length()<len).collect(Collectors.toList());
        int i=x;
        if (x>uniqueNames.size()) {
             i = x % uniqueNames.size();
        }
        return uniqueNames.get(i);
    }
}
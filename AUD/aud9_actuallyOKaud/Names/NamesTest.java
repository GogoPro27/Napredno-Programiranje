package Napredno_Programiranje.AUD.aud9_actuallyOKaud.Names;

import java.io.*;
import java.util.*;



public class NamesTest {
    public static void main(String[] args) {
        try {

            Map<String, Integer> boynames = generateMap(new FileInputStream("/Users/gorazd/IdeaProjects/Napredno Programiranje prv/src/Napredno_Programiranje.AUD/aud9_actuallyOKaud/data/boysnames.txt"));
            Map<String, Integer> girlnames = generateMap(new FileInputStream("/Users/gorazd/IdeaProjects/Napredno Programiranje prv/src/Napredno_Programiranje.AUD/aud9_actuallyOKaud/data/girlsnames.txt"));

            Set<String> setUnique = new HashSet<>(boynames.keySet());
            setUnique.retainAll(girlnames.keySet());

            Map<String, Integer> uniqueNames = new TreeMap<>();
            setUnique.forEach(i -> uniqueNames.put(i, boynames.get(i) + girlnames.get(i)));

            //printed lexicographski
            System.out.println(uniqueNames);

            //printed sorted by value
            uniqueNames.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.printf("(%s -> %d) ", entry.getKey(), entry.getValue()));


        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }

    public static Map<String, Integer> generateMap(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        Map<String, Integer> map = new HashMap<>();
        br.lines().forEach(
                line -> {
                    String[] parts = line.split("\\s+");
                    map.put(parts[0], Integer.parseInt(parts[1]));
                }
        );
        return map;
    }
}

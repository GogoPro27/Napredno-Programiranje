package Napredno_Programiranje.LABS.LABS7;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        // Vasiod kod ovde
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        Map<String, List<String>> map = new TreeMap<>();
        br.lines().forEach(line-> {
            map.putIfAbsent(wordToKey(line), new LinkedList<>());
            map.get(wordToKey(line)).add(line);
        });
        map.entrySet().stream()
                .filter(es->es.getValue().size()>=5)
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(es->es.get(0))))
                .forEach(es->{
                    System.out.println(String.join(" ", es.getValue()));
                });

    }
    public static String wordToKey(String word){
        return word.chars().sorted()
                .mapToObj(i->(char)i)
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }
}

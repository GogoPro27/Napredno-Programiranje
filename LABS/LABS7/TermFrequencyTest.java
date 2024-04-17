package Napredno_Programiranje.LABS.LABS7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
class TermFrequency{
    Map<String, Long> map;
     int total;
    public TermFrequency(InputStream inputStream, String[] stopWords){

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> words = filterText(br.lines());
        words.removeAll(Arrays.stream(stopWords).collect(Collectors.toList()));
        total = words.size();
        map = words.stream()
                        .collect(Collectors.groupingBy(
                                string -> string,
                                TreeMap::new,
                                Collectors.counting()
                        ));
        System.out.println(String.join(", ", words));
    }
    public int countTotal(){
        return total;
    }
    public int countDistinct(){
        return map.keySet().size();
    }
    public List<String> mostOften(int n){
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
//    public List<String> filterText(Stream<String> stream){
//        return stream.map(string -> {
//          return  string.chars()
//                  .mapToObj(i->(char)i)
//                  .map(character -> isLetter(character) ? character : ' ')
//                  .map(Object::toString)
//                  .collect(Collectors.joining(""));
//        }).map(string -> Arrays.stream(string.split("\\s+")).collect(Collectors.toList()))
//                .flatMap(Collection::stream)
//                .map(String::toLowerCase)
//                .filter(string -> !string.isEmpty())
//                .collect(Collectors.toList());
//    }
public List<String> filterText(Stream<String> stream){
    return stream
            .map(string -> string.replaceAll("[^А-Ша-шј0-9]+", " "))
            .map(string -> Arrays.stream(string.split("\\s+")).collect(Collectors.toList()))
            .flatMap(Collection::stream)
            .map(String::toLowerCase)
            .filter(string -> !string.equals(""))
            .collect(Collectors.toList());
}

    public boolean isLetter(char c){
        return (c >= 'А' && c <= 'Ш') || (c >= 'а' && c <= 'ш') || c=='ј' || (c>='0'&&c<='9');
    }

}
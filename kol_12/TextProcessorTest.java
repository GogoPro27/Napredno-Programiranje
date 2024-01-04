package SecondPartialExcercises.kol_12;
//zeznata

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class CosineSimilarityCalculator {
    public static double cosineSimilarity (Collection<Integer> c1, Collection<Integer> c2) {
        int [] array1;
        int [] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1=0, down2=0;

        for (int i=0;i<c1.size();i++) {
            up+=(array1[i] * array2[i]);
        }

        for (int i=0;i<c1.size();i++) {
            down1+=(array1[i]*array1[i]);
        }

        for (int i=0;i<c1.size();i++) {
            down2+=(array2[i]*array2[i]);
        }

        return up/(Math.sqrt(down1)*Math.sqrt(down2));
    }
}

public class TextProcessorTest {

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();

        textProcessor.readText(System.in);

        System.out.println("===PRINT VECTORS===");
        textProcessor.printTextsVectors(System.out);

        System.out.println("PRINT FIRST 20 WORDS SORTED ASCENDING BY FREQUENCY ");
        textProcessor.printCorpus(System.out,  20, true);

        System.out.println("PRINT FIRST 20 WORDS SORTED DESCENDING BY FREQUENCY");
        textProcessor.printCorpus(System.out, 20, false);

        System.out.println("===MOST SIMILAR TEXTS===");
        textProcessor.mostSimilarTexts(System.out);
    }
}
class TextProcessor{
public List<Sentence> sentences;
    public TextProcessor() {
        sentences = new ArrayList<>();
    }
    public void readText (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> inputs= br.lines().collect(Collectors.toList());
        Set<String>wordsSet = new TreeSet<>();
        for (String input : inputs) {
            List<String> words = TextPotter.getWords(TextPotter.filterText(input));
            wordsSet.addAll(words);
        }
        for (String input : inputs) {
            Sentence sentence = new Sentence(input,wordsSet);
            sentences.add(sentence);
        }
    }
    public void printTextsVectors (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        sentences.forEach(sentence -> pw.println(sentence.getVector()));
        pw.flush();
    }
    public void printCorpus(OutputStream os, int n, boolean ascending){
        PrintWriter pw = new PrintWriter(os);
        Map<String,Integer> map= sentences.stream()
                .map(sentence -> sentence.getWordsMap().entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ));
        Comparator<Map.Entry<String, Integer>> comparator = Comparator.<Map.Entry<String, Integer>, Integer>comparing(Map.Entry::getValue, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey);
            map.entrySet().stream()
                .sorted(comparator)
                    .limit(n)
                    .forEach(entry->pw.println(String.format("%s : %d",entry.getKey(),entry.getValue())));
        pw.flush();
    }
    public void mostSimilarTexts (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        pw.print(mostSimilar());
        pw.flush();
    }
    public String mostSimilar(){
        String list = sentences.stream()
                .flatMap(s1 -> sentences.stream().filter(s2 -> !s1.equals(s2))
                        .map(s2 -> new AbstractMap.SimpleEntry<>(s1, s2)))
                .min(Comparator.comparingDouble(entry ->
                        Math.abs(1 - CosineSimilarityCalculator.cosineSimilarity(entry.getKey().getVector(), entry.getValue().getVector()))))
                .map(entry -> {
                    Sentence first = entry.getKey();
                    Sentence second = entry.getValue();
                    double similarity = CosineSimilarityCalculator.cosineSimilarity(first.getVector(), second.getVector());
                    return String.format("%s\n%s\n%.10f", first, second, similarity);
                })
                .orElse("");
        return list;
    }
}
class TextPotter{
    public static List<String>getWords(String text){
        return Arrays.stream(text.split("\\s+"))
                .map(string -> string.toLowerCase())
                .collect(Collectors.toList());
    }
    public static String filterText(String s){
       return s.chars().filter(character->{
                        if (character>='A'&&character<='Z' || character>='a'&&character<='z' || character==' ')
                            return true;
                        return false;
                    }).mapToObj(character->(char)character)
                    .map(Object::toString)
                    .collect(Collectors.joining(""));
    }
}
class Sentence{
    private Map<String,Integer> wordsMap;
    private String sentence;

    public Sentence(String sentence,Set<String> words) {
        wordsMap = new TreeMap<>();
        List<String> myWords = TextPotter.getWords(TextPotter.filterText(sentence));
        for (String myWord : myWords) {
            wordsMap.computeIfPresent(myWord,(k,v)->++v);
            wordsMap.putIfAbsent(myWord,1);
        }
        for (String word : words) {
            wordsMap.putIfAbsent(word,0);
        }
        this.sentence = sentence;
    }
    public List<Integer> getVector(){
        return wordsMap.values().stream().collect(Collectors.toList());
    }

    public Map<String, Integer> getWordsMap() {
        return wordsMap;
    }

    @Override
    public String toString() {
        return sentence;
    }
}
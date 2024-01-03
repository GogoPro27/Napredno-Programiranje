package SecondPartialExcercises.kol_12;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//    private Set<String>wordsSet;
//    private List<Text> texts;
    private Map<Text,List<Integer>>textToVector;
    public TextProcessor() {
        textToVector = new HashMap<>();
    }
    public void readText (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> inputs= Text.filterText(br.lines());
        Set<String>wordsSet = new TreeSet<>();
        for (String input : inputs) {
            textToVector.put(new Text(input),new ArrayList<>());
            List<String> words = Text.getWords(input);
            wordsSet.addAll(words);
        }
        textToVector.keySet()
                .forEach(text -> {
                    List<Integer> occurances=new ArrayList<>();
                    wordsSet.forEach(word->{
                        occurances.add(text.occurances(word));
                    });
                    textToVector.get(text).addAll(occurances);
                });
    }
    public void printTextsVectors (OutputStream os){
        PrintWriter pw= new PrintWriter(os);
        textToVector.keySet().stream()
                        .forEach(key-> System.out.println(textToVector.get(key)));
        pw.flush();
    }
    public void printCorpus(OutputStream os, int n, boolean ascending){

    }
    public void mostSimilarTexts (OutputStream os){

    }
}
class Text{
    private String text;
    private Map<String,Integer> vector;

    public Text(String text) {
        this.text = text;
        vector = new HashMap<>();
        getWords(text).stream()
                .forEach(word->vector.put(word,0));
    }
    public List<Integer> getWordsSortedByOccurances(boolean ascending){
        return null;
    }
    public int occurances(String word){
        return (int) getWords(text).stream().filter(w->w.equals(word)).count();
    }
    public static List<String>getWords(String text){
        return Arrays.stream(text.split("\\s+"))
                .collect(Collectors.toList());
    }
    public static List<String> filterText(Stream<String> stream){
        return  stream.map(string -> {
            return string.chars().filter(character->{
                        if (character>='A'&&character<='Z' || character>='a'&&character<='z' || character==' ')
                            return true;
                        return false;
                    }).mapToObj(character->(char)character)
                    .map(Object::toString)
                    .collect(Collectors.joining(""));
        }).collect(Collectors.toList());
    }
}
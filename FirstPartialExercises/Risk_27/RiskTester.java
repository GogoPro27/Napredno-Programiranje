package Napredno_Programiranje.FirstPartialExercises.Risk_27;
//linija18 zasto nemoze mapToInt

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Partija{
     private List<Integer> napagjac;
     private List<Integer> branac;
     int preostanatiNapagjac;
     int preostanatiBranac;

    public Partija(String line) {
        String[] parts = line.split(";");
        napagjac = takeNumbers(parts[0]);
        branac = takeNumbers(parts[1]);
        preostanatiBranac = 3;
        preostanatiNapagjac = 3;
        bitka();
    }
    public void bitka(){
        for(int i=0;i<3;i++){
            if(napagjac.get(i)>branac.get(i))preostanatiBranac--;
            else preostanatiNapagjac--;
        }
    }

    public int getPreostanatiNapagjac() {
        return preostanatiNapagjac;
    }

    public int getPreostanatiBranac() {
        return preostanatiBranac;
    }

    @Override
    public String toString() {
        return preostanatiNapagjac+ " " + preostanatiBranac;
    }

    public List<Integer> takeNumbers(String s){
        return Arrays.stream(s.split("\\s++")).map(Integer::parseInt).sorted().collect(Collectors.toList());
    }
}
class Risk{
    List<Partija> partii;

    public Risk() {
        partii = new ArrayList<>();
    }

    public void processAttacksData(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = br.lines().collect(Collectors.toList());
        for (int i=0;i<lines.size();i++){
            partii.add(new Partija(lines.get(i)));
        }
        partii.stream().forEach(System.out::println);
    }
}

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}
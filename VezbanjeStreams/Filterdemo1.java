package Napredno_Programiranje.VezbanjeStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filterdemo1 {
    public static void main(String[] args) {
        List<Integer> numbersList = Arrays.asList(10,15, 20,25,30);
        List<Integer> evenNumbersList = new ArrayList<>();

//        evenNumbersList = numbersList.stream().filter(number -> number%2==0).collect(Collectors.toList());
//        System.out.println(evenNumbersList);

        numbersList.stream().filter(number -> number%2==0).forEach(number-> System.out.println(number));
        numbersList.stream().filter(number -> number%2==0).forEach(System.out::println);
        numbersList.stream().filter(number -> number%2==0).forEach(number-> number*=2); //zaso ne raboti (ne vrakja niso)


    }
}

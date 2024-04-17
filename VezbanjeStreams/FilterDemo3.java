package Napredno_Programiranje.VezbanjeStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterDemo3 {
    public static void main(String[] args) {

        List<String> words = Arrays.asList("cup", null, "forest","sky","book", null,"theatre");
        List<String> result = new ArrayList<>();

        result = words.stream().filter(n -> n!=null).collect(Collectors.toList());
        System.out.println(result);


    }
}

package Napredno_Programiranje.VezbanjeStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterDemo2 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Melisandre","Sansa","Jon","Daenerys","Joffery");

        List<String> longNames = new ArrayList<>();

//        longNames = names.stream().filter(name -> name.length()>6 && name.length()<8).collect(Collectors.toList());
//        System.out.println(longNames);

        names.stream().filter(name -> name.length()>6 && name.length()<8).forEach(name -> System.out.println(name));
    }
}

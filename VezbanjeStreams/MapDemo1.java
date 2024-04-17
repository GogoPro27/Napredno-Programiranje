package Napredno_Programiranje.VezbanjeStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapDemo1 {
    public static void main(String[] args) {
        List<String> vehicles = Arrays.asList("bus", "car","bicycle","flight","train");
        List<String> uppercaseVehicle = new ArrayList<>();
        uppercaseVehicle = vehicles.stream().map(word -> word.toUpperCase()).collect(Collectors.toList());
        System.out.println(uppercaseVehicle);

        List<Integer>niza = vehicles.stream().map(vehicle -> vehicle.length()).collect(Collectors.toList());
        System.out.println(niza);
    }

}

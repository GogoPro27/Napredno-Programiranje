package Napredno_Programiranje.AUD.audSoStefanInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BufferedReaderImplementation {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        input = bufferedReader.lines().collect(Collectors.toList());

        System.out.println(input);
    }
}

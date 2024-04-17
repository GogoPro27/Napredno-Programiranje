package Napredno_Programiranje.FirstPartialExercises.Risk_24;
//interesna zadacka, stefan gi resavase napadite kako posebna klasa "Partija" kade sto sreduvase toa so treba da sredi

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Risk {
    public Risk() {
    }
    public List<Integer> stringToSortedArray(String line){
        return Arrays.stream(line.split("\\s+")).map(Integer::parseInt).sorted().collect(Collectors.toList());
    }
    public int processAttacksData (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String>lines = new ArrayList<>();
        lines = br.lines().collect(Collectors.toList());
        int counter = 0;
        for(int i=0;i< lines.size();i++){
            String[] parts = lines.get(i).split(";");
            List<Integer>napagjac = stringToSortedArray(parts[0]);
            List<Integer>branac = stringToSortedArray(parts[1]);
            int inner=0;

            for (int j=0;j<napagjac.size();j++){
                if(napagjac.get(j)>branac.get(j)){
                    inner++;
                }
            }
            if (inner==3)counter++;
        }
        return counter;
    }
}

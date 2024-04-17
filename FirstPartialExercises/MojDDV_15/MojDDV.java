package Napredno_Programiranje.FirstPartialExercises.MojDDV_15;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MojDDV {
    List<Smetka>smetki;

    public MojDDV() {
        smetki = new ArrayList<>();
    }

    public void readRecords(InputStream inputStream) throws AmountNotAllowedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String>lines = new ArrayList<>();
        lines = br.lines().collect(Collectors.toList());

        for (String line : lines) {
            try {
                smetki.add(new Smetka(line));
            } catch (AmountNotAllowedException e) {
                e.message();
            }
        }
    }
    public void printTaxReturns(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        smetki.forEach(pw::print);
        pw.flush();
        pw.close();

    }

}

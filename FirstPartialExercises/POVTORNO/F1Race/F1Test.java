package Napredno_Programiranje.FirstPartialExercises.POVTORNO.F1Race;
//bez nieden for ciklus REREEEEEEEESHIIIIII
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class F1Driver implements Comparable<F1Driver>{
    private String name;
    private List<LocalTime> vreminja;

    public F1Driver(String line) {
        String[] parts = line.split("\\s++");
        name = parts[0];
        vreminja = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("m:ss:SSS");
        for (int i = 1; i <= 3; i++) {
//            vreminja.add(LocalTime.parse(parts[i], dtf));
            String[] partsInner = parts[i].split(":");
            int min = Integer.parseInt(partsInner[0]);
            int sec = Integer.parseInt(partsInner[1]);
            int mils = Integer.parseInt(partsInner[2]);
            vreminja.add(LocalTime.of(0,min,sec,mils*1000000));
        }
    }
    public LocalTime najdobroVreme(){
        return vreminja.stream().min(LocalTime::compareTo).get();
    }
    @Override
    public int compareTo(F1Driver o) {
        return najdobroVreme().compareTo(o.najdobroVreme());
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s",name,najdobroVreme().format(DateTimeFormatter.ofPattern("m:ss:SSS")));
    }
}
class F1Race {
    // vashiot kod ovde
    private List<F1Driver> pilots;

    public F1Race() {
        pilots = new ArrayList<>();
    }
    public void readResults(InputStream inputStream)  {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs ;
        inputs =bufferedReader.lines().collect(Collectors.toList());
        pilots = inputs.stream().map(F1Driver::new)
                .collect(Collectors.toList());

    }
    public void printSorted(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        AtomicInteger i= new AtomicInteger(1);
        pilots.stream().sorted(Comparator.naturalOrder()).forEach(pilot->pw.println(((i.getAndIncrement()))+". "+pilot));
        pw.flush();
        pw.close();
    }
}
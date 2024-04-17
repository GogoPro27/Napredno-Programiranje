package Napredno_Programiranje.FirstPartialExercises.F1Race_21;
//bez nieden for ciklus REREEEEEEEESHIIIIII
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class F1DriverTime implements Comparable<F1DriverTime>{
    private final int minutes;
    private final int seconds;
    private final int miliseconds;
    String zaPecatenje;

    public F1DriverTime(String vreme) {
        zaPecatenje = vreme;
        String [] parts = vreme.split(":");
        this.minutes = Integer.parseInt(parts[0]);
        this.seconds = Integer.parseInt(parts[1]);
        this.miliseconds = Integer.parseInt(parts[2]);
    }
    public int totalMiliseconds(){
        return miliseconds + seconds*1000 + minutes*60*1000;
    }

    @Override
    public String toString() {
        return zaPecatenje;
    }

    @Override
    public int compareTo(F1DriverTime o) {
        return totalMiliseconds()-o.totalMiliseconds();
    }
}
class F1Driver implements Comparable<F1Driver>{
    private String name;
    private List<F1DriverTime> vreminja;

    public F1Driver(String line) {
        String[] parts = line.split("\\s++");
        name = parts[0];
        vreminja = new ArrayList<>();
        IntStream.range(1,4).forEach(i->vreminja.add(new F1DriverTime(parts[i])));
    }
    public F1DriverTime najdobroVreme(){
        return vreminja.stream().max(Comparator.reverseOrder()).get();
    }
    @Override
    public int compareTo(F1Driver o) {
        return najdobroVreme().totalMiliseconds()-o.najdobroVreme().totalMiliseconds();
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s",name,najdobroVreme());
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
        IntStream.range(0,inputs.size()).forEach(i->pilots.add(new F1Driver(inputs.get(i))));

    }
    public void printSorted(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        AtomicInteger i= new AtomicInteger(1);
        pilots.stream().sorted(Comparator.naturalOrder()).forEach(pilot->pw.println(((i.getAndIncrement()))+". "+pilot));
        pw.flush();
        pw.close();
    }
}
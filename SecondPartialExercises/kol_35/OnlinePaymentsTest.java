package Napredno_Programiranje.SecondPartialExercises.kol_35;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}
class OnlinePayments{
    private final Map<String,Student> studentMap;

    public OnlinePayments() {
        studentMap = new HashMap<>();
    }

    void readItems (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> inputs = br.lines().collect(Collectors.toList());
        for (String input : inputs) {
            String [] parts = input.split(";");
            String id = parts[0];
            String descr = parts[1];
            int price = Integer.parseInt(parts[2]);
            studentMap.putIfAbsent(id,new Student(id));
            studentMap.get(id).addStavka(descr,price);
        }
    }
    void printStudentReport (String index, OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        if (studentMap.containsKey(index)){
            pw.println(studentMap.get(index));
        }else pw.println(String.format("Student %s not found!",index));
        pw.flush();
    }
}

class Student{
    private String id;
    private List<Stavka> stavki;

    public Student(String id) {
        this.id = id;
        stavki = new ArrayList<>();
    }
    public void addStavka(String desc, int price){
        stavki.add(new Stavka(desc,price));
    }
    public int netoSum(){
        return stavki.stream()
                .mapToInt(Stavka::getPrice)
                .sum();
    }
    public int fee(){
        int fee = (int) Math.round(netoSum()*1.14 / 100.00);
        if (fee<3)fee=3;
        else if (fee>300) fee = 300;
        return fee;
    }
    public int total(){return netoSum()+fee();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
         sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\nItems:\n",id,netoSum(),fee(),total()));
         AtomicInteger counter = new AtomicInteger(1);
         sb.append(stavki.stream()
                 .sorted(Comparator.comparing(Stavka::getPrice).reversed())
                 .map(stavka -> (counter.getAndIncrement())+". "+stavka.toString())
                 .collect(Collectors.joining("\n")));

         return sb.toString();
    }

}
class Stavka{
    private String description;
    private int price;

    public Stavka(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s %d",description,price);
    }
}

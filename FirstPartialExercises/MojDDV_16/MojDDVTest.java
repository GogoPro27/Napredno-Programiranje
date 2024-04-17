package Napredno_Programiranje.FirstPartialExercises.MojDDV_16;

import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
class AmountNotAllowedException extends Exception{
    double price;

    public AmountNotAllowedException(double price) {
        this.price = price;
        getMessage();
    }

    @Override
    public String getMessage() {
        return String.format("Receipt with amount %.0f is not allowed to be scanned",price);
    }
}

class Proizvod{
    private int price;
    private String type;

    public Proizvod(int price, String type) {
        this.price = price;
        this.type = type;
    }
    public double TotalPrice(){
        if(type.equals("A")){
            return price*1.18;
        }else if(type.equals("B")){
            return price*1.05;
        }else {
            return price;
        }
    }

    public int getPrice() {
        return price;
    }

    public double totalTax(){
        if(type.equals("A")){
            return 0.18*price*0.15;
        }else if(type.equals("B")){
            return 0.05*price*0.15;
        }else {
            return 0;
        }
    }
}

class Smetka{
    private String id;
    private List<Proizvod> proizvodi;

    public Smetka(String line) throws AmountNotAllowedException {
        proizvodi = new ArrayList<>();
        String[] parts = line.split("\\s++");
        id = parts[0];
        for (int i = 1; i < parts.length; i+=2) {
            proizvodi.add(new Proizvod(Integer.parseInt(parts[i]),parts[i+1]));
        }
        if (totalPriceWithoutTaxSmetka()>30000) {
//            System.out.println("frlam");
            throw new AmountNotAllowedException(totalPriceWithoutTaxSmetka());

        }
    }
//    public double totalPriceSmetka(){
//       return proizvodi.stream().mapToDouble(Proizvod::TotalPrice).sum();
//    }
    public int totalPriceWithoutTaxSmetka(){
        return proizvodi.stream().mapToInt(Proizvod::getPrice).sum();
    }
    public double totalTax(){
        return proizvodi.stream().mapToDouble(Proizvod::totalTax).sum();
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f",id,totalPriceWithoutTaxSmetka(),totalTax());
    }
}

class MojDDV{
    private List<Smetka> smetki;

    public MojDDV() {
        smetki = new ArrayList<>();
    }
    void readRecords (InputStream inputStream) throws AmountNotAllowedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());
        for (String input : inputs) {
            try {
                smetki.add(new Smetka(input));
            } catch (AmountNotAllowedException ae) {
                System.out.println(ae.getMessage());
            }
        }
    }
    public void printTaxReturns (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        smetki.forEach(pw::println);
        pw.flush();
        //pw.close();
    }
    public void printStatistics (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        DoubleSummaryStatistics ds = smetki.stream().mapToDouble(Smetka::totalTax).summaryStatistics();
        pw.println(String.format("min:\t%-10.3f",ds.getMin()));
        pw.println(String.format("max:\t%-10.3f",ds.getMax()));
        pw.println(String.format("sum:\t%-10.3f",ds.getSum()));
        pw.println(String.format("count:\t%-10d",ds.getCount()));
        pw.println(String.format("avg:\t%-10.3f",ds.getAverage()));
        pw.flush();
//        pw.close();
    }
}


public class MojDDVTest {

    public static void main(String[] args) throws AmountNotAllowedException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        try {
            mojDDV.readRecords(System.in);
        }catch (AmountNotAllowedException ae){
            System.out.println(ae.getMessage());
        }


        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);
        //program doesn't want to print after this line
        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}

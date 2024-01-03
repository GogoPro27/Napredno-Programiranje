package SecondPartialExcercises.kol_20;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * I partial exam 2016
 */
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde
class DailyTemperatures{
    public Map<Integer, TemperaturesInADay> dayMap;

    public DailyTemperatures() {
        dayMap = new TreeMap<>();
    }
    public String cutString(String string){
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))){
                return string.substring(0,i);
            }
        }
        return "00";
    }
    void readTemperatures(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line->{
            String[]parts = line.split("\\s+");
            int numDay = Integer.parseInt(parts[0]);
            List<Double> list = Arrays.stream(parts)
                    .skip(1)
                    .map(part->Double.parseDouble(cutString(part)))
                    .collect(Collectors.toList());
            if (line.contains("F"))
                dayMap.put(numDay,new Fahrenheit(numDay,list));
            else dayMap.put(numDay,new CelsiusDay(numDay,list));
        });
    }
    void writeDailyStats(OutputStream outputStream, char scale){
        PrintWriter pw = new PrintWriter(outputStream);
        dayMap.keySet().stream()
                        .forEach(key-> System.out.println(dayMap.get(key).print(scale)));
        pw.flush();
    }
}
abstract class TemperaturesInADay {
    protected int day;
    protected  List<Double> measurements;
    protected  DoubleSummaryStatistics dss ;
    public TemperaturesInADay(int day,List<Double> measurements) {
        this.measurements = measurements;
        dss = measurements.stream()
                .mapToDouble(i->i)
                .summaryStatistics();
        this.day = day;
    }
    public abstract String print(char c);
}
class Fahrenheit extends TemperaturesInADay{


    public Fahrenheit(int day, List<Double> measurements) {
        super(day, measurements);
    }

    public double toCelsius(double fahrenheit){
        return ((fahrenheit-32)*5)/9;
    }

    public String print(char c) {
        switch (c){
            case 'F':
                return String.format("%3d: Count: %3d Min: %6.2fF Max: %6.2fF Avg: %6.2fF",
                        day,
                        dss.getCount(),
                        dss.getMin(),
                        dss.getMax(),
                        dss.getAverage()
                        );

            case 'C':
                return String.format("%3d: Count: %3d Min: %6.2fC Max: %6.2fC Avg: %6.2fC",
                        day,
                        dss.getCount(),
                        toCelsius(dss.getMin()),
                        toCelsius(dss.getMax()),
                        toCelsius(dss.getAverage())
                );

                default:return "Invalid";
        }
    }
}
class CelsiusDay extends TemperaturesInADay{


    public CelsiusDay(int day, List<Double> measurements) {
        super(day, measurements);
    }

    public double toFahrenheit(double celsius){
        return (celsius*9/5) +32;
    }
    public String print(char c) {
        switch (c){
            case 'F':
                return String.format("%3d: Count: %3d Min: %6.2fF Max: %6.2fF Avg: %6.2fF",
                        day,
                        dss.getCount(),
                        toFahrenheit(dss.getMin()),
                        toFahrenheit(dss.getMax()),
                        toFahrenheit(dss.getAverage())
                );

            case 'C':
                return String.format("%3d: Count: %3d Min: %6.2fC Max: %6.2fC Avg: %6.2fC",
                        day,
                        dss.getCount(),
                        dss.getMin(),
                        dss.getMax(),
                        dss.getAverage()
                );

            default:return "Invalid";
        }
    }
}
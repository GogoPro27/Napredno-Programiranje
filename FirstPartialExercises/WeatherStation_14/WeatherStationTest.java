package Napredno_Programiranje.FirstPartialExercises.WeatherStation_14;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            LocalDateTime date = LocalDateTime.parse(line,df);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        LocalDateTime from = LocalDateTime.parse(line,df);
        line = scanner.nextLine();
        LocalDateTime to = LocalDateTime.parse(line,df);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
class WeatherInAMoment{
    private double temperatura;
    private double vlaznost;
    private double vetar;
    private double vidlivost;
    LocalDateTime vreme;

    public WeatherInAMoment(double temperatura, double vlaznost, double vetar, double vidlivost, LocalDateTime vreme) {
        this.temperatura = temperatura;
        this.vlaznost = vlaznost;
        this.vetar = vetar;
        this.vidlivost = vidlivost;
        this.vreme = vreme;
    }

    public LocalDateTime getVreme() {
        return vreme;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getTemperatura() {
        return temperatura;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",temperatura,vetar,vlaznost,vidlivost, vreme.format(DateTimeFormatter.ofPattern("E MMM d HH:mm:ss 'GMT' u")));
    }
}
class WeatherStation{
    private int days;
    ArrayList<WeatherInAMoment>merenja;

    public WeatherStation(int days) {
        this.days = days;
        merenja = new ArrayList<>();
    }
    public void addMeasurment(float temperature, float humidity,float wind, float visibility, LocalDateTime date){
        for (WeatherInAMoment weatherInAMoment : merenja) {
//            if ((Math.abs(weatherInAMoment.getVreme().getTime() - date.getTime()) < 150000)) return;
          long p = weatherInAMoment.getVreme().until(date,ChronoUnit.SECONDS);

            if(p<150)return;
        }
        merenja.add(new WeatherInAMoment(temperature,wind,humidity,visibility,date));
        merenja = merenja.stream()
                .filter(currvreme->currvreme.getVreme().until(date,ChronoUnit.DAYS)<days)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public int total(){
        return merenja.size();
    }
    public void status(LocalDateTime from, LocalDateTime to){
        List<WeatherInAMoment> filteredData = merenja.stream()
                .filter(merenje -> (merenje.getVreme().equals(from)||merenje.getVreme().isAfter(from))&&(merenje.getVreme().equals(to)||merenje.getVreme().isBefore(to)))
                .collect(Collectors.toList());

        filteredData.forEach(System.out::println);

        OptionalDouble opt = filteredData.stream()
                .mapToDouble(WeatherInAMoment::getTemperatura)
                .average();

        if (opt.isPresent()) {
            System.out.println(String.format("Average temperature: %.2f\n",opt.getAsDouble()));
        } else {
            throw new RuntimeException();
        }
    }
}

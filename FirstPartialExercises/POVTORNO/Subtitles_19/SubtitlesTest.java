package Napredno_Programiranje.FirstPartialExercises.POVTORNO.Subtitles_19;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде
class Element{
    int redenBr;
    private LocalTime start,end;
    private StringBuilder lines;

    public Element(List<String> linii) {
        redenBr = Integer.parseInt(linii.get(0));
        setStartAndEnd(linii.get(1));
        lines = new StringBuilder();
        linii.stream().skip(2).forEach(line->lines.append(line).append('\n'));
    }
public void setStartAndEnd(String line){
    String [] parts = line.split(" --> ");
    start = LocalTime.parse(parts[0], DateTimeFormatter.ofPattern("HH:mm:ss,SSS"));
    end = LocalTime.parse((parts[1]),DateTimeFormatter.ofPattern("HH:mm:ss,SSS"));
}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(redenBr).append('\n');
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
        sb.append(String.format("%s --> %s",start.format(dtf),end.format(dtf))).append('\n');
        sb.append(lines);
        return sb.toString();
    }
    public void pomesti(int ms){
        start = start.plus(ms, ChronoUnit.MILLIS);
        end = end.plus(ms, ChronoUnit.MILLIS);
    }
}
class Subtitles{
    private List<Element> elements;
    public Subtitles() {
        elements = new ArrayList<>();
    }
    public int loadSubtitles(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());
        List<String>elementLines = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            if(!inputs.get(i).isEmpty()){
                elementLines.add(inputs.get(i));
            }else {
                elements.add(new Element(elementLines));
                elementLines = new ArrayList<>();
            }
        }
        if(!elementLines.isEmpty())elements.add(new Element(elementLines));
        return elements.size();
    }
    public void print(){
        elements.forEach(System.out::println);
    }
    public void shift(int ms){
        elements.forEach(el->el.pomesti(ms));
    }
}

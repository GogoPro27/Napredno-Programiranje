package Napredno_Programiranje.FirstPartialExercises.Subtitles_19;
//poseben nacin na vnesuvanje, ne znam dali moze bolje, nmozno e da moze
//isto taka go prepisav ona so popravanjeto na ms s min h poso ne mi iskacase a i me mrzerse,
//inaku ez
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
class Vreme{
    private int cas,minuti,sekundi,milisekundi;
    String zaPecatenje;

    public Vreme(String time) {
        zaPecatenje = time;
        String[] main2Parts = time.split(",");
        milisekundi = Integer.parseInt(main2Parts[1]);
        String[] parts3  = main2Parts[0].split(":");
        cas = Integer.parseInt(parts3[0]);
        minuti = Integer.parseInt(parts3[1]);
        sekundi = Integer.parseInt(parts3[2]);
    }

    @Override
    public String toString() {
        return zaPecatenje;
    }
    public void change(int ms){
        milisekundi+=ms;
        sredi();
    }
    public void sredi(){
        if(milisekundi > 999) {
            milisekundi %= 1000;
            sekundi++;
        } else if(milisekundi < 0) {
            milisekundi += 1000;
            sekundi--;
        }

        if(sekundi > 59) {
            sekundi -= 60;
            minuti++;
        } else if(sekundi < 0) {
            sekundi += 60;
            minuti--;
        }

        if(minuti > 59) {
            minuti -= 60;
            cas++;
        } else if(minuti < 0) {
            minuti += 60;
            cas--;
        }
        zaPecatenje = String.format("%02d:%02d:%02d,%03d", cas, minuti, sekundi, milisekundi);
    }
}

class Element{
    private int redenBroj;
    private Vreme start;
    private Vreme end;
    String text;
    public Element(List<String> lines){
        text = "";
        for (int i = 0; i < lines.size(); i++) {
            if(i==0){
                redenBroj = Integer.parseInt(lines.get(i));
            }else if(i==1){
                String[] parts = lines.get(i).split(" --> ");
                start = new Vreme(parts[0]);
                end = new Vreme(parts[1]);
            }else {
                text+=lines.get(i)+'\n';
            }
        }
    }

    public Vreme getStart() {
        return start;
    }

    public Vreme getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return String.format("%d\n%s --> %s\n%s",redenBroj,start.toString(),end.toString(),text);
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
        int counter = 0;
        List<String>currentElement = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            if(inputs.get(i).isEmpty()){
                if (!currentElement.isEmpty()) {
                    elements.add(new Element(currentElement));
                    currentElement = new ArrayList<>();
                    counter++;
                }
            }else {
                currentElement.add(inputs.get(i));
            }
        }
        if(!currentElement.isEmpty()){
            elements.add(new Element(currentElement));
            counter++;
        }
        return counter;
    }

    public void print(){
        elements.forEach(System.out::println);
    }
    public void shift(int ms){
        elements.forEach(i->{
            i.getStart().change(ms);
            i.getEnd().change(ms);
        });
//        for (int i = 0; i < elements.size(); i++) {
//            elements.get(i).getStart().change(ms);
//            elements.get(i).getEnd().change(ms);
//        }
    }

}
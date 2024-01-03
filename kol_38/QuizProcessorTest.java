package SecondPartialExcercises.kol_38;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
class QuizProcessor{

    public static Map<String, Double> processAnswers(InputStream is){
        List<Student>students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String>inputs = br.lines().collect(Collectors.toList());
        for (String input : inputs) {
            String []parts = input.split(";");
            Student student = new Student(parts[0]);
            try {
                student.evaluatePoints(parts[1],parts[2]);
                students.add(student);
            }catch (RuntimeException ignored){
                System.out.println("A quiz must have same number of correct and selected answers");
            }
        }
        return students.stream()
                .collect(Collectors.groupingBy(
                        student -> student.index,
                        TreeMap::new,
                        Collectors.summingDouble(Student::getPoints)
                ));
    }

}
class Student{
    double points;
    String index;

    public Student(String index) {
        this.index = index;
        points = 0;
    }
    public void evaluatePoints(String studentAnswers,String correctAnswers){
        List<Character> studentA = Arrays.stream(studentAnswers.split(", "))
                .map(string -> string.charAt(0))
                .collect(Collectors.toList());
        List<Character> correctA = Arrays.stream(correctAnswers.split(", "))
                .map(string -> string.charAt(0))
                .collect(Collectors.toList());
        if (studentA.size()!=correctA.size())throw new RuntimeException();

        for (int i = 0; i < studentA.size(); i++) {
            if (studentA.get(i)==correctA.get(i))points+=1;
            else points-=0.25;
        }
    }

    public String getIndex() {
        return index;
    }

    public double getPoints() {
        return points;
    }
}
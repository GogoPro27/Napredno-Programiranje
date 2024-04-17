package Napredno_Programiranje.SecondPartialExercises.kol_31;
//ez
//package mk.ukim.finki.midterm;

import java.util.*;
import java.util.stream.Collectors;


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
class Student{
    private final String index;
    private final String name;
    private final Map<String,Integer> activities;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        activities = new HashMap<>();
        activities.put("midterm1",0);
        activities.put("midterm2",0);
        activities.put("labs",0);
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getActivities() {
        return activities;
    }
    public void update(String activity, int points){
        int max = activity.equals("labs") ? 10 : 100;
        if (activities.containsKey(activity)) {
            if (activities.get(activity) + points > max) {
                throw new RuntimeException();
            }
        activities.computeIfPresent(activity,(k,v)-> v + points);
        }
    }
    public double summaryPoints(){
        return activities.get("midterm1")*0.45 +
                activities.get("midterm2")*0.45 +
                activities.get("labs");
    }
    public int getGrade(){
        int grade = (int) Math.ceil(summaryPoints()/10);
        if (grade<6)grade = 5;
        return grade;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                index,name,activities.get("midterm1"),activities.get("midterm2"),activities.get("labs"),summaryPoints(),getGrade()
                );
    }
}
class AdvancedProgrammingCourse{
    private final Map<String,Student>studentMap;

    public AdvancedProgrammingCourse() {
        studentMap = new HashMap<>();
    }

    public void addStudent (Student s){
        studentMap.put(s.getIndex(),s);
    }
    public void updateStudent (String idNumber, String activity, int points){
        studentMap.get(idNumber).update(activity,points);
    }
    public List<Student> getFirstNStudents (int n){
        return studentMap.values().stream()
                .sorted(Comparator.comparing(Student::summaryPoints).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
    public Map<Integer,Integer> getGradeDistribution(){
        Map<Integer,Integer> map= studentMap.values().stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        TreeMap::new,
                        Collectors.summingInt(i->1)
                ));
        map.putIfAbsent(5,0);
        map.putIfAbsent(6,0);
        map.putIfAbsent(7,0);
        map.putIfAbsent(8,0);
        map.putIfAbsent(9,0);
        map.putIfAbsent(10,0);
        return map;
    }
    public void printStatistics(){
        DoubleSummaryStatistics dss= studentMap.values().stream()
                .filter(student -> student.getGrade()>=6)
                .mapToDouble(Student::summaryPoints)
                .summaryStatistics();
        System.out.println(String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                    dss.getCount(),dss.getMin(),dss.getAverage(),dss.getMax()));
    }
}
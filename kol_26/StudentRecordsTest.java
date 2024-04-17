package SecondPartialExcercises.kol_26;
//interesna, eden tezok metod
//konsultacii
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

// your code here
class StudentRecords{
    Map<String,Set<Student>> majorToStudentsMap;
    public StudentRecords() {
        majorToStudentsMap = new TreeMap<>();
    }
    public int readRecords(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());
        int counter = 0;
        for (String input : inputs) {
            Student newStudent = Student.generateStudent(input);
            majorToStudentsMap.putIfAbsent(newStudent.getMajor(),new TreeSet<>(Student.STUDENT_COMPARATOR));
            majorToStudentsMap.get(newStudent.getMajor()).add(newStudent);
            counter++;
        }
        return counter;
    }
    public void writeTable(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        majorToStudentsMap.keySet()
                        .forEach(key->{
                            pw.println(key);
                            majorToStudentsMap.get(key)
                                    .forEach(pw::println);
                        });
        pw.flush();
    }
    public void writeDistribution(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        Map<String,Map<Integer,Integer>> mapMap = new HashMap<>();
        majorToStudentsMap.forEach((key, value) -> {
            mapMap.putIfAbsent(key, new HashMap<>());
            for (int i = 6; i <= 10; i++) {
                mapMap.get(key).put(i, 0);
            }
            majorToStudentsMap.get(key).stream()
                    .map(Student::getGrades)
                    .flatMap(Collection::stream)
                    .forEach(grade -> mapMap.get(key).computeIfPresent(grade, (k, v) -> ++v));
        });
       mapMap.entrySet().stream()
               .sorted(Map.Entry.comparingByValue(Comparator.comparing(innerMap->-innerMap.get(10))))
                .map(Map.Entry::getKey)
                .forEach(key->{
                           pw.println(key);
                           mapMap.get(key).keySet().stream()
                                   .forEach(innerKey->{
                                       StringBuilder stars = new StringBuilder();
                                       int numOfInnerKey = mapMap.get(key).get(innerKey);
                                       IntStream.range(0, (int) Math.ceil((double) numOfInnerKey /10)).mapToObj(i->"*").forEach(stars::append);
                                       pw.println(String.format("%2d | %s(%d)",innerKey,stars,numOfInnerKey));
                                   });
                        });

        pw.flush();
    }
}
class Student {
    private final String code;
    private final String major;
    private final List<Integer> grades;
    public static final Comparator<Student> STUDENT_COMPARATOR =
            Comparator.comparing(Student::averageGrade)
                    .reversed()
                    .thenComparing(Student::getCode);

    public Student(String code, String major, List<Integer> grades) {
        this.code = code;
        this.major = major;
        this.grades = grades;
    }
    public static Student generateStudent(String line){
        String[] parts = line.split("\\s+");
        String code = parts[0];
        String major = parts[1];
        List<Integer> list = Arrays.stream(parts)
                .skip(2)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return new Student(code,major,list);
    }
    public double averageGrade(){
        return (double) grades.stream().mapToInt(i -> i).sum() / grades.size();
    }

    public String getCode() {
        return code;
    }

    public String getMajor() {
        return major;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f",code,averageGrade());
    }
}
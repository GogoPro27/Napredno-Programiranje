package SecondPartialExcercises.kol_28;

//package mk.ukim.finki.vtor_kolokvium;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class OperationNotAllowedException extends Exception{
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
class Student{
     String index;
    int yearsOfStudies;
    private Map<Integer,Map<String,Integer>> termsAndGrades;
    public static Comparator<Student> COMPARATOR =
            Comparator.comparing(Student::numOfPassedCourses)
                    .thenComparing(Student::avgGrade)
                    .thenComparing(Student::getIndex)
                    .reversed();

    public Student(String index, int yearsOfStudies) {
        this.index = index;
        this.yearsOfStudies = yearsOfStudies;
        termsAndGrades = new HashMap<>();
        int counter = 1;
        for (int i = 1; i <= yearsOfStudies*2; i++) {
            termsAndGrades.put(i,new TreeMap<>());
        }
    }
    public boolean addGrade(int term, String courseName,int grade) throws OperationNotAllowedException {
        if (yearsOfStudies == 3 && term> 6)throw new OperationNotAllowedException("Term "+term+" is not possible for student with ID "+index);
        if (yearsOfStudies == 4 && term> 8)throw new OperationNotAllowedException("Term "+term+" is not possible for student with ID "+index);
        if ((long) termsAndGrades.get(term).keySet().size() ==3)throw new OperationNotAllowedException("Student "+ index+" already has 3 grades in term "+term);
        termsAndGrades.get(term).putIfAbsent(courseName,grade);
        int maxGrades = yearsOfStudies==3 ? 18 : 24;
        if (numOfPassedCourses() == maxGrades) return true;
        return false;
    }
    public double avgGrade(){
        return termsAndGrades.values().stream()
                .flatMap(i->i.values().stream())
                .mapToInt(Integer::valueOf)
                .average().orElse(5);
    }
    public String detailedReport(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student: %s\n",index));
//        System.out.println(termsAndGrades.keySet());
        termsAndGrades.keySet().forEach(term->{
            sb.append(String.format("Term %d\n",term));
            sb.append(String.format("Courses: %d\n",termsAndGrades.get(term).keySet().size()));
            sb.append(String.format("Average grade for term: %.2f\n",
                    termsAndGrades.get(term).values().stream().mapToInt(Integer::valueOf).average().orElse(5.0)));
        });
        sb.append(String.format("Average grade: %.2f\n",avgGrade()));
        sb.append(String.format("Courses attended: %s\n",termsAndGrades.values().stream()
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.joining(" "))
        ));
        return sb.toString();
    }
    public int numOfPassedCourses(){
        return (int) termsAndGrades.values().stream()
                .mapToLong(i->i.values().size())
                .sum();
    }

    public Map<Integer, Map<String, Integer>> getTermsAndGrades() {
        return termsAndGrades;
    }

    @Override
    public String toString() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f",index,numOfPassedCourses(),avgGrade());
    }

    public String getIndex() {
        return index;
    }
}
class Faculty {
    private Map<String,Student> studentMap;
    private List<String> logs;
    private Map<String,List<Integer>> courses;

    public Faculty() {
        studentMap = new HashMap<>();
        logs = new ArrayList<>();
        courses = new HashMap<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        studentMap.put(id, new Student(id,yearsOfStudies));
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        boolean tf = studentMap.get(studentId).addGrade( term,courseName,grade);
        courses.putIfAbsent(courseName,new ArrayList<>());
        courses.get(courseName).add(grade);
        Student student = studentMap.get(studentId);
        if (tf){
            String log = String.format("Student with ID %s graduated with average grade %.2f in %d years",student.index,student.avgGrade(),student.yearsOfStudies);
            logs.add(log);
            studentMap.remove(studentId);
        }
    }

    String getFacultyLogs() {
//        System.out.println(logs.stream().collect(Collectors.joining("\n")));
        return logs.stream().collect(Collectors.joining("\n"));
    }

    String getDetailedReportForStudent(String id) {
        return studentMap.get(id).detailedReport();
    }

    void printFirstNStudents(int n) {
        studentMap.values().stream()
                .sorted(Student.COMPARATOR)
                .limit(n)
                .forEach(System.out::println);
    }

    void printCourses() {
        courses.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(List::size)))
                .forEach(entry->{
                    System.out.println(String.format("%s %d %.2f",entry.getKey(),entry.getValue().size(),entry.getValue().stream().mapToInt(Integer::valueOf).average().getAsDouble()));
                });
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}

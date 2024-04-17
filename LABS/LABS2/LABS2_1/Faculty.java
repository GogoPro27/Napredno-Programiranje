package Napredno_Programiranje.LABS.LABS2.LABS2_1;

import java.util.Arrays;

public class Faculty {
    protected String name;
    protected Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = Arrays.copyOf(students, students.length);
    }

    public int countStudentsFromCity(String cityName){
        int counter = 0;
        for(Student s : students){
            if(s.getCity().equals(cityName))counter++;
        }
        return counter;
    }

    public Student getStudent(long index){
        for(Student s : students){
            if(s.getIndex()==index)return s;
        }
        return null;
    }

    public double getAverageNumberOfContacts()
    {
        double av = 0;
        for(Student s : students){
            av += s.getNumContacts();
        }
        return av/students.length;//sumljivo
    }

    public Student getStudentWithMostContacts(){
        Student max = students[0];
        for(Student s : students){
           if(s.getNumContacts()>max.getNumContacts())max = s;
           else if(s.getNumContacts() == max.getNumContacts() && s.getIndex()>max.getIndex())max = s;
        }
        return max;
    }

    @Override
    public String toString() {
        return "{\"fakultet\":\"" +
                name +
                "\", \"studenti\":" +
                Arrays.toString(students) +
                "}";
    }
}

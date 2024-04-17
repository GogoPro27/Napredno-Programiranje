package Napredno_Programiranje.LABS.LABS6;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }
}
class SuperString{
    List<String> stringList;
    List<Boolean> booleans;
    public SuperString() {
        stringList = new LinkedList<>();
        booleans = new LinkedList<>();
    }
    void append(String string){
        stringList.add(string);
        booleans.add(0,true);
    }
    void insert(String string){
        stringList.add(0,string);
        booleans.add(0,false);
    }
    boolean contains(String s){
        return concatanated().contains(s);
    }
    public String concatanated(){
        return stringList.stream().collect(Collectors.joining(""));
    }
    @Override
    public String toString() {
        return concatanated();
    }
    public void removeLast(int k){
        booleans.stream()
                .limit(k)
                .forEach(b->{
                    if (b){
                        stringList.remove(stringList.size()-1);
                    }else {
                        stringList.remove(0);
                    }
                });
    }
    public void reverse(){
        stringList = reversedList(stringList.stream()
                .map(this::reversedString).collect(Collectors.toList()));

    }
    public String reversedString(String s){
        StringBuilder sb = new StringBuilder();
        for (int i = s.length()-1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
    public List<String> reversedList(List<String> list){
        List<String> newList = new LinkedList<>();
        for (String s : list) {
            newList.add(0,s);
        }
        return newList;
    }
}
package Napredno_Programiranje.LABS.LABS6.SuperString_2;

import java.util.*;
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
    private List<String> strings;
    private Stack<String> stek;

    public SuperString() {
        strings = new LinkedList<>();
        stek = new Stack<>();
    }
    public void append(String s){
        strings.add(s);
        stek.push(s);
    }
    public void insert(String s){
        strings.add(0,s);
        stek.push(s);
    }
    public boolean contains(String s){
        String sb = String.join("", strings);
        return sb.contains(s);
    }
    public void reverse(){
        Collections.reverse(strings);
        strings = strings.stream()
                .map(s -> new StringBuilder(s).reverse().toString())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.join("", strings);
    }
    public void removeLast(int k){
        while (k!=0&&!stek.isEmpty()){
            strings.remove(stek.pop());
            k--;
        }
    }
}

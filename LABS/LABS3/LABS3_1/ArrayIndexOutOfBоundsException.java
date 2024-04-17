package Napredno_Programiranje.LABS.LABS3.LABS3_1;

public class ArrayIndexOutOfBоundsException extends Exception {
    int a ;
    public ArrayIndexOutOfBоundsException(int idx) {
        a = idx;
    }
    public void message(){
        System.out.printf("%d nema vo listata%n",a);
    }
}
